const { defineConfig } = require('@vue/cli-service')
const Timestamp = new Date().getTime()
module.exports = defineConfig({
  // chainWebpack: config => {
  //   config.output.filename(`js/[name].${Timestamp}.js`)
  //   config.output.chunkFilename(`js/[name].${Timestamp}.js`)
  // },
  transpileDependencies: true, // 默认是false,如果你想要通过 Babel 显式转译一个依赖，可以在这个选项中列出来
  lintOnSave: false, // 关闭eslint
  publicPath: '/',   //如果放到jar中,需要修改为/ecy
  outputDir: 'dist', // 打包后的目录
  assetsDir: 'static', // 静态资源目录 (js, css, img, fonts)
  devServer: {
    client: {
      overlay: false, // 关闭错误提示
    },
    port: 8080,
    open: true, //配置自动启动浏览器
    allowedHosts: ['all'],  // 允许访问的域名地址  
    disableHostCheck: true,  // 解决内网穿透报错
    // 打包后就用nginx转发啦
    proxy: {
      '/ecy': {
        target: 'http://localhost:8081',
        ws: true,
        changeOrigin: true,
        // pathRewrite: { '^/ecy': 'ecy' }
      },
    }
  }
})
