<template>
  <div>
    <!-- 搜索 -->
    <div class="head">
      <el-input size="small"
                prefix-icon="el-icon-search"
                placeholder="请输入字典键"
                v-model="queryParam.dictKey"></el-input>
      <el-input size="small"
                prefix-icon="el-icon-search"
                placeholder="请输入字典值"
                v-model="queryParam.dictValue"></el-input>
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
              style="width: 100%"
              @selection-change="handleSelectionChange">
      <el-table-column type="selection"
                       width="55"></el-table-column>
      <el-table-column align="center"
                       type="index"></el-table-column>
      <el-table-column prop="dictKey"
                       label="字典键"
                       align="center"></el-table-column>
      <el-table-column prop="dictValue"
                       label="字典值"
                       align="center"></el-table-column>
      <el-table-column prop="sort"
                       label="字典顺序"
                       align="center"></el-table-column>
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
        <el-form-item label="字典键"
                      prop="dictKey">
          <el-input v-model="form.dictKey"
                    placeholder="请输入字典键" />
        </el-form-item>
        <el-form-item label="字典值"
                      prop="dictValue">
          <el-input v-model="form.dictValue"
                    placeholder="请输入字典值" />
        </el-form-item>
        <el-form-item label="字典顺序"
                      prop="sort">
          <el-input-number v-model="form.sort"
                           :min="0"></el-input-number>
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
      // 多选框数据
      multipleSelection: [],
      total: 0,
      current: 1,
      size: 10,
      loading: false,
      dialogVisible: false,
      dialogTitle: '',

      dataList: [],
      form: {
        dictId: null,
        dictKey: null,
        dictValue: null,
        sort: null,
      },
      queryParam: {
        dictId: null,
        dictKey: null,
        dictValue: null,
      },
      rules: {
        dictKey: [{ required: true, message: '请输入字典键', trigger: 'change' }],
        dictValue: [{ required: true, message: '请输入字典值', trigger: 'change' }],
        sort: [{ required: true, message: '请输入字典顺序', trigger: 'change' }],
      },
    }
  },
  mounted () {
    this.queryParam.dictId = this.$route.query.dictId
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
      this.dialogVisible = true
    },
    handleAddOrUpdate () {
      this.$refs.form.validate((valid) => {
        if (valid) {
          if (this.form.id) {
            this.axios.post('/basic/dict/data/update', this.form).then(() => {
              this.getList()
              this.dialogVisible = false
            })
          } else {
            this.axios.post('/basic/dict/data/add', this.form).then(() => {
              this.getList()
              this.dialogVisible = false
            })
          }
        }
      })
    },
    handleDelete (row) {
      this.$confirm('此操作将永久删除这条数据, 是否继续?', '提示', { type: 'warning' }).then(() => {
        this.axios.post('/basic/dict/data/delete/' + row.id).then(() => this.getList())
      })
    },
    handleDeleteMany () {
      this.$confirm('此操作将永久删除 [' + this.multipleSelection.length + '] 条数据, 是否继续?', '提示', { type: 'warning' }).then(() => {
        this.axios.post('/basic/dict/data/delete/' + this.multipleSelection).then(() => this.getList())
      })
    },
    // 初始化数据
    getList () {
      this.loading = true
      this.axios.post('/basic/dict/data/page?current=' + this.current + '&size=' + this.size, this.queryParam).then(data => {
        this.loading = false
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