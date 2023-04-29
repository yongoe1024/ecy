<template>
  <div>
    <el-upload :show-file-list="false"
               :before-upload="beforeUpload"
               :on-success="onSuccess"
               :on-error="onError"
               :headers="headers"
               :disabled="uploadButtonDisabled"
               :action="uploadURL">
      <el-button :icon="uploadButtonIcon"
                 :size="size"
                 type="primary">{{uploadButtonText}}</el-button>
    </el-upload>
  </div>
</template>

<script>
export default {
  props: {
    url: {
      type: String,
      default: ''
    },
    size: {
      type: String,
      default: 'small'  //	medium/small/mini
    },
  },
  data () {
    return {
      uploadURL: this.$BASE_URL + this.url,
      uploadButtonText: '导入数据',
      uploadButtonIcon: 'el-icon-upload2',
      uploadButtonDisabled: false,
      headers: {
        Authorization: window.localStorage.getItem("token")
      },
    }
  },
  methods: {
    beforeUpload () {
      this.uploadButtonText = '正在导入'
      this.uploadButtonIcon = 'el-icon-loading'
      this.uploadButtonDisabled = true
    },
    onSuccess (data) {
      this.$message({ message: data.message, type: data.code === 200 ? 'success' : 'error' })
      this.uploadButtonText = '导入数据'
      this.uploadButtonIcon = 'el-icon-upload2'
      this.uploadButtonDisabled = false
      this.$emit('on-success', data.data)
    },
    onError () {
      this.$message({ message: '导入失败', type: 'error' })
      this.uploadButtonText = '导入数据'
      this.uploadButtonDisabled = false
      this.uploadButtonIcon = 'el-icon-upload2'
    },
  },
}
</script>
<style scoped>
</style>