<template>
  <div>
    <el-submenu :index="item.name"
                v-if="item.type==1"
                v-show="item.isShow">
      <template slot="title">
        <i :class="item.icon"></i>
        <span>{{ item.name }}</span>
      </template>
      <div v-for="(x,indexj) in item.children"
           :key="indexj">
        <e-menu-item :item="x"> </e-menu-item>
      </div>
    </el-submenu>

    <el-menu-item v-if="item.type==2"
                  style="width:180px"
                  :index="item.path"
                  v-show="item.isShow">
      <i style="margin-right:5px"
         :class="item.icon"></i>
      <span>{{ item.name }}</span>
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
.el-menu--collapse .el-menu-item span {
  display: none;
}
span {
  font-size: 15px;
}
i {
  font-size: 18px;
  color: #ffffff;
}
</style>