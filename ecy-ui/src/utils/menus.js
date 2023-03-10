import axios from '@/utils/request'

var flag = true

export default (router, store) => {

  if (flag && store.getters.getRoutes.length == 0) {
    flag = false
    //防止无限请求，一秒一次
    setTimeout(() => { flag = true }, 1000)
    axios.post('/user/menu').then(data => {
      //格式化
      let fmtRoutes = formatRoutes(data)
      fmtRoutes.forEach(x => router.addRoute(x))
      //存入vuex
      store.commit('initRoutes', fmtRoutes)
    }).catch(() => {
      //防止无限请求
      flag = false
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
    } = router
    if (children && children instanceof Array) {
      children = formatRoutes(children)			// 递归
    }

    let fmRouter = {
      type: type,
      name: name,
      icon: icon,
      children: children,
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
