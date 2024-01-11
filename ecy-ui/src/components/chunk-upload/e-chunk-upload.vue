<template>
  <div class="upload">
    <el-button style="margin: 10px;"
               size="small"
               type="success"
               :disabled="fileList.length ==0"
               @click="submitUpload">开始上传</el-button>
    <el-button style="margin: 10px;"
               size="small"
               type="primary"
               :disabled="fileList.length ==0"
               @click="fileList=[]">清空文件列表</el-button>
    <el-button style="margin: 10px;"
               size="small"
               type="primary"
               @click="stopUpload()">取消上传</el-button>
    <el-upload drag
               action="#"
               :file-list="fileList"
               ref="upload"
               :http-request="uploadRequest"
               :auto-upload="false"
               :on-change="handleChange"
               :on-preview="handlePreview"
               :on-remove="handleRemove"
               :on-exceed="handleExceed"
               :before-upload="beforeUpload"
               :before-remove="beforeRemove"
               :limit="limit"
               multiple>
      <i class="el-icon-upload"></i>
      <div class="el-upload__text">将文件拖到此处，或<em>点击上传</em></div>

      <div class="el-upload__tip"
           slot="tip">只能上传不超过50M</div>
    </el-upload>
  </div>
</template>

<script>
import { uploadChunk, stop } from '@/components/chunk-upload/upload.js'
export default {
  data () {
    return {
      fileList: [],
      limit: 3,
    }
  },
  methods: {
    //停止上传
    stopUpload () {
      stop()
    },
    //禁止异步执行
    uploadRequest (fileObject) {
      //fileObject中的file是原生File，而fileList中的file是element包装过的
      uploadChunk(fileObject).then((res) => {
        console.log(res)
        fileObject.onSuccess()
        this.$message({ message: `上传 ${fileObject.file.name} 成功`, type: 'success' })
      }).catch((err) => {
        console.log(err)
        fileObject.onError()
        this.$message({ message: `${fileObject.file.name} 失败 ---失败原因：${err},请重试`, type: 'error' })
      })
    },
    submitUpload () {
      //手动按钮上传文件，默认方法被uploadRequest覆盖，某些回调函数无法自动执行
      this.$refs.upload.submit()
    },
    // handleProgress (event, file, fileList) {
    //   console.log(event)
    //   // 上传过程中触发的回调函数
    // },
    // onSuccess (data) {
    //   // 上传成功时的回调函数
    //   this.$message({ message: '上传成功', type: 'success' })
    // },
    // onError () {
    //   // 上传失败时的回调函数
    //   this.$message({ message: '上传失败', type: 'error' })
    // },
    handleChange (file, fileList) {
      //文件状态改变时的钩子，添加文件、上传成功和上传失败时都会被调用
      this.fileList = fileList
    },
    handleRemove (file, fileList) {
      //文件列表移除文件时的钩子
      this.$refs.upload.onChange(file, fileList)
    },
    handlePreview (file) {
      //点击文件列表中的文件时的钩子
      console.log(file)
    },
    handleExceed (files, fileList) {
      //文件超出个数限制时的钩子
      this.$message.warning(`当前限制选择 3 个文件，本次选择了 ${files.length} 个文件，共选择了 ${files.length + fileList.length} 个文件`)
    },
    beforeUpload (file) {
      // 上传前的钩子，参数为上传的文件，若返回 false 或者 Promise 则停止上传。
      console.log('类型：' + file.type)
      let type = !!file.type
      let size = file.size <= 1024 * 1024 * 5 * 10
      if (!type) {
        this.$message.error('上传头只能是 XXX 格式!')
      }
      if (!size) {
        this.$message.error('上传大小不能超过 50 MB!')
      }
      return type && size
    },
    beforeRemove (file, fileList) {
      //文件列表移除文件时的钩子，返回 false 时不移除
      return this.$confirm(`确定移除 ${file.name}？`)
    }

  }
};
</script>

<style scoped>
.upload {
  width: 400px;
  padding: 20px;
  box-shadow: 0 5px 10px rgba(0, 21, 41, 0.7);
  z-index: 10;
}
</style>

