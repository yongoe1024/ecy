<template>
  <div>
    <!-- <e-dict name="性别"
            v-model="v"></e-dict>
    <e-upload>
    </e-upload>
    <e-chunk-upload></e-chunk-upload>
    <e-editor v-model="v"></e-editor> -->
    <el-button @click="pay">支付</el-button>
  </div>
</template>


<script>
import eEditor from '@/components/e-editor.vue'
export default {
  components: { eEditor },
  props: {},
  computed: {},
  watch: {},
  data () {
    return {
      v: '',
    }
  },
  mounted () { },
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