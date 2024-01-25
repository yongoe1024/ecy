import Vue from 'vue'
import VueRouter from 'vue-router'
import store from '@/store'

import initMenu from '@/utils/menus'

Vue.use(VueRouter)

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
    path: '/oauth/qq',
    name: 'QQ',
    component: () => import('@/views/oauth/QQ'),
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
        path: '/letter',
        name: '信件',
        component: () => import('@/views/letter/Letter'),
      },
      {
        path: '/letter-info',
        name: '信件详情',
        component: () => import('@/views/letter/LetterInfo'),
      },
      {
        path: '/test',
        name: '测试',
        component: () => import('@/views/Test'),
      },
    ]
  },
]

const router = new VueRouter({
  // mode: 'history',
  mode: 'hash',
  routes
})
// 路由前置守卫
router.beforeEach((to, from, next) => {
  // startLoading()
  if (window.localStorage.getItem('token')) {
    if (to.path == '/login' || to.path == '/') {
      next('/home')
    } else {
      initMenu(router, store)
      next()
    }
  }
  else {
    let arr = ['/login', '/forget', '/register', '/oauth/qq']
    if (arr.indexOf(to.path) > -1) {
      next()
    }
    else {
      //加入参数来重定向
      if (to.path == '/')
        next('/login')
      else
        next('/login?redirect=' + to.path)
    }
  }
})
router.afterEach((to, from) => {
  // endLoading()
})

// 防止跳转当前路径报错
let originPush = VueRouter.prototype.push  //备份原push方法
VueRouter.prototype.push = function (location, resolve, reject) {
  if (resolve && reject) {    //如果传了回调函数，直接使用
    originPush.call(this, location, resolve, reject)
  } else {                     //如果没有传回调函数，手动添加
    originPush.call(this, location, () => { }, () => { })
  }
}
VueRouter.prototype.push = function (location, resolve, reject) {
  if (resolve && reject) {    //如果传了回调函数，直接使用
    originPush.call(this, location, resolve, reject)
  } else {                     //如果没有传回调函数，手动添加
    originPush.call(this, location, () => { }, () => { })
  }
}

export default router
