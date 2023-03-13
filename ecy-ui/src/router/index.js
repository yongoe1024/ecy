import Vue from 'vue'
import VueRouter from 'vue-router'
import store from '@/store'

import initMenu from '@/utils/menus'

import NotFound from '@/views/404'
import Index from '@/views/Index'
import Login from '@/views/Login'
import Home from '@/views/Home'

import UserInfo from '@/views/user/UserInfo'
import Password from '@/views/user/Password'
import Register from '@/views/user/Register'
import Forget from '@/views/user/Forget'

import DictData from '@/views/basic/DictData'

Vue.use(VueRouter)

const routes = [
  {
    path: '/login',
    name: '登录',
    component: Login
  },
  {
    path: '/register',
    name: '注册账号',
    component: Register,
  },
  {
    path: '/forget',
    name: '找回密码',
    component: Forget,
  },
  {
    path: '/',
    name: '页面框架',
    component: Index,
    children: [
      {
        path: '/index',
        name: '首页',
        component: Home,
      },
      {
        path: '/userinfo',
        name: '用户信息',
        component: UserInfo,
      },
      {
        path: '/password',
        name: '修改密码',
        component: Password,
      },
      {
        path: '/dict-date',
        name: '数据字典-数据',
        component: DictData,
      },
    ]
  },
  {
    path: '*',
    component: NotFound
  }
]

const router = new VueRouter({
  // mode: 'history',
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
    if (to.path === '/login' || to.path === '/forget' || to.path === '/register') {
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
