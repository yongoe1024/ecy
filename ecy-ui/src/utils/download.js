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
    console.log(error)
  }
)

Vue.prototype.$downloadRequest = (url, params) => {
  return service(
    {
      method: 'post',
      url: `${Vue.prototype.$BASE_URL}${url}`,
      data: params
    }
  )

}
