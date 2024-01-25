import { Loading } from 'element-ui'

let loading
export function startLoading () {
  loading = Loading.service({
    lock: true,
    text: '拼命加载中...',
    spinner: 'el-icon-loading',
    background: 'rgba(0, 0, 0, 0.2)'
  })
}
export function endLoading () {
  loading.close()
}