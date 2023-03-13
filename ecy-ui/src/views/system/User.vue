<template>
  <div>
    <!-- 搜索 -->
    <div class="head">
      <el-input size="small"
                prefix-icon="el-icon-search"
                placeholder="请输入姓名"
                v-model="queryParam.name"></el-input>
      <el-input size="small"
                prefix-icon="el-icon-search"
                placeholder="请输入邮箱"
                v-model="queryParam.email"></el-input>
      <el-input size="small"
                prefix-icon="el-icon-search"
                placeholder="请输入手机号"
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
                 size="small"
                 icon="el-icon-search"
                 @click="getList">搜索</el-button>
      <el-button type="primary"
                 size="small"
                 icon="el-icon-refresh"
                 @click="resetQuery">重置</el-button>
      <el-button type="primary"
                 size="small"
                 @click="handleShowAddEdit"
                 icon="el-icon-plus">添加</el-button>

    </div>

    <!-- 表格 -->
    <el-table v-loading="loading"
              element-loading-text="拼命加载中"
              element-loading-spinner="el-icon-loading"
              element-loading-background="rgba(0, 0, 0, 0.8)"
              :data="dataList"
              stripe
              border
              style="width: 100%">
      <el-table-column align="center"
                       type="index"></el-table-column>
      <el-table-column prop="name"
                       label="姓名"
                       align="center"></el-table-column>
      <el-table-column prop="identifier"
                       label="账号"
                       align="center"></el-table-column>
      <el-table-column prop="email"
                       label="邮箱"
                       align="center"></el-table-column>
      <el-table-column prop="avatar"
                       label="头像"
                       align="center">
        <template slot-scope="scope">
          <img :src="scope.row.avatar"
               style="width:40px">
        </template>
      </el-table-column>
      <el-table-column prop="phone"
                       label="手机号"
                       align="center"></el-table-column>
      <el-table-column prop="remark"
                       label="备注"
                       align="center"></el-table-column>
      <el-table-column prop="lastTime"
                       label="上次登录时间"
                       align="center"></el-table-column>
      <el-table-column prop="enabled"
                       label="是否启用"
                       align="center">
        <template slot-scope="scope">
          <el-tag v-if="scope.row.enabled"
                  type="success">启用
          </el-tag>
          <el-tag v-else
                  type="danger">禁用
          </el-tag>
        </template>
      </el-table-column>
      <el-table-column label="操作"
                       align="center"
                       width="150"
                       fixed="right">
        <template slot-scope="scope"
                  v-if="scope.row.id!=1">
          <el-button style="padding: 3px"
                     type="danger"
                     @click="handleUpdatePassword(scope.row)">修改密码</el-button>
          <el-button style="padding: 3px"
                     type="primary"
                     @click="handleShowUpdateEdit(scope.row)">编辑</el-button>
          <el-button style="padding: 3px"
                     type="danger"
                     @click="handleDelete(scope.row)">删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <!-- 分页 -->
    <el-pagination background
                   style="display:flex;justify-content:center;"
                   @size-change="handleSizeChange"
                   @current-change="handleCurrentChange"
                   :page-size="size"
                   layout="total, sizes, prev, pager, next, jumper"
                   :total="total"></el-pagination>

    <el-dialog :visible.sync="dialogVisible"
               :title="dialogTitle"
               @close="reset"
               :close-on-click-modal="false"
               width="600px">
      <el-form ref="form"
               :model="form"
               label-width="auto"
               style="margin:20px"
               :rules="rules">
        <el-form-item label="姓名"
                      prop="name">
          <el-input v-model="form.name"
                    placeholder="请输入姓名" />
        </el-form-item>
        <el-form-item label="账号"
                      prop="identifier">
          <el-input v-model="form.identifier"
                    placeholder="请输入账号" />
        </el-form-item>
        <el-form-item label="头像"
                      prop="avatar">
          <el-input v-model="form.avatar"
                    placeholder="请输入头像" />
        </el-form-item>
        <el-form-item label="邮箱"
                      prop="email">
          <el-input v-model="form.email"
                    placeholder="请输入邮箱" />
        </el-form-item>
        <el-form-item label="手机号"
                      prop="phone">
          <el-input v-model="form.phone"
                    placeholder="请输入手机号" />
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
                         :label="x.id">{{x.nameZh}}</el-checkbox>
          </el-checkbox-group>
        </el-form-item>
      </el-form>
      <span slot="footer">
        <el-button @click="dialogVisible = false">取 消</el-button>
        <el-button type="primary"
                   @click="handleAddOrUpdate">确 定</el-button>
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
      current: 1,
      size: 10,
      loading: false,
      dialogVisible: false,
      dialogTitle: '',
      roleList: [],
      dataList: [],
      form: {
        name: null,
        avatar: null,
        email: null,
        phone: null,
        remark: null,
        lastTime: null,
        enabled: true,
        roleList: [],
        roleIds: [],
        identifier: null
      },
      queryParam: {
        name: null,
        phone: null,
        email: null,
        enabled: null,
      },
      rules: {
        identifier: [{ required: true, message: '请输入账号', trigger: 'blur' }],
        name: [{ required: true, message: '请输入姓名', trigger: 'blur' }],
        avatar: [{ required: true, message: '请输入头像', trigger: 'blur' }],
        phone: [{ required: true, message: '请输入手机号', trigger: 'blur' }],
        email: [{ type: 'email', required: true, message: '请输入邮箱', trigger: 'blur' }],
        enabled: [{ required: true, message: '请输入是否启用', trigger: 'blur' }],
      },
    }
  },
  mounted () {
    this.getList()
  },
  methods: {
    reset () {
      this.form = this.$options.data().form
    },
    resetQuery () {
      this.queryParam = this.$options.data().queryParam
    },
    // 改变页码
    handleSizeChange (val) {
      this.size = val
      this.getList()
    },
    // 点击页数
    handleCurrentChange (val) {
      this.current = val
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
        inputPattern: /^\d{6,}$/,
        inputErrorMessage: '密码最低6位'
      }).then(({ value }) => {
        this.axios.post('/system/user/update/password', { id: row.id, credential: value }).then(() => {
          this.getList()
        })
      })
    },
    handleAddOrUpdate () {
      this.$refs.form.validate((valid) => {
        // 后端不接受rolelist
        if (valid) {
          if (this.form.id) {
            this.axios.post('/system/user/update', this.form).then(() => {
              this.getList()
              this.dialogVisible = false
            })
          } else {
            this.axios.post('/system/user/add', this.form).then(() => {
              this.getList()
              this.dialogVisible = false
            })
          }
        }
      })
    },
    handleDelete (row) {
      this.$confirm('此操作将永久删除这条数据, 是否继续?', '提示', { type: 'warning' }).then(() => {
        this.axios.post('/system/user/delete/' + row.id).then(() => this.getList())
      })
    },
    // 初始化数据
    getList () {
      this.loading = true
      this.axios.post('/system/user/page?current=' + this.current + '&size=' + this.size, this.queryParam).then(data => {
        this.loading = false
        this.dataList = data.list
        this.total = data.total - 0
      })
      this.axios.post('/system/role/list').then(data => {
        this.roleList = data
      })
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