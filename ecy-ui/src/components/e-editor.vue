<template>
  <div>
    <vue-editor class="editor"
                useCustomImageHandler
                @image-added="handleImageAdded"
                v-model="content"></vue-editor>
    <!-- <span class="ql-editor"
          v-html="content"></span> -->
  </div>
</template>


<script>
import { VueEditor } from 'vue2-editor'
import axios from 'axios'
export default {
  components: {
    VueEditor
  },
  props: {
    value: {
      type: String,
      default: () => ''
    },
  },
  watch: {
    value: {
      immediate: true,
      handler (nv) {
        this.content = nv
      },
    },
    content: {
      immediate: true,
      handler (nv) {
        this.$emit("input", nv)
      }
    },
  },
  data () {
    return {
      content: '',
    }
  },
  methods: {
    handleImageAdded: function (file, Editor, cursorLocation, resetUploader) {
      var formData = new FormData()
      formData.append("file", file)
      axios({
        url: `${this.$BASE_URL}/upload/single`,
        method: "POST",
        data: formData,
        headers: { "Content-Type": "multipart/form-data", Authorization: window.localStorage.getItem("token") }
      })
        .then(result => {
          let url = result.data.data // Get url from response
          Editor.insertEmbed(cursorLocation, "image", url)
          resetUploader()
        })
        .catch(err => {
          this.$message({ message: '图片上传失败', type: 'error' })
          console.log(err)
        })
    }
  }
}
</script>
<style  >
.editor {
  margin: 5px;
  padding: 20px;
  box-shadow: 0 5px 5px rgba(0, 21, 41, 0.7);
}
</style>