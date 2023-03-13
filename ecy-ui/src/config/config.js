// 环境变量，最先执行
import Vue from 'vue'
//配合docker
// const { promiseBaseUrl } = document.querySelector('html').dataset
// if (promiseBaseUrl) {
//     Vue.prototype.$BASE_URL = `${promiseBaseUrl}`
// } else {
//     Vue.prototype.$BASE_URL = process.env.VUE_APP_BASE_URL
// }

// 用nginx做转发
Vue.prototype.$BASE_URL = process.env.VUE_APP_BASE_URL
Vue.prototype.$TITLE = process.env.VUE_APP_TITLE