<template>
  <div>

    <!-- 按钮 -->
    <div class="button">
      <el-button type="primary"
                 size="small"
                 icon="el-icon-search"
                 @click="getList">刷新</el-button>
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
              row-key="id"
              :tree-props="{children: 'children'}"
              stripe
              border
              style="width: 100%">
      <el-table-column prop="name"
                       label="部门名称"></el-table-column>
      <el-table-column prop="leader"
                       label="负责人"
                       align="center"></el-table-column>
      <el-table-column prop="phone"
                       label="联系电话"
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
                       width="140"
                       fixed="right">
        <template slot-scope="scope">
          <el-button style="padding: 3px"
                     type="primary"
                     @click="handleShowUpdateEdit(scope.row)">编辑</el-button>
          <el-button style="padding: 3px"
                     type="danger"
                     @click="handleDelete(scope.row)">删除</el-button>
        </template>
      </el-table-column>
    </el-table>

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
        <el-form-item label="父部门"
                      prop="parentId">
          <Tree :data="dataList"
                v-model="form.parentId"
                :tree_props="{ children: 'children', label: 'name', keyname: 'id' }"
                placeholder="请选择父部门"></Tree>
        </el-form-item>
        <el-form-item label="部门名称"
                      prop="name">
          <el-input v-model="form.name"
                    placeholder="请输入部门名称" />
        </el-form-item>
        <el-form-item label="负责人"
                      prop="leader">
          <el-input v-model="form.leader"
                    placeholder="请输入负责人" />
        </el-form-item>
        <el-form-item label="联系电话"
                      prop="phone">
          <el-input v-model="form.phone"
                    placeholder="请输入联系电话" />
        </el-form-item>
        <el-form-item label="是否启用"
                      prop="enabled">
          <el-switch v-model="form.enabled"
                     active-text="启用"
                     inactive-text="禁用"
                     active-color="#13ce66"
                     inactive-color="#ff4949"></el-switch>
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
  props: {},
  data () {
    return {
      loading: false,
      dialogVisible: false,
      dialogTitle: '',

      dataList: [],
      form: {
        parentId: null,
        name: null,
        leader: null,
        phone: null,
        enabled: true,
      },
      rules: {
        parentId: [{ required: true, message: '请输入父id', trigger: 'change' }],
        name: [{ required: true, message: '请输入部门名称', trigger: 'change' }],
        leader: [{ required: true, message: '请输入负责人', trigger: 'change' }],
        phone: [{ required: true, message: '请输入联系电话', trigger: 'change' }],
        enabled: [{ required: true, message: '请输入是否启用', trigger: 'change' }],
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
            this.axios.post('/basic/department/update', this.form).then(() => {
              this.getList()
              this.dialogVisible = false
            }).catch(e => { })
          } else {
            this.axios.post('/basic/department/add', this.form).then(() => {
              this.getList()
              this.dialogVisible = false
            }).catch(e => { })
          }
        }
      })
    },
    handleDelete (row) {
      this.$confirm('此操作将永久删除这条数据, 是否继续?', '提示', { type: 'warning' }).then(() => {
        this.axios.post('/basic/department/delete/' + row.id).then(() => this.getList()).catch(e => { })
      }).catch(e => { })
    },
    // 初始化数据
    getList () {
      this.loading = true
      this.axios.post('/basic/department/tree').then(data => {
        this.loading = false
        this.dataList = data
      }).catch(e => this.loading = false)
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