import axios from 'axios'
import Vue from 'vue'
import store from '@/store'
import router from '@/router'
import {Message} from 'element-ui'

const ax = axios.create()

// 解决请求不包含cookie  ：session
ax.defaults.withCredentials = true
ax.defaults.baseURL = Vue.prototype.$BASE_URL

//请求拦截器
ax.interceptors.request.use(config => {
  if (window.localStorage.getItem('token')) {
    config.headers['Authorization'] = window.localStorage.getItem('token')
  }
  return config
}), error => {
  console.log(error)
}

//响应拦截器
ax.interceptors.response.use(
  success => {
    if (success.status == 200) {
      // 未登录
      if (success.data.code == 401) {
        Message.error({ message: success.data.message })
        window.localStorage.removeItem('token')
        store.commit('initRoutes', [])
        router.replace('/login')
        return Promise.reject(success)
      }
      else if (success.data.code == 402) {
        //验证码错误
        Message.error({ message: success.data.message })
        return Promise.reject(success)
      }
      else if (success.data.code != 200) {
        Message.error({ message: success.data.message })
        return Promise.reject(success)
      }
      //有信息就提示
      else {
        if (success.data.message) {
          Message.success({ message: success.data.message })
        }
      }
      return success.data.data
    }
  },
  error => {
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