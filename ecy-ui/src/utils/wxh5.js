import axois from './request'

function wxconfig (data) {
  return axois({
    url: '/system/vote/config?url=' + data,
    method: 'post',
  })
}
window.localStorage.setItem("url", location.href.split('#')[0])
wxconfig(encodeURIComponent(location.href.split('#')[0])).then(respon => {
  if (respon) {
    //  alert("当前签名:" + respon.data.signature)
    jWeixin.config({
      debug: false, // 开启调试模式,调用的所有api的返回值会在客户端alert出来，若要查看传入的参数，可以在pc端打开，参数信息会通过log打出，仅在pc端时才会打印。
      appId: appId, // 必填，公众号的唯一标识
      timestamp: respon.data.timestamp, // 必填，生成签名的时间戳
      nonceStr: respon.data.nonceStr, // 必填，生成签名的随机串
      signature: respon.data.signature, // 必填，签名
      jsApiList: [
        'updateAppMessageShareData', 'onMenuShareAppMessage',
        'onMenuShareTimeline', "checkJsApi",
        "updateTimelineShareData"
      ] // 必填，需要使用的JS接口列表
    })
  }
})
jWeixin.checkJsApi({
  jsApiList: ['updateAppMessageShareData'], // 需要检测的JS接口列表，所有JS接口列表见附录2,
  success: function (res) {
    //alert("检测成功")
  }
}),
  jWeixin.ready(function () { //需在用户可能点击分享按钮前就先调用
    //  alert("注册分享:" + window.localStorage.getItem("url"))
    jWeixin.updateAppMessageShareData({
      title: '红花郎红红火火闹元宵', // 分享标题
      desc: '快来为我投票吧！', // 分享描述
      imgUrl: 'http://footorhead.cn/xxx/share.jpg', // 分享图标
      link: window.localStorage.getItem("url"), // 分享链接，该链接域名或路径必须与当前页面对应的公众号JS安全域名一致
      success: function () {
        // 设置成功
        console.log("分享成功！！")
      }
    })
  })
jWeixin.error(function (res) {
  // config信息验证失败会执行error函数，如签名过期导致验证失败，具体错误信息可以打开config的debug模式查看，也可以在返回的res参数中查看，对于SPA可以在这里更新签名。
  console.log("失败")
})