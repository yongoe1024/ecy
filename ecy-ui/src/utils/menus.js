import axios from '@/utils/request'

var flag = true
export default (router, store) => {
  if (flag) {
    flag = false
    if (store.getters.getRoutes.length == 0) {
      axios.post('/user/menu').then(data => {
        //格式化
        let fmtRoutes = formatRoutes(data)
        router.addRoute({
          name: '',
          path: '',
          component: () => import('/src/views/Index'),
          children: fmtRoutes
        })
        //存入vuex
        store.commit('initRoutes', fmtRoutes)
      }).finally(() => {
        flag = true
      }
      )
    }
  }
}

function formatRoutes (routes) {
  let fmtRoutes = []
  routes.forEach(router => {
    let {
      keepAlive,
      type,
      component,
      name,
      icon,
      children,
      isShow
    } = router
    if (children && children instanceof Array) {
      children = formatRoutes(children)			// 递归
    }
    let fmRouter = {
      type: type,
      name: name,
      icon: icon,
      children: children,
      isShow: isShow,
      meta: {
        auth: [],
        keepAlive: keepAlive
      }
    }
    // 存在组件就是页面
    if (component) {
      fmRouter.path = component
      fmRouter.component = () => import('/src/views' + component)
      fmRouter.children.forEach(v => {
        //按钮权限
        if (v.type === 3)
          fmRouter.meta.auth.push(v.name)
      })
      fmRouter.children = []
    } else {
      // 不存在就是目录和按钮
      fmRouter.path = ''
      fmRouter.component = { render: (e) => e("router-view") }
    }
    fmtRoutes.push(fmRouter)
  })
  return fmtRoutes
}
