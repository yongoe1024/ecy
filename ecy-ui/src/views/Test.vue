<template>
  <div>
    <!-- <e-dict name="性别"
            v-model="v"></e-dict>
  
    <e-editor v-model="v"></e-editor>  
     <el-button @click="pay">支付</el-button>
    <el-button @click="dd">aa{{p}}</el-button>
      <e-upload>收拾 </e-upload>
  
    <e-dict name="启用"
            :tag="true"
            :value="true"></e-dict>
    <e-dict name="启用"
            v-model="a"></e-dict> -->
    <e-chunk-upload></e-chunk-upload>
    <e-download-file file-path="/1/2dd29b76af857983a2dc84ba33327330.mp4"> 下载</e-download-file>
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