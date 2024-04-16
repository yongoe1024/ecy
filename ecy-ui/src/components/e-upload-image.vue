<template>
  <div>
    <el-upload :action="$BASE_URL +  '/upload/single'"
               list-type="picture-card"
               :on-success="handleUploadSuccess"
               :before-upload="handleBeforeUpload"
               :limit="limit"
               :on-error="handleUploadError"
               :on-exceed="handleExceed"
               :on-remove="handleRemove"
               :show-file-list="true"
               :headers="headers"
               :file-list="fileList"
               :on-preview="handlePictureCardPreview">
      <i class="el-icon-plus"></i>
    </el-upload>

    <!-- 上传提示 -->
    <div class="el-upload__tip"
         slot="tip"
         v-if="showTip">请上传
      <template v-if="fileSize"> 大小不超过 <b style="color: #f56c6c">{{ fileSize }}MB</b> </template>
      <template v-if="fileType"> 格式为 <b style="color: #f56c6c">{{ fileType.join("/") }}</b> </template>的文件
    </div>

    <el-dialog :visible.sync="dialogVisible"
               title="预览"
               append-to-body>
      <img :src="dialogImageUrl"
           style="display: block; max-width: 100%; margin: 0 auto" />
    </el-dialog>
  </div>
</template>
 
<script>
export default {
  props: {
    value: [String, Object, Array],
    // 图片数量限制
    limit: {
      type: Number,
      default: null,
    },
    // 大小限制(MB)
    fileSize: {
      type: Number,
      default: 5,
    },
    // 文件类型, 例如['png', 'jpg', 'jpeg']
    fileType: {
      type: Array,
      default: () => ["png", "jpg", "jpeg"],
    },
    // 是否显示提示
    isShowTip: {
      type: Boolean,
      default: true
    }
  },
  data () {
    return {
      dialogImageUrl: "",
      dialogVisible: false,
      hideUpload: false,
      uploadImgUrl: "", // 上传的图片服务器地址
      headers: {
        Authorization: window.localStorage.getItem("token")
      },
      fileList: []
    }
  },
  watch: {
    value: {
      handler (val) {
        if (val) {
          // 首先将值转为数组
          const list = Array.isArray(val) ? val : this.value.split(',')
          // 然后将数组转为对象数组
          this.fileList = list.map(item => {
            if (typeof item === "string") {
              item = { name: item, url: item }
            }
            return item
          })
        } else {
          this.fileList = []
          return []
        }
      },
      deep: true,
      immediate: true
    }
  },
  computed: {
    // 是否显示提示
    showTip () {
      return this.isShowTip && (this.fileType || this.fileSize)
    },

  },
  methods: {
    // 删除图片
    handleRemove (file, fileList) {
      const findex = this.fileList.map(f => f.name).indexOf(file.name)
      if (findex > -1) {
        this.fileList.splice(findex, 1)
        this.$emit("input", this.listToString(this.fileList))
      }
    },
    // 上传成功回调
    handleUploadSuccess (res) {
      if (res.code == 200) {
        this.fileList.push({ name: res.data, url: res.data })
        this.$emit("input", this.listToString(this.fileList))
        this.loading.close()
      } else {
        this.$message.error(res.message)
        this.loading.close()
      }
    },
    // 上传前loading加载
    handleBeforeUpload (file) {
      let isImg = false
      if (this.fileType.length) {
        let fileExtension = ""
        if (file.name.lastIndexOf(".") > -1) {
          fileExtension = file.name.slice(file.name.lastIndexOf(".") + 1)
        }
        isImg = this.fileType.some((type) => {
          if (file.type.indexOf(type) > -1) return true
          if (fileExtension && fileExtension.indexOf(type) > -1) return true
          return false
        })
      } else {
        isImg = file.type.indexOf("image") > -1
      }

      if (!isImg) {
        this.$message.error(
          `文件格式不正确, 请上传${this.fileType.join("/")}图片格式文件!`
        )
        return false
      }
      if (this.fileSize) {
        const isLt = file.size / 1024 / 1024 < this.fileSize
        if (!isLt) {
          this.$message.error(`上传头像图片大小不能超过 ${this.fileSize} MB!`)
          return false
        }
      }
      this.loading = this.$loading({
        lock: true,
        text: "上传中",
        background: "rgba(0, 0, 0, 0.7)",
      })
    },
    // 文件个数超出
    handleExceed () {
      this.$message.error(`上传文件数量不能超过 ${this.limit} 个!`)
    },
    // 上传失败
    handleUploadError (res) {
      this.$message({
        type: "error",
        message: "上传失败",
      })
      this.loading.close()
    },
    // 预览
    handlePictureCardPreview (file) {
      this.dialogImageUrl = file.url
      this.dialogVisible = true
    },
    // 对象转成指定字符串分隔
    listToString (list) {
      return list.map(item => item.name).join(",")
    }
  }
};
</script>
<style scoped >
</style>