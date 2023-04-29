import Vue from 'vue'
import VueRouter from 'vue-router'
import store from '@/store'

import initMenu from '@/utils/menus'

import NotFound from '@/views/404'
import Index from '@/views/Index'

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
    path: '/index',
    name: '页面框架',
    component: Index,
    children: [
      {
        path: '/index',
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
        path: '/password',
        name: '修改密码',
        component: () => import('@/views/user/Password'),
      },
      {
        path: '/letter-info',
        name: '信件详情',
        component: () => import('@/views/letter/LetterInfo'),
      },
      {
        path: '/letter-send',
        name: '写信',
        component: () => import('@/views/letter/LetterSend'),
      },
      {
        path: '/letter-recv',
        name: '收件箱',
        component: () => import('@/views/letter/LetterRecv'),
      },
    ]
  },
  {
    path: '*',
    component: NotFound
  }
]

const router = new VueRouter({
  mode: 'history',
  routes
})
// 路由前置守卫
router.beforeEach((to, from, next) => {
  if (window.localStorage.getItem('token')) {
    if (to.path == '/login' || to.path == '/') {
      next('/index')
    } else {
      initMenu(router, store)
      next()
    }
  }
  else {
    // 退出登陆刷新页面的，先不用
    // if (to.path == '/login' && from.path != '/') {
    //   window.localStorage.removeItem("first")
    // }
    let arr = ['/login', '/forget', '/register', '/oauth/qq']
    console.log(to.path)
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

export default router
