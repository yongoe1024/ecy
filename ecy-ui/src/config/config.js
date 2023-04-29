// 环境变量，最先执行
import Vue from 'vue'

Vue.prototype.$BASE_URL = process.env.VUE_APP_BASE_URL
Vue.prototype.$TITLE = process.env.VUE_APP_TITLE