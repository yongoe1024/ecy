export default {
  data () {
    return {
      total: 0,
      queryParam: {
        current: 1,
        size: 10,
      },
    }
  },
  methods: {
    // 改变页码
    handleSizeChange (val) {
      this.queryParam.size = val
      this.getList()
    },
    // 点击页数
    handleCurrentChange (val) {
      this.queryParam.current = val
      this.getList()
    }
  }
}