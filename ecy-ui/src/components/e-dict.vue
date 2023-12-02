<template>
  <div>
    <el-select v-if="type==='select'"
               v-model="myValue"
               :prefix-icon="icon"
               :size="size"
               :placeholder="placeholder">
      <el-option v-for="item in dataList"
                 :key="item.id"
                 :label="item.dictKey"
                 :value="item.dictValue">
      </el-option>
    </el-select>
    <el-radio v-else-if="type==='radio'"
              v-for="item in dataList"
              :key="item.id"
              v-model="myValue"
              :label="item.dictValue">{{item.dictKey}}</el-radio>

  </div>
</template>

<script>
export default {
  props: {
    value: {
      type: String,
      default: () => ''
    },
    name: {
      type: String,
      default: () => ''
    },
    placeholder: {
      type: String,
      default: () => ''
    },
    icon: {
      type: String,
      default: () => ''
    },
    size: {
      type: String,
      default: () => 'medium' // medium , small , mini
    }
  },
  watch: {
    value: {
      immediate: true,
      handler (nv) {
        this.myValue = nv
      },
    },
    myValue: {
      immediate: true,
      handler (nv) {
        this.$emit("input", nv)
      }
    },
  },
  data () {
    return {
      dataList: [],
      type: 1,
      myValue: null
    }
  },
  mounted () {
    this.axios.post('/user/getdict?name=' + this.name).then(data => {
      this.dataList = data.list
      this.type = data.type
    }).catch(e => { })
  }
}
</script>
<style scoped>
</style>