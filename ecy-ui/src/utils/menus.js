import axios from '@/utils/request'

export default (router, store) => {

  if (store.getters.getRoutes.length == 0) {
    //防止无限请求，一秒一次
    axios.post('/user/menu').then(data => {
      //格式化
      let fmtRoutes = formatRoutes(data)
      fmtRoutes.forEach(x => router.addRoute(x))
      //存入vuex
      store.commit('initRoutes', fmtRoutes)
    }).catch(() => {
    })
  }
}

function formatRoutes (routes) {
  let fmtRoutes = []
  routes.forEach(router => {
    let {
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
      meta: { auth: [] }
    }
    let a = component == null ? '' : component.replaceAll('/', '-')
    fmRouter.path = '/' + a

    children.forEach(v => {
      //按钮权限
      if (v.type === 3)
        fmRouter.meta.auth.push(v.name)
    })
    if (fmRouter.type === 1 || type === 3) {
      fmRouter.component = () => import('/src/views/Index')
      fmRouter.path = '//'    // 此处不这么做 会导致路由混乱 ，meta传不进去值，顺序错误
    }
    else {
      fmRouter.component = () => import('/src/views/' + component)
    }
    fmtRoutes.push(fmRouter)
  })
  return fmtRoutes
}
