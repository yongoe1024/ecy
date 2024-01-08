import axios from 'axios'
import Vue from 'vue'
import store from '@/store'
import router from '@/router'
import {Message} from 'element-ui'
import {endLoading, startLoading} from '@/utils/loading'

const ax = axios.create({
  baseURL: Vue.prototype.$BASE_URL,
  withCredentials: true,  //是否允许跨域, 解决请求不包含cookie  ：session
  timeout: 6000
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
        window.localStorage.removeItem('token')
        store.commit('initRoutes', [])
        router.replace('/login')
        return Promise.reject(response)
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
    if (!error.response) {
      Message.error({ message: '无法连接服务器' })
      window.localStorage.removeItem('token')
      store.commit('initRoutes', [])
      router.replace('/login')
    }
    else if (error.response.data.status == 504 || error.response.data.status == 404) {
      Message.error({ message: '404 服务器无法访问' })
    }
    else if (error.response.data.status == 500) {
      Message.error({ message: '500 服务器错误' })
      window.localStorage.removeItem('token')
      store.commit('initRoutes', [])
      router.replace('/login')
    }
    else if (error.response.data.status == 403) {
      Message.error({ message: '403 权限不足,请联系管理员' })
    }
    else if (error.response.data.status == 401) {
      Message.error({ message: '401 未登录' })
      window.localStorage.removeItem('token')
      store.commit('initRoutes', [])
      router.replace('/login')
    }
    else {
      if (error.response.data.error) {
        Message.error({ message: error.response.data.message })
      }
      else {
        Message.error({ message: '无法请求服务器，未知错误' })
      }
    }
    return Promise.reject(error)
  })

export default ax
Vue.prototype.axios = ax