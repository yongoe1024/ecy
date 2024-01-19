<template>
  <div>
    <!-- 搜索 -->
    <div class="head">
      <el-input size="small"
                prefix-icon="el-icon-search"
                placeholder="请输入姓名"
                v-model="queryParam.name"></el-input>
      <e-input-tree :data="departmentList"
                    size="small"
                    v-model="queryParam.departmentId"
                    :tree_props="{ children: 'children', label: 'name', keyname: 'id' }"
                    placeholder="请选择部门"></e-input-tree>
      <el-select v-model="queryParam.positionId"
                 placeholder="请选择职位"
                 size="small">
        <el-option v-for="item in positionList"
                   :key="item.id"
                   :label="item.name"
                   :value="item.id">
        </el-option>
      </el-select>
      <el-input size="small"
                prefix-icon="el-icon-search"
                placeholder="请输入邮箱"
                v-model="queryParam.email"></el-input>
      <el-input size="small"
                prefix-icon="el-icon-search"
                placeholder="请输入联系电话"
                v-model="queryParam.phone"></el-input>
      <el-select v-model="queryParam.enabled"
                 size="small"
                 placeholder="是否启用">
        <el-option label="是"
                   :value="true"></el-option>
        <el-option label="否"
                   :value="false"></el-option>
      </el-select>
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
      <e-upload size="mini"
                url="/system/user/upload"
                @success="getList">导入用户</e-upload>
      <el-button type="warning"
                 icon="el-icon-download"
                 plain
                 size="mini"
                 @click="handleExport">导出模板</el-button>
    </div>

    <!-- 表格 -->
    <el-table :data="dataList"
              :header-cell-style="{background:'#eef1f6'}"
              style="width: 100%">
      <el-table-column align="center"
                       type="index"></el-table-column>
      <el-table-column prop="username"
                       label="账号"
                       align="center"></el-table-column>
      <el-table-column prop="name"
                       label="姓名"
                       align="center"></el-table-column>
      <el-table-column prop="departmentName"
                       label="部门"
                       align="center"></el-table-column>
      <el-table-column prop="positionName"
                       label="职位"
                       align="center"></el-table-column>
      <el-table-column prop="avatar"
                       label="头像"
                       align="center">
        <template slot-scope="scope">
          <img :src="scope.row.avatar"
               style="width:40px">
        </template>
      </el-table-column>
      <el-table-column prop="enabled"
                       label="是否启用"
                       align="center">
        <template slot-scope="scope">
          <el-tag v-if="scope.row.enabled"
                  effect="dark"
                  type="success">启用
          </el-tag>
          <el-tag v-else
                  effect="dark"
                  type="danger">禁用
          </el-tag>
        </template>
      </el-table-column>
      <el-table-column prop="roleList"
                       label="角色"
                       align="center">
        <template slot-scope="scope">
          <el-tag v-for="item in scope.row.roleList"
                  style="margin:2px"
                  size="small"
                  :key="item.id">{{item.name}}</el-tag>
        </template>
      </el-table-column>
      <!--  <el-table-column prop="email"
                       label="邮箱"
                       width="200"
                       align="center"></el-table-column>
                       <el-table-column prop="lastIp"
                       label="上次登录ip"
                       width="100"
                       align="center"></el-table-column>
      <el-table-column prop="lastTime"
                       label="上次登录时间"
                       width="120"
                       align="center"></el-table-column>
      <el-table-column prop="phone"
                       label="联系电话"
                       align="center"></el-table-column>
      <el-table-column prop="remark"
                       label="备注"
                       align="center"></el-table-column> -->
      <el-table-column label="操作"
                       align="center"
                       width="140"
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
                     @click="handleUpdatePassword(scope.row)">修改密码</el-button>

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
               label-width="auto"
               style="margin:20px"
               :rules="rules">
        <el-form-item label="账号"
                      prop="username">
          <el-input v-model="form.username"
                    placeholder="请输入账号" />
        </el-form-item>
        <el-form-item label="密码"
                      v-if="dialogTitle == '添加'"
                      prop="password">
          <el-input v-model="form.password"
                    placeholder="请输入密码" />
        </el-form-item>
        <el-form-item label="部门"
                      prop="departmentId">
          <e-input-tree :data="departmentList"
                        v-model="form.departmentId"
                        :tree_props="{ children: 'children', label: 'name', keyname: 'id'}"
                        placeholder="请选择部门"></e-input-tree>
        </el-form-item>
        <el-form-item label="职位"
                      prop="positionId">
          <el-select v-model="form.positionId"
                     placeholder="请选择职位">
            <el-option v-for="item in positionList"
                       :key="item.id"
                       :label="item.name"
                       :value="item.id">
            </el-option>
          </el-select>
        </el-form-item>

        <el-form-item label="姓名"
                      prop="name">
          <el-input v-model="form.name"
                    placeholder="请输入姓名" />
        </el-form-item>
        <el-form-item label="邮箱"
                      prop="email">
          <el-input v-model="form.email"
                    placeholder="请输入邮箱" />
        </el-form-item>
        <el-form-item label="联系电话"
                      prop="phone">
          <el-input v-model="form.phone"
                    placeholder="请输入联系电话" />
        </el-form-item>
        <el-form-item label="备注"
                      prop="remark">
          <el-input v-model="form.remark"
                    placeholder="请输入备注" />
        </el-form-item>
        <el-form-item label="是否启用"
                      prop="enabled">
          <el-switch v-model="form.enabled"
                     active-text="启用"
                     inactive-text="禁用"
                     active-color="#13ce66"
                     inactive-color="#ff4949"></el-switch>
        </el-form-item>
        <el-form-item label="角色"
                      prop="roleList">
          <el-checkbox-group v-model="form.roleIds">
            <el-checkbox v-for="x in roleList"
                         :key="x.id"
                         :label="x.id">{{x.name}}</el-checkbox>
          </el-checkbox-group>
        </el-form-item>
      </el-form>
      <span slot="footer">
        <el-button type="primary"
                   @click="handleAddOrUpdate">确 定</el-button>
        <el-button @click="dialogVisible = false">取 消</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
