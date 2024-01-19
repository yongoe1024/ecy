<template>
  <div>
    <!-- 搜索 -->
    <div class="head">
      <el-input v-model="queryParam.code"
                size="small"
                prefix-icon="el-icon-search"
                placeholder="请输入角色代码"></el-input>
      <el-input v-model="queryParam.name"
                size="small"
                prefix-icon="el-icon-search"
                placeholder="请输入中文名"></el-input>
    </div>
    <!-- 按钮 -->
    <div class="button">
      <el-button type="primary"
                 size="mini"
                 plain
                 icon="el-icon-search"
                 @click="getList">搜索</el-button>
      <el-button size="mini"
                 plain
                 icon="el-icon-refresh"
                 @click="resetQuery">重置</el-button>
      <el-button type="success"
                 size="mini"
                 plain
                 @click="handleShowAddEdit"
                 icon="el-icon-plus">添加</el-button>
    </div>

    <!-- 表格 -->
    <el-table :data="roleList"
              style="width: 100%"
              :header-cell-style="{background:'#eef1f6'}">
      <el-table-column align="center"
                       type="index"></el-table-column>
      <el-table-column prop="code"
                       label="角色代码"
                       align="center"></el-table-column>
      <el-table-column prop="name"
                       label="中文名"
                       align="center"></el-table-column>
      <el-table-column label="操作"
                       align="center"
                       fixed="right">
        <template slot-scope="scope">
          <el-button type="text"
                     size="mini"
                     icon="el-icon-edit"
                     @click="handleShowUpdateEdit(scope.row)">编辑</el-button>
          <el-button type="text"
                     size="mini"
                     icon="el-icon-delete"
                     @click="handleDelete(scope.row)">删除</el-button>
          <el-button type="text"
                     size="mini"
                     @click="handleRoleMenuEdit(scope.row)">菜单权限</el-button>
        </template>
      </el-table-column>
    </el-table>

    <!-- 分页 -->
    <el-pagination background
                   style="display:flex;justify-content:center;"
                   @size-change="handleSizeChange"
                   @current-change="handleCurrentChange"
                   :page-size="queryParam.size"
                   layout="total, sizes, prev, pager, next, jumper"
                   :total="total"></el-pagination>

    <el-dialog :visible.sync="dialogVisible"
               :title="dialogTitle"
               :destroy-on-close="true"
               @close="reset"
               :close-on-click-modal="false"
               width="600px">
      <el-form ref="form"
               :model="form"
               style="margin:20px"
               label-width="auto"
               :rules="rules">
        <el-form-item label="角色代码"
                      prop="code">
          <el-input v-model="form.code"
                    :disabled="form.id!=null"
                    placeholder="请输入角色代码" />
        </el-form-item>
        <el-form-item label="中文名"
                      prop="name">
          <el-input v-model="form.name"
                    placeholder="请输入中文名" />
        </el-form-item>
      </el-form>
      <span slot="footer">
        <el-button type="primary"
                   @click="handleAddOrUpdate">确 定</el-button>
        <el-button @click="dialogVisible = false">取 消</el-button>
      </span>
    </el-dialog>

    <!-- 编辑菜单权限 -->
    <el-dialog :visible.sync="dialogVisibleMenu"
               title="菜单权限"
               :destroy-on-close="true"
               :close-on-click-modal="false"
               width="600px">
      <el-form label-width="auto"
               style="margin:20px">
        <el-form-item>
          <!-- 树 -->
          <el-tree ref="tree"
                   :check-strictly="treeCheckStrictly"
                   :default-expand-all="true"
                   show-checkbox
                   :default-checked-keys="form.menuIds"
                   :data="menuList"
                   :props="{children:'children',label:'name'}"
                   node-key="id"></el-tree>
        </el-form-item>

      </el-form>
      <span slot="footer">
        <el-button type="primary"
                   @click="handlMenuUpdate()">确认修改</el-button>
        <el-button @click="dialogVisibleMenu = false">关闭</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
export default {
  components: {},
  props: {},
  data () {
    return {
      total: 0,

      dialogVisible: false,
      dialogVisibleMenu: false,
      dialogTitle: '',

      treeCheckStrictly: true,

      menuList: [],
      roleList: [],
      form: {
        id: null,
        code: null,
        name: null,
        menuIds: [],
      },
      queryParam: {
        current: 1,
        size: 10,
        id: null,
        code: null,
        name: null,
      },
      rules: {
        code: [{ required: true, message: '请输入角色代码', trigger: 'change' }],
        name: [{ required: true, message: '请输入中文名', trigger: 'change' }],
      },
    }
  },
  mounted () {
    this.getList()
    this.getMenus()
  },
  methods: {
    handlMenuUpdate () {
      let tree = this.$refs.tree
      //  父节点与子节点 ，传入的时候无关联，1. 是否只是叶子节点，默认值为 false 2. 是否包含半选节点，默认值为 false
      let selectedKeys = tree.getCheckedNodes(false, true)
      let menuIds = selectedKeys.map(item => item.id)
      let url = `/system/role/menu/update?roleId=${this.form.id}&menuIds=${menuIds}`
      this.axios.post(url).then(() => {
        this.dialogVisibleMenu = false
        this.getList()
      }).catch(e => { })
    },
    handleRoleMenuEdit (row) {
      this.dialogVisibleMenu = true
      Object.assign(this.form, row)
    },
    reset () {
      this.form = this.$options.data().form
    },
    resetQuery () {
      this.queryParam = this.$options.data().queryParam
    },
    // 改变页码
    handleSizeChange (val) {
      this.queryParam.size = val
      this.getList()
    },
    // 点击页数
    handleCurrentChange (val) {
      this.queryParam.current = val
      this.getList()
    },
    handleShowAddEdit () {
      this.dialogTitle = '添加'
      this.dialogVisible = true
    },
    handleShowUpdateEdit (row) {
      this.dialogTitle = '修改'
      Object.assign(this.form, row)
      this.dialogVisible = true
    },
    handleAddOrUpdate () {
      this.$refs.form.validate((valid) => {
        if (valid) {
          if (this.form.id) {
            this.axios.post('/system/role/update', this.form).then(() => {
              this.getList()
              this.dialogVisible = false
            }).catch(e => { })
          } else {
            this.axios.post('/system/role/add', this.form).then(() => {
              this.getList()
              this.dialogVisible = false
            }).catch(e => { })
          }
        }
      })
    },
    handleDelete (row) {
      this.$confirm('此操作将永久删除[' + row.name + ']角色, 是否继续?', '提示', { type: 'warning' }).then(() => {
        this.axios.post('/system/role/delete/' + row.id).then(() => this.getList()).catch(e => { })
      }).catch(e => { })
    },
    // 初始化菜单数据
    getMenus () {
      this.axios.post('/system/menu/tree').then(data => this.menuList = data).catch(e => { })
    },
    // 初始化数据
    getList () {
      this.axios.post(`/system/role/page`, this.queryParam).then(data => {
        this.roleList = data.list
        this.total = data.total - 0
      }).catch(e => { })
    },
  },
}
</script>
<style scoped>
.head {
  display: flex;
  flex-wrap: wrap;
}
.head .el-input {
  width: 200px !important;
}
.head * {
  margin: 0 8px 8px 0;
}

.button {
  margin: 0 0 15px 0;
  display: flex;
}
.button * {
  margin: 0 8px 0 0;
}
</style>
