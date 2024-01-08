<template>
  <div>
    <el-popover placement="bottom"
                v-model="popover"
                width="400"
                trigger="click">
      <el-tree :data="data"
               :default-expand-all="open_all"
               :props="tree_props"
               @node-click="handleNodeClick"></el-tree>
      <el-input v-model="label"
                readonly
                :size="size"
                slot="reference"
                :placeholder="placeholder" />
    </el-popover>
  </div>
</template>

<script>
export default {
  props: {
    value: {
      default: () => null
    },
    //原为element的tree组件的props参数
    tree_props: {
      default: () => { return { children: 'children', label: 'name', keyname: 'id' } }
    },
    //树形 折叠状态
    open_all: {
      type: Boolean,
      default: () => false
    },
    // tree数据
    data: {
      type: Array,
      default: () => []
    },
    placeholder: {
      type: String,
      default: () => ''
    },
    size: {
      type: String,
      default: () => '' //medium , small , mini
    }
  },
  watch: {
    value: {
      immediate: true,
      handler (newVal) {
        if (newVal == null) {
          this.label = null
          return
        }
        if (newVal == '0') {
          this.label = "根节点"
          return
        }
        this.getChild(this.data, newVal)
      }
    }
  },
  data () {
    return {
      label: '',
      popover: false,   //popover框显示状态
    }
  },
  mounted () {

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
.el-input {
  width: 200px !important;
}
</style>