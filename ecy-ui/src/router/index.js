import Vue from 'vue'
import Router from 'vue-router'
import store from '@/store'
import NProgress from "nprogress"
import 'nprogress/nprogress.css' //这个样式必须引入

import initMenu from '@/utils/menus'

Vue.use(Router)

const routes = [
  {
    path: '/login',
    name: '登录',
    component: () => import('@/views/Login')
  },
  {
    path: '/register',
    name: '注册账号',
    component: () => import('@/views/user/Register')
  },
  {
    path: '/forget',
    name: '找回密码',
    component: () => import('@/views/user/Forget'),
  },
  {
    path: '',
    name: '',
    component: () => import('@/views/Index'),
    children: [
      {
        path: '/home',
        name: '首页',
        component: () => import('@/views/Home'),
      },
      {
        path: '/userinfo',
        name: '用户信息',
        component: () => import('@/views/user/UserInfo'),
      },
      {
        path: '/password',
        name: '修改密码',
        component: () => import('@/views/user/Password'),
      },
      {
        path: '/test',
        name: '测试',
        component: () => import('@/views/Test'),
      },
    ]
  },
]

const router = new Router({
  // mode: 'history',
  mode: 'hash',
  routes: routes
})
// 路由前置守卫
// 路由前置守卫
var flag = true
router.beforeEach((to, from, next) => {
  NProgress.start()
  // startLoading()
  if (window.localStorage.getItem('token')) {
    if (flag) {
      flag = false
      initMenu().then((fmtRoutes) => {
        router.addRoute({
          name: '',
          path: '',
          component: () => import('/src/views/Index'),
          children: fmtRoutes
        })
        store.commit('initRoutes', fmtRoutes)
        if (to.path == '/login' || to.path == '/') {
          next('/home')
        } else {
          next({ ...to })
        }
      })
    } else {
      if (to.path == '/login' || to.path == '/') {
        next('/home')
      } else {
        next()
      }
    }
  }
  else {
    if (to.path != '/login')
      next('/login?redirect=' + to.path)
    let arr = ['/', '/login*', '/forget', '/register', '/oauth/*']
    for (let i = 0; i < arr.length; i++) {
      if (matchAntPath(arr[i], to.path)) {
        next()
        return
      }
    }
  }
})
router.afterEach((to, from) => {
  NProgress.done()
})

function matchAntPath (pattern, path) {
  // 将通配符转换为正则表达式
  const regexPattern = pattern
    .replace(/\*/g, '[^/]*') // 将单个星号转换为匹配任何非斜杠字符的序列
    .replace(/\*\*/g, '.*')  // 将双星号转换为匹配任何字符的序列

  const regex = new RegExp(`^${regexPattern}$`)
  return regex.test(path)
}

// 防止连续点击多次路由报错
let routerPush = Router.prototype.push
let routerReplace = Router.prototype.replace
// push
Router.prototype.push = function push (location) {
  return routerPush.call(this, location).catch(err => err)
}
// replace
Router.prototype.replace = function push (location) {
  return routerReplace.call(this, location).catch(err => err)
}

export default router
