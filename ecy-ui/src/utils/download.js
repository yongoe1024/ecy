import Vue from 'vue'

/**
 * a标签下载
 * @param {*路径} url 
 * @param {*参数} params 
 */
function download (url, params) {
  var a = document.createElement("a")
  var str = ''
  if (params) {
    str = Object.keys(params).map(key => {
      if (typeof (params[key]) !== "undefined" && params[key] !== null) {
        return `${key}=${params[key]}`
      }
    }).join('&')
  }
  a.href = `${Vue.prototype.$BASE_URL}${url}?Authorization=${window.localStorage.getItem('token')}&${str}`
  a.style.display = "none"
  a.target = '_blank'
  document.body.appendChild(a)
  a.click()
  a.remove()
}

export default download

Vue.prototype.download = download