export default {
  data () {
    return {
      total: 0,

      dialogVisible: false,
      dialogTitle: '',

      departmentList: [],
      positionList: [],
      roleList: [],
      dataList: [],
      form: {
        departmentId: null,
        positionId: null,
        username: null,
        password: null,
        name: null,
        email: null,
        phone: null,
        remark: null,
        enabled: true,
        roleList: [],
        roleIds: [],
      },
      queryParam: {
        current: 1,
        size: 10,
        departmentId: null,
        positionId: null,
        name: null,
        phone: null,
        email: null,
        enabled: null,
      },
      rules: {
        username: [{ required: true, message: '请输入账号', trigger: 'change' }],
        password: [{ required: true, message: '请输入密码', trigger: 'change' }],
        name: [{ required: true, message: '请输入姓名', trigger: 'change' }],
        email: [{ required: true, message: '请输入邮箱', trigger: 'change' }],
        phone: [{ required: true, message: '请输入联系电话', trigger: 'change' }],
        enabled: [{ required: true, message: '是否启用', trigger: 'change' }],
      },
    }
  },
  mounted () {
    this.axios.post('/getDept').then(data => {
      this.departmentList = data
    }).catch(e => { })
    this.axios.post('/getPosition').then(data => {
      this.positionList = data
    }).catch(e => { })
    this.axios.post('/getRole').then(data => {
      this.roleList = data
    }).catch(e => { })
    this.getList()
  },
  methods: {
    handleExport () {
      this.$confirm('部门存在子部门，若子部门同名，则无法区分，建议所有子部门区分名称, 是否继续?', '提示', { type: 'warning' }).then(() => {
        this.download('/system/user/export')
      }).catch(e => { })
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
      if (this.form.roleList)
        this.form.roleIds = this.form.roleList.map(item => item.id)
      this.dialogVisible = true
    },
    handleUpdatePassword (row) {
      this.$prompt('请输入密码', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        inputPattern: /^.{1,}$/,
        inputErrorMessage: '密码不能为空'
      }).then(({ value }) => {
        this.axios.post('/system/user/update/password', { id: row.id, password: value }).then(() => {
          this.getList()
        })
      }).catch(e => { })
    },
    handleAddOrUpdate () {
      this.$refs.form.validate((valid) => {
        // 后端不接收rolelist,只接收roleids
        if (valid) {
          if (this.form.id) {
            this.axios.post('/system/user/update', this.form).then(() => {
              this.getList()
              this.dialogVisible = false
            }).catch(e => { })
          } else {
            this.axios.post('/system/user/add', this.form).then(() => {
              this.getList()
              this.dialogVisible = false
            }).catch(e => { })
          }
        }
      })
    },
    handleDelete (row) {
      this.$confirm('此操作将永久删除这条数据, 是否继续?', '提示', { type: 'warning' }).then(() => {
        this.axios.post('/system/user/delete/' + row.id).then(() => this.getList()).catch(e => { })
      }).catch(e => { })
    },

    // 初始化数据
    getList () {
      this.axios.post(`/system/user/page`, this.queryParam).then(data => {
        this.dataList = data.list
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