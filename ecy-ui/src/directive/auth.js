import Vue from 'vue'

Vue.directive('auth', {
  inserted: function (el, binding, vnode) {
    var isExist = false
    // 从配置获取用户按钮权限
    let btnPermissions = vnode.context.$route.meta.auth
    if (btnPermissions && btnPermissions.indexOf(binding.value) > -1)
      isExist = true

    //不存在时删除节点
    if (el.parentNode && !isExist) {
      el.parentNode.removeChild(el)
    }
  }
})