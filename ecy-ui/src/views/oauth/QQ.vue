<template>
  <div></div>
</template>
<script>
export default {
  data () {
    return {
      code: null,
      state: null
    }
  },
  mounted () {
    this.code = this.$route.query.code
    this.state = this.$route.query.state
    this.axios.get(`/oauth/qq/callback?code=${this.code}&state=${this.state}`).then((result) => {
      window.localStorage.setItem('token', result)
      this.$router.replace('/index')
    }).catch((err) => {
      this.$router.replace('/login')
    })
  },
  methods: {},
}
</script>