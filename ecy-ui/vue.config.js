const { defineConfig } = require('@vue/cli-service')

module.exports = defineConfig({
  transpileDependencies: true,
  lintOnSave: false,
  publicPath: '/',
  devServer: {
    //host: "localhost",
    port: 8080, // 端口号
    allowedHosts: [
      'all', // 允许访问的域名地址
    ],
    proxy: {
      '/api': {
        target: 'http://localhost:8081',
        pathRewrite: { '^/api': '/' },
        ws: true,
        changeOrigin: true
      }
    }
  }
})
