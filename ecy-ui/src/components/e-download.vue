<template>
  <div>
    <el-button type="warning"
               :icon="icon"
               :disabled="loading"
               plain
               :size="size"
               @click="handleExport">
      <slot v-if="!loading"></slot>
      <span v-else> {{progressNum}}%</span>
    </el-button>
  </div>
</template>

<script>
export default {
  props: {
    data: {
      default: () => { return {} }
    },
    filePath: {
      type: String,
      default: () => ''
    },
    size: {
      type: String,
      default: () => '' //medium/small/mini
    },
  },
  data () {
    return {
      loading: false,
      progressNum: 0,
      icon: 'el-icon-download'
    }
  },
  methods: {
    handleExport () {
      if (!this.filePath) return this.$message({ message: '文件路径不能为空', type: 'error' })
      this.loading = true
      this.icon = 'el-icon-loading'
      this.$downloadRequest('/download?filePath=' + this.filePath, {}, (progress) => { this.progressNum = progress }).then(() => {
        this.loading = false
        this.icon = 'el-icon-download'
      }).catch(() => {
        this.loading = false
        this.icon = 'el-icon-download'
      })
    },
  },
}
</script>
<style scoped>
</style>