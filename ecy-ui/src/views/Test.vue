<template>
  <div>
  </div>
</template>

<script>
export default {
  data () {
    return {
    }
  },
  mounted () {
  },
  methods: {
    pay () {
      this.axios.get('/pay').then(res => {
        let myWindow = window.open('', '_parent', 'width:90%,height:90%')
        myWindow.document.write(res) //info为html的字符串
        myWindow.document.close()//必须关闭流，否则表单不生效
      })
    },
  },
}
</script>

<style  >
</style>