<template>
  <div>
    <el-select v-if="type===1"
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
    <el-radio v-else-if="type===2"
              v-for="item in dataList"
              :key="item.id"
              v-model="myValue"
              :label="item.dictValue">{{item.dictKey}}</el-radio>

  </div>
</template>

<script>
export default {
  props: {
    name: {
      type: String,
      default: ''
    },
    placeholder: {
      type: String,
      default: ''
    },
    icon: {
      type: String,
      default: ''
    },
    size: {
      type: String,
      default: 'small'
    }
  },
  watch: { 
    myValue (newVal) {
      this.$emit('input', newVal)
    }
  },
  data () {
    return {
      myValue: '',
      dataList: [],
      type: 1,
    }
  },
  mounted () {
    this.axios.post('/user/dict/getdict?name=' + this.name).then(data => {
      this.dataList = data.list
      this.type = data.type
    })
  }
}
</script>
<style scoped>
</style>