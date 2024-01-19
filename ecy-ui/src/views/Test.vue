<template>
  <div>
    <e-dict name="启用"
            type="checkboxtag"
            :value="v"></e-dict>
    <e-chunk-upload></e-chunk-upload>
  </div>
</template>


<script>
import eChunkUpload from '@/components/chunk-upload/e-chunk-upload.vue'
export default {
  components: { eChunkUpload },
  props: {},
  computed: {},
  watch: {},
  data () {
    return {
      v: '1,2',
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