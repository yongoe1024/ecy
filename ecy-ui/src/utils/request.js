import axios from 'axios'
import Vue from 'vue'
import router from '@/router'
import { Message } from 'element-ui'
import { endLoading, startLoading } from '@/utils/loading'

const ax = axios.create({
  baseURL: Vue.prototype.$BASE_URL,
  withCredentials: true,  //是否允许跨域, 解决请求不包含cookie  ：session
  timeout: 0
})

//请求拦截器
ax.interceptors.request.use(config => {
  if (window.localStorage.getItem('token')) {
    config.headers['Authorization'] = window.localStorage.getItem('token')
  }
  startLoading()
  return config
}), error => {
  endLoading()
  console.log(error)
  Promise.reject(error)
}

//响应拦截器
ax.interceptors.response.use(
  response => {
    endLoading()
    if (response.status == 200) {
      // 未登录
      if (response.data.code == 401) {
        Message.error({ message: response.data.message })
        window.localStorage.clear()
        window.sessionStorage.clear()
        router.replace('/login')
        location.reload()
      }
      else if (response.data.code == 402) {
        //验证码错误
        Message.error({ message: response.data.message })
        return Promise.reject(response)
      }
      else if (response.data.code != 200) {
        Message.error({ message: response.data.message })
        return Promise.reject(response)
      }
      //有信息就提示
      else {
        if (response.data.message) {
          Message.success({ message: response.data.message })
        }
      }
      return response.data.data
    }
  },
  error => {
    endLoading()
    console.log(error)
    Message.error({ message: error.response.status + ' ' + error.response.statusText })
    return Promise.reject(error)
  })

export default ax
Vue.prototype.axios = ax