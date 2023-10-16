const { defineConfig } = require('@vue/cli-service')
const Timestamp = new Date().getTime()
module.exports = defineConfig({
  configureWebpack: {
    output: { // 输出重构，打包编译后的文件名称 【模块名称.版本号(可选).时间戳】
      filename: `[name].${Timestamp}.js`,
      chunkFilename: `[name].${Timestamp}.js`
    },
  },
  css: {
    extract: { // 打包后css文件名称添加时间戳
      filename: `css/[name].${Timestamp}.css`,
      chunkFilename: `css/[name].${Timestamp}.css`
    }
  },
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
