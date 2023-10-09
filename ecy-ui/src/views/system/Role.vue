<template>
  <div>
    <div style="display: flex;">
      <el-input class="addInput"
                placeholder="请输入角色代码"
                v-model="form.code"> </el-input>
      <el-input class="addInput"
                v-model="form.name"
                placeholder="请输入中文名"
                @keydown.enter.native="handleAddRole"></el-input>
      <el-button size="small"
                 type="primary"
                 icon="el-icon-plus"
                 @click="handleAddRole">添加角色</el-button>
    </div>

    <!-- 手风琴折叠面板 -->
    <el-collapse accordion
                 style="width:500px;margin:8px"
                 @change="handleCollapseChange"
                 v-model="activeName">
      <el-collapse-item :title="r.name"
                        :name="r.id"
                        v-for="(r, index) in roleList"
                        :key="index">
        <el-card>
          <!-- 卡片头 -->
          <div slot="header"
               class="clearfix">
            <span>可访问资源</span>
            <el-button style="float: right; padding: 3px 0; color: #ff0000"
                       type="text"
                       icon="el-icon-delete"
                       @click="handleDeleteRole(r)"></el-button>
          </div>

          <!-- 树形 -->
          <!-- :check-strictly="checkStrictly" -->
          <div>
            <el-tree ref="tree"
                     :default-expand-all="true"
                     :key="index"
                     show-checkbox
                     :default-checked-keys="r.menuIds"
                     :data="menuList"
                     :check-strictly="true"
                     :props="{children:'children',label:'name'}"
                     node-key="id"></el-tree>
            <div style="display:flex;justify-content:flex-end">
              <el-button size="mini"
                         @click="activeName = ''">取消</el-button>
              <el-button size="mini"
                         type="primary"
                         @click="handleUpdate(r.id, index)">确认修改</el-button>
            </div>
          </div>
        </el-card>
      </el-collapse-item>
    </el-collapse>
  </div>
</template>

<script>
export default {
  data () {
    return {
      // 手风琴默认打开位置
      activeName: '',
      checkStrictly: true,
      menuList: [],
      roleList: [],
      form: {
        name: '',
        code: '',
      },
    }
  },
  mounted () {
    this.initAllRoles()
    this.initAllMenus()
  },
  methods: {
    handleConnect () {
      this.checkStrictly = !this.checkStrictly
    },
    handleCollapseChange () {
      this.initAllRoles()
    },
    //删除角色
    handleDeleteRole (role) {
      this.$confirm('此操作将永久删除[' + role.name + ']角色, 是否继续?', '提示', { type: 'warning' }).then(() => {
        this.axios.post('/system/role/delete/' + role.id).then(() => this.initAllRoles())
      }).catch(e => { })
    },
    //添加角色
    handleAddRole () {
      if (this.form.name && this.form.code) {
        this.axios.post('/system/role/add', this.form).then(() => {
          this.initAllRoles()
          this.form.name = ''
          this.form.code = ''
        }).catch(e => { })
      } else {
        this.$message.error('字段不能为空')
      }
    },
    //更新 角色-菜单 index为折叠面板的id
    handleUpdate (rid, index) {
      let tree = this.$refs.tree[index]
      //  父节点与子节点 ，传入的时候无关联
      let selectedKeys = tree.getCheckedNodes(false, true)
      let menuIds = selectedKeys.map(item => item.id)
      let url = '/system/role/menu/update?roleId=' + rid + '&menuIds=' + menuIds
      this.axios.post(url)
    },
    // 查询全部菜单
    initAllMenus () {
      this.axios.post('/system/menu/tree').then(data => this.menuList = data).catch(e => { })
    },
    // 查询 全部角色
    initAllRoles () {
      this.checkStrictly = true
      this.axios.post('/system/role/list').then(data => {
        this.roleList = data
        this.$nextTick(() => this.checkStrictly = false)
      }).catch(e => { })
    },
  },
}

</script>
<style scoped>
.addInput {
  width: 250px;
  margin-right: 6px;
}
.card {
  width: 500px;
}
</style>