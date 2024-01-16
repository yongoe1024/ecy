<template>
  <div>
    <el-submenu :index="item.name"
                v-if="item.type==1"
                v-show="item.isShow">
      <template slot="title">
        <i :class="item.icon"
           style="color:#FFFFFF;font-size:18px"></i>
        <span style="margin-left:5px;font-size:15px">{{ item.name }}</span>
      </template>

      <div v-for="(x, indexj) in item.children"
           :key="indexj">
        <e-menu-item :item="x"> </e-menu-item>
      </div>
    </el-submenu>
    <el-menu-item v-if="item.type==2"
                  style="width:180px"
                  :index="item.path"
                  v-show="item.isShow">
      <i :class="item.icon"
         style="color:#FFFFFF;font-size:15px"></i>
      <span style="font-size:15px">{{ item.name }}</span>
    </el-menu-item>
  </div>
</template>


<script>
export default {
  props: ["item"],
  mounted () { },
  update () { },
  methods: {},
}

</script>
<style scoped>
/*隐藏文字*/
.el-menu--collapse .el-submenu__title span {
  display: none;
}
/*隐藏 > */
.el-menu--collapse .el-submenu__title .el-submenu__icon-arrow {
  display: none;
}
</style>