<template>
  <div>
    <e-dict name="性别"
            v-model="v"></e-dict>
    <e-upload>
    </e-upload>
    <e-chunk-upload></e-chunk-upload>
    <e-editor v-model="v"></e-editor>
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
  methods: {},
}
</script>

<style  >
</style>