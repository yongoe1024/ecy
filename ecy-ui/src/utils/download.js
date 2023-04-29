import axios from 'axios'
import {Message} from 'element-ui'
import Vue from 'vue'

const service = axios.create(
  {
    responseType: 'arraybuffer'
  }
)
/**
 * 下载文件的配置
 */
service.interceptors.request.use(
  config => {
    config.headers['Authorization'] = window.localStorage.getItem('token')
    return config
  },
  error => {
    console.log(error)
  }
)

service.interceptors.response.use(
  resp => {
    const headers = resp.headers
    let reg = RegExp(/application\/json/)
    if (headers['content-type'].match(reg)) {
      resp.data = unitToString(resp.data)
      Message.error({ message: resp.data.message })
    }
    else {
      let fileDownload = require('js-file-download')
      let fileName = headers['content-disposition'].split(';')[1].split('filename=')[1]
      let contentType = headers['content-type']
      fileName = decodeURIComponent(fileName)
      fileDownload(resp.data, fileName, contentType)
    }
  },
  error => {
    console.log(error)
  }
)

function unitToString (unitArray) {
  let encodeString = String.fromCharCode.apply(null, new Uint8Array(unitArray))
  // 使用 decodeURI() 和 decodeURIComponent() 替代它。
  let decodeString = decodeURIComponent(encodeString)
  return JSON.parse(decodeString)
}

Vue.prototype.$downloadRequest = (url, params) => {
  return service(
    {
      method: 'post',
      url: `${Vue.prototype.$BASE_URL}${url}`,
      data: params
    }
  )

}
