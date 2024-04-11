import SparkMD5 from "spark-md5"
import Vue from 'vue'
import axios from 'axios'

var CancelToken = axios.CancelToken //在请求的任何阶段关闭请求。
var source = CancelToken.source() //取消请求的令牌

const baseURL = Vue.prototype.$BASE_URL
const chunkSize = 1024 * 1024 * 5 // 文件分片大小 5M
const uploadUrl = baseURL + '/upload/uploadChunk' // 上传地址
const checkChunkExistUrl = baseURL + '/upload/checkChunkExist' // 检查分片是否存在
const mergeChunksUrl = baseURL + '/upload/merge' // 合并分片


/**
 * 主要逻辑
 * http-request	覆盖默认的上传行为，可以自定义上传的实现，但是需要调用 onSuccess 才能告知组件上传成功。
 * http-request 传入的参数为fileObject，包含el-upload的所有属性/函数，同时还有file属性，为原生的File对象。每个文件都会调用一次http-request。
 * http-request 内部调用下面的uploadChunk异步函数，返回Promise对象，最终成功后调用onSuccess函数，失败后调用onError函数。
 * 构造请求对象FileRequest，包含文件名、文件对象、文件总大小、文件md5、当前分块序号、分块总数、分块大小、当前分块大小。
 * 1.先检测文件是否存在，如果存在则跳过上传，否则上传。
 * 2.上传分块，跳过存在的分块。每个分块上传成功后，调用onProgress函数，更新进度条。
 * 3.所有分块上传成功后，再执行mergeChunks函数，合并分块。
 * 4.返回Promise对象。最终成功后调用onSuccess函数，失败后调用onError函数。
 * 
 * checkChunkExist函数检查文件是否存在，返回{skipUpload: true}，跳过上传。返回{uploaded: [0,1,2]}，表示已上传的分块序号。
 * mergeChunks函数，合并分块。
 */

/**
 * 分片上传功能
 * @param fileObject Element的fileObject对象
 * @returns 
 */
export async function uploadChunk (fileObject) {
  if (!fileObject && !fileObject.file) {
    return Promise.reject("File is Null.")
  }
  let file = fileObject.file
  //分块数量
  let totalChunks = Math.ceil(file.size / chunkSize)
  //md5计算
  let md5 = await firstChunkMd5(file)
  //检查文件存在
  let res = await checkChunkExist(new FileRequest(file, md5, totalChunks, null))
  if (res.skipUpload) {
    //文件已存在
    return Promise.resolve("上传成功")
  }
  let chunkExistNumber = res.uploaded
  if (chunkExistNumber.length == totalChunks) {
    //可以合并了
    let res = await mergeChunks(new FileRequest(file, md5, totalChunks, null))
    console.log(res)
    return Promise.resolve("上传成功")
  }

  // for循环分块上传
  let axiosList = []   // axios请求列表
  let progressList = [] // 进度列表
  for (let chunkNumber = 0; chunkNumber < totalChunks; chunkNumber++) {
    progressList[chunkNumber] = 0
    if (chunkExistNumber.includes(chunkNumber)) {
      console.log('分块已存在 ' + chunkNumber)
      progressList[chunkNumber] = 100
      continue
    }
    let fileRequest = new FileRequest(file, md5, totalChunks, chunkNumber)
    let formData = new FormData()
    //将fileObj全部添加到formData中
    for (let key in fileRequest) {
      formData.append(key, fileRequest[key])
    }
    //axios配置
    let config = {
      timeout: 0,
      headers: {
        "Content-Type": "multipart/form-data",
        Authorization: window.localStorage.getItem("token")
      },
      cancelToken: source.token,
      onUploadProgress: progressEvent => {
        try {
          progressList[chunkNumber] = Math.floor((progressEvent.loaded * 100) / progressEvent.total)
          //progress的平均值为进度
          let sum = progressList.reduce((accumulator, currentValue) => accumulator + currentValue, 0)
          fileObject.onProgress({ percent: sum / totalChunks })
        } catch (e) {
          stop()
        }
      }
    }
    //等所有axios都完成再下一步
    axiosList.push(axios.post(uploadUrl, formData, config))
  }
  let result = await Promise.all(axiosList)
  //所有分块上传成功
  if (result.every(item => item.data.code == 200)) {
    //合并分块
    try {
      await mergeChunks(new FileRequest(file, md5, totalChunks, null))
      return Promise.resolve("合并成功")
    } catch (error) {
      return Promise.reject("合并失败")
    }
  } else {
    return Promise.reject("分块未全部上传成功")
  }
}

/**
 * 中止上传
 */
export function stop () {
  source.cancel('中止上传')
  source = CancelToken.source()
}

/**
 * 获取第一个块的md5
 * @param file 原生File对象
 * @returns 异步Promise
 */
function firstChunkMd5 (file) {
  return new Promise((resolve, reject) => {
    const fileReader = new FileReader()
    const spark = new SparkMD5.ArrayBuffer()
    const chunk = file.slice(0, chunkSize)
    fileReader.onload = function (event) {
      spark.append(event.target.result)
      const md5 = spark.end()
      resolve(md5)
    }
    fileReader.onerror = function () {
      reject(new Error("File MD5 read error."))
    }
    fileReader.readAsArrayBuffer(chunk)
  })
}
/**
 * 检测文件是否存在
 * @param fileRequest FileRequest对象
 * @returns 请求结果
 */
function checkChunkExist (fileRequest) {
  fileRequest.file = null
  return myAxios.post(checkChunkExistUrl, fileRequest)
}
/**
 * 合并文件
 * @param fileRequest FileRequest对象
 * @returns 请求结果
 */
function mergeChunks (fileRequest) {
  fileRequest.file = null
  return myAxios.post(mergeChunksUrl, fileRequest)
}
/**
 * 文件请求对象构造函数
 * @param file 原生File
 * @param md5 文件md5
 * @param totalChunks 分块总数
 * @param chunkNumber 当前分块序号,从0开始
 */
function FileRequest (file, md5, totalChunks, chunkNumber) {
  let chunkFile = file.slice(0 + chunkNumber * chunkSize, 0 + (chunkNumber + 1) * chunkSize)
  return {
    fileName: file.name,   //文件名
    file: chunkFile,      //文件对象
    totalSize: file.size, //文件总大小
    md5: md5,             //文件md5
    chunkNumber: chunkNumber,   //当前分块序号
    totalChunks: totalChunks,   //分块总数
    chunkSize: chunkSize,     //分块大小
    currentChunkSize: chunkFile.size, //当前分块大小
  }
}

const myAxios = axios.create()
// 解决请求不包含cookie  ：session
myAxios.defaults.withCredentials = true
//请求拦截器
myAxios.interceptors.request.use(config => {
  if (window.localStorage.getItem('token')) {
    config.headers['Authorization'] = window.localStorage.getItem('token')
  }
  return config
}), error => {
  console.log(error)
}
//响应拦截器
myAxios.interceptors.response.use(
  success => {
    if (success.status == 200) {
      if (success.data.code != 200) {
        return Promise.reject(success.data.message)
      }
      return success.data.data
    }
  },
  error => {
    return Promise.reject(error)
  })