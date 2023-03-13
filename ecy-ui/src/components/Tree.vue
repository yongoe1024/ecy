<template>
  <div>
    <el-popover placement="bottom"
                v-model="popover"
                width="400"
                trigger="click">
      <el-tree :data="dataList"
               :default-expand-all="open_all"
               :props="tree_props"
               @node-click="handleNodeClick"></el-tree>
      <el-input v-model="label"
                readonly
                slot="reference"
                :placeholder="placeholder" />
    </el-popover>
  </div>
</template>

<script>
export default {
  props: {
    value: {
      default: ''
    },
    //原为element的tree组件的props参数
    tree_props: {
      default: () => { return { children: 'children', label: 'name', keyname: 'id' } }
    },
    //树形 折叠状态
    open_all: {
      type: Boolean,
      default: false
    },
    // 请求 tree接口
    url: {
      type: String,
      default: ''
    },
    // tree数据
    data: {
      type: Array,
      default: []
    },
    placeholder: {
      type: String,
      default: ''
    },
  },
  watch: {
    value (newVal) {
      let value = this.value + ''
      if (value == null) {
        this.label = null
        return
      }
      if (value === '0') {
        this.label = "根目录"
        return
      }
      this.getChild(this.dataList, value)
    }
  },
  data () {
    return {
      label: '',
      popover: false,   //popover框显示状态
      dataList: [],
    }
  },
  mounted () {
    if (this.data.length > 0) {
      this.dataList = this.data
    }
    else if (this.url)
      this.axios.post(this.url).then(data => {
        this.dataList = data
      })
    else {
      this.$message.error("tree组件没有数据")
    }
  },
  methods: {
    handleNodeClick (data) {
      this.popover = false
      this.label = data[this.tree_props.label]
      this.$emit('input', data[this.tree_props.keyname])
    },
    getChild (list, id) {
      list.forEach((el) => {
        if (el[this.tree_props.children] && el[this.tree_props.children] instanceof Array) {
          this.getChild(el[this.tree_props.children], id)   //递归调用 传入子数组
        }
        if (el[this.tree_props.keyname] === id) {
          this.label = el[this.tree_props.label]
          this.$emit('input', el[this.tree_props.keyname])
        }
      })
    }
  }
}

</script>
<style scoped>
</style>