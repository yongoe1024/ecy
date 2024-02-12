<template>
  <div>
    <!-- 搜索 -->
    <div class="head">
      <el-input size="small"
                prefix-icon="el-icon-search"
                placeholder="请输入字典名称"
                v-model="queryParam.name"></el-input>
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
    <el-table :data="dataList"
              style="width: 100%"
              :header-cell-style="{background:'#eef1f6'}"
              @selection-change="handleSelectionChange">
      <el-table-column type="selection"
                       width="55"></el-table-column>
      <el-table-column align="center"
                       type="index"></el-table-column>
      <el-table-column prop="name"
                       label="字典名称"
                       align="center">
        <template slot-scope="scope">
          <router-link :to="{
                path: '/basic/DictData',  query :{ dictId:scope.row.id}
                }">{{scope.row.name}}
          </router-link>
        </template>
      </el-table-column>
      <el-table-column prop="remark"
                       label="备注"
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
        </template>
      </el-table-column>
    </el-table>

    <!-- 批量删除 -->
    <el-button style="margin-top: 8px"
               type="danger"
               size="small"
               :disabled="multipleSelection.length == 0"
               @click="handleDeleteMany">批量删除</el-button>
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
        <el-form-item label="字典名称"
                      prop="name">
          <el-input v-model="form.name"
                    placeholder="请输入字典名称" />
        </el-form-item>
        <el-form-item label="备注"
                      prop="remark">
          <el-input v-model="form.remark"
                    placeholder="请输入备注" />
        </el-form-item>
      </el-form>
      <span slot="footer">
        <el-button size="medium"
                   @click="dialogVisible = false">取 消</el-button>
        <el-button size="medium"
                   type="primary"
                   @click="handleAddOrUpdate">确 定</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
import page from '@/mixin/page.js'
export default {
  mixins: [page],
  data () {
    return {
      // 多选框数据
      multipleSelection: [],
      dialogVisible: false,
      dialogTitle: '',

      dataList: [],
      form: {
        name: null,
        remark: null,
      },
      queryParam: {
        name: null,
      },
      rules: {
        name: [{ required: true, message: '请输入字典名称', trigger: 'change' }],
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
    // 多选框回调
    handleSelectionChange (val) {
      this.multipleSelection = val.map(item => item.id)
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
            this.axios.post('/basic/dict/update', this.form).then(() => {
              this.getList()
              this.dialogVisible = false
            })
          } else {
            this.axios.post('/basic/dict/add', this.form).then(() => {
              this.getList()
              this.dialogVisible = false
            })
          }
        }
      })
    },
    handleDelete (row) {
      this.$confirm('此操作将永久删除这条数据, 是否继续?', '提示', { type: 'warning' }).then(() => {
        this.axios.post('/basic/dict/delete/' + row.id).then(() => this.getList())
      }).catch(e => { })
    },
    handleDeleteMany () {
      this.$confirm('此操作将永久删除 [' + this.multipleSelection.length + '] 条数据, 是否继续?', '提示', { type: 'warning' }).then(() => {
        this.axios.post('/basic/dict/delete/' + this.multipleSelection).then(() => this.getList())
      }).catch(e => { })
    },
    // 初始化数据
    getList () {
      this.axios.post(`/basic/dict/page`, this.queryParam).then(data => {
        this.dataList = data.list
        this.total = data.total - 0
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