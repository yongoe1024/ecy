<template>
  <div>
    <el-button type="warning"
               icon="el-icon-download"
               plain
               :size="size"
               @click="handleExport">
      <slot></slot>
    </el-button>
  </div>
</template>

<script>
import download from '@/utils/download.js'
export default {
  props: {
    filePath: {
      type: String,
      default: () => ''
    },
    size: {
      type: String,
      default: () => '' //medium/small/mini
    },
  },
  methods: {
    handleExport () {
      if (!this.filePath) return this.$message({ message: '下载文件不能为空', type: 'error' })
      download('/download', { filePath: this.filePath })
    },
  },
}
</script>
<style scoped>
</style>