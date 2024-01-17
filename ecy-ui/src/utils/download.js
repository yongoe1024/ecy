import axios from 'axios'
import { Message } from 'element-ui'
import Vue from 'vue'
import { endLoading, startLoading } from '@/utils/loading'

const service = axios.create(
  {
    baseURL: Vue.prototype.$BASE_URL,
    responseType: 'arraybuffer',
    withCredentials: true,  //是否允许跨域, 解决请求不包含cookie  ：session
    timeout: 0
  }
)
/**
 * 下载文件的配置
 */
service.interceptors.request.use(
  config => {
    startLoading()
    config.headers['Authorization'] = window.localStorage.getItem('token')
    return config
  },
  error => {
    console.log(error)
  }
)

service.interceptors.response.use(
  resp => {
    endLoading()
    const headers = resp.headers
    let reg = RegExp(/application\/json/)
    if (headers['content-type'].match(reg)) {
      let enc = new TextDecoder('utf-8')
      let data = JSON.parse(enc.decode(new Uint8Array(resp.data)))
      Message.error({ message: data.message })
    }
    else {
      let fileName = headers['content-disposition'].split(';')[1].split('filename=')[1]
      let contentType = headers['content-type']
      fileName = decodeURIComponent(fileName)
      let fileDownload = require('js-file-download')
      fileDownload(resp.data, fileName, contentType)
    }
  },
  error => {
    endLoading()
    console.log(error)
    Message.error({ message: error.response.status + ' ' + error.response.statusText })
    return Promise.reject(error)
  })

/**
 * axios下载文件
 * @param {*下载接口} url 
 * @param {*请求体} params 
 * @param {*回调,传回进度百分比} progress 
 * @returns 
 */
function axiosDownload (url, params, progress) {
  return service(
    {
      method: 'post',
      url: `${url}`,
      data: params,
      onDownloadProgress: progressEvent => {
        try {
          progress(Math.floor((progressEvent.loaded * 100) / progressEvent.total))
        } catch (e) { }
      }
    }
  )
}

/**
 * a标签下载
 * @param {*路径} url 
 * @param {*参数} params 
 */
function download (url, params) {
  var a = document.createElement("a")
  var str = ''
  if (params) {
    str = Object.keys(params).map(key => {
      if (typeof (params[key]) !== "undefined" && params[key] !== null) {
        return `${key}=${params[key]}`
      }
    }).join('&')
  }
  a.href = `${Vue.prototype.$BASE_URL}${url}?Authorization=${window.localStorage.getItem('token')}&${str}`
  a.style.display = "none"
  a.target = '_blank'
  document.body.appendChild(a)
  a.click()
  a.remove()
}

export default download

Vue.prototype.download = download