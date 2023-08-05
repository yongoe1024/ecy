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
      '/ecy': {
        target: 'http://localhost:8081',
        ws: true,
        changeOrigin: true
      },
    }
  }
})
