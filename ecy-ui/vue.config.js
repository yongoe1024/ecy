const { defineConfig } = require('@vue/cli-service')

module.exports = defineConfig({
  transpileDependencies: true,
  lintOnSave: false,
  publicPath: '/',
  devServer: {
    port: 8080,
    allowedHosts: [
      'all', // 允许访问的域名地址
    ],
    // 打包后就用nginx转发啦
    proxy: {
      '/api': {
        target: 'http://localhost:8081',
        pathRewrite: { '^/api': '/' },
        ws: true,
        changeOrigin: true
      },
      '/file': {
        target: 'http://localhost:8081',
        ws: true,
        changeOrigin: true
      }
    }
  }
})
