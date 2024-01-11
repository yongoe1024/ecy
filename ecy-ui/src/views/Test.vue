<template>
  <div>
    <!-- <e-dict name="性别"
            v-model="v"></e-dict>
    <e-chunk-upload></e-chunk-upload>
    <e-editor v-model="v"></e-editor>  
     <el-button @click="pay">支付</el-button>
    <el-button @click="dd">aa{{p}}</el-button>
      <e-upload>收拾 </e-upload>
    <e-download> 下载</e-download>
    <e-dict name="启用"
            :tag="true"
            :value="true"></e-dict>
    <e-dict name="启用"
            v-model="a"></e-dict> -->
  </div>
</template>


<script>
export default {
  props: {},
  computed: {},
  watch: {},
  data () {
    return {
      v: '',
      p: '',
      a: true
    }
  },
  mounted () {
    this.axios.post('/system/user/export').then(res => {
    }).catch(err => {
    })
  },
  methods: {
    dd () {
      this.$downloadRequest('/download?filePath=', {}, (progress) => { this.p = progress })
    },
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