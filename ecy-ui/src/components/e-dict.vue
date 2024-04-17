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
    <el-checkbox-group v-else-if="type==='checkbox'"
                       v-model="myCheckbox">
      <el-checkbox v-for="item in dataList"
                   :key="item.id"
                   :label="item.dictValue">{{item.dictKey}}</el-checkbox>
    </el-checkbox-group>
    <el-radio v-else-if="type==='radio'"
              v-for="item in dataList"
              :key="item.id"
              v-model="myValue"
              :label="item.dictValue">{{item.dictKey}}</el-radio>
    <span v-else-if="type==='tag'"
          v-for="item in dataList"
          :key="item.id">
      <el-tag :size="size"
              effect="dark"
              :style="{'background-color': item.color,'border-color': item.color}"
              v-if="myValue == item.dictValue">{{item.dictKey}}</el-tag>
    </span>
    <span v-else-if="type==='checkboxtag'"
          v-for="item in myCheckboxList"
          :key="item.id">
      <el-tag :size="size"
              effect="dark"
              style="margin: 2px;"
              :style="{'background-color': item.color,'border-color': item.color}">{{item.dictKey}}</el-tag>
    </span>
  </div>
</template>

<script>
export default {
  props: {
    value: {
      default: () => null
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
      default: () => '' // medium , small , mini
    },
    type: {
      type: String,
      default: () => 'select' // select , radio , tag , checkbox , checkboxtag
    }
  },
  watch: {
    value: {
      immediate: true,
      handler (nv) {
        if (nv != null) {
          nv = nv + ''
          this.myValue = nv
          this.myCheckbox = !!nv ? nv.split(',') : []
        }
        else
          this.myValue = nv
      },
    },
    myValue: {
      immediate: true,
      handler (nv) {
        this.$emit("input", nv)
      }
    },
    myCheckbox: {
      immediate: true,
      handler (nv) {
        this.getCheckboxList()
        this.$emit("input", nv + '')
      }
    },
  },
  data () {
    return {
      dataList: [],
      myValue: null,
      myCheckbox: [],
      myCheckboxList: []
    }
  },
  mounted () {
    window.sessionStorage.getItem('dict_' + this.name) ? this.dataList = JSON.parse(window.sessionStorage.getItem('dict_' + this.name)) : this.getData()
    this.getCheckboxList()
  },
  methods: {
    getData () {
      this.axios.post('/getDict?name=' + this.name).then(data => {
        this.dataList = data
        window.sessionStorage.setItem('dict_' + this.name, JSON.stringify(data))
        this.getCheckboxList()
      })
    },
    getCheckboxList () {
      if (this.type == 'checkboxtag') {
        //从dataList中找出myCheckboxList
        this.myCheckboxList = this.dataList.filter(item => {
          return this.myCheckbox.includes(item.dictValue)
        })
      }
    }
  }
}
</script>
<style scoped>
</style>