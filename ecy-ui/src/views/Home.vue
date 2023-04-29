<template>
  <div>
    <div class="welcomeFont">
      欢迎来到{{$TITLE}}!
    </div>
  </div>
</template>


<script>
export default {
  components: {},
  props: {},
  computed: {},
  watch: {},
  data () {
    return {
    }
  },

  mounted () { },
  methods: {},
}

</script>
<style scoped>
.welcomeFont {
  margin: 12% auto;
  text-align: center;
  font-size: 70px;
  font-family: 楷体;
  color: #c7e3ff;
}
</style>