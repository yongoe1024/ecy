import './config/config'
import Vue from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'

import ElementUI from 'element-ui'
import 'element-ui/lib/theme-chalk/index.css'
import 'font-awesome/css/font-awesome.css'
import eIconPicker from 'e-icon-picker'
import 'e-icon-picker/lib/index.css' // 基本样式，包含基本图标
import '@/components/index' //全局引入组件
import '@/utils/request' //axios工具
import "@/utils/download" //下载工具
import '@/utils/auth' //自定义指令
import '@/utils/drag' //自定义指令

Vue.config.productionTip = false

Vue.use(ElementUI)
Vue.use(eIconPicker, {
  FontAwesome: true, ElementUI: true, zIndex: 3100  /*选择器弹层的最低层,全局配置*/
})
new Vue({
  router,
  store,
  render: h => h(App)
}).$mount('#app')
