<template>
  <div>
    <el-page-header @back="$router.back()"
                    style="margin-bottom:30px"
                    content="数据字典-数据"></el-page-header>
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
      <el-table-column prop="dictKey"
                       label="字典键"
                       align="center"></el-table-column>
      <el-table-column prop="dictValue"
                       label="字典值"
                       align="center"></el-table-column>
      <el-table-column prop="color"
                       label="颜色"
                       align="center">
        <template slot-scope="scope">
          <el-tag effect="dark"
                  :style="{'background-color': scope.row.color,'border-color': scope.row.color}">{{scope.row.color}}</el-tag>
        </template>
      </el-table-column>
      <el-table-column prop="sort"
                       label="字典顺序"
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
        <el-form-item label="字典键"
                      prop="dictKey">
          <el-input v-model="form.dictKey"
                    placeholder="请输入字典键" />
        </el-form-item>
        <el-form-item label="字典值"
                      prop="dictValue">
          <el-input v-model="form.dictValue"
                    placeholder="数据库若为布尔类型,则字典值填写true或false" />
        </el-form-item>

        <el-form-item label="字典顺序"
                      prop="sort">
          <el-input-number v-model="form.sort"
                           :min="0"></el-input-number>
        </el-form-item>
        <el-form-item label="颜色"
                      prop="color">
          <el-color-picker v-model="form.color"
                           show-alpha
                           :predefine="predefineColors">
          </el-color-picker>
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
  components: {},
  props: {},
  data () {
    return {
      // 多选框数据
      multipleSelection: [],
      total: 0,

      dialogVisible: false,
      dialogTitle: '',

      dataList: [],
      form: {
        dictId: null,
        dictKey: null,
        dictValue: null,
        color: null,
        sort: null,
      },
      queryParam: {
        current: 1,
        size: 10,
        dictKey: null,
        dictValue: null,
      },
      predefineColors: [
        '#ff4500',
        '#ff8c00',
        '#ffd700',
        '#90ee90',
        '#00ced1',
        '#1e90ff',
        '#c71585',
        'rgba(255, 69, 0, 0.68)',
        'rgb(255, 120, 0)',
        'hsv(51, 100, 98)',
        'hsva(120, 40, 94, 0.5)',
        'hsl(181, 100%, 37%)',
        'hsla(209, 100%, 56%, 0.73)',
        '#c7158577'
      ],
      rules: {
        dictKey: [{ required: true, message: '请输入字典键', trigger: 'change' }],
        dictValue: [{ required: true, message: '请输入字典值', trigger: 'change' }],
        sort: [{ required: true, message: '请输入字典顺序', trigger: 'change' }],
      },
    }
  },
  mounted () {
    this.queryParam.dictId = this.$route.query.dictId
    this.form.dictId = this.$route.query.dictId
    this.getList()
  },
  methods: {
    reset () {
      this.form = this.$options.data().form
      this.form.dictId = this.$route.query.dictId
    },
    resetQuery () {
      this.queryParam = this.$options.data().queryParam
      this.queryParam.dictId = this.$route.query.dictId
    },
    // 多选框回调
    handleSelectionChange (val) {
      this.multipleSelection = val.map(item => item.id)
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
            this.axios.post('/basic/dict/data/update', this.form).then(() => {
              this.getList()
              this.dialogVisible = false
            }).catch(e => { })
          } else {
            this.axios.post('/basic/dict/data/add', this.form).then(() => {
              this.getList()
              this.dialogVisible = false
            }).catch(e => { })
          }
        }
      })
    },
    handleDelete (row) {
      this.$confirm('此操作将永久删除这条数据, 是否继续?', '提示', { type: 'warning' }).then(() => {
        this.axios.post('/basic/dict/data/delete/' + row.id).then(() => this.getList()).catch(e => { })
      }).catch(e => { })
    },
    handleDeleteMany () {
      this.$confirm('此操作将永久删除 [' + this.multipleSelection.length + '] 条数据, 是否继续?', '提示', { type: 'warning' }).then(() => {
        this.axios.post('/basic/dict/data/delete/' + this.multipleSelection).then(() => this.getList()).catch(e => { })
      }).catch(e => { })
    },
    // 初始化数据
    getList () {
      this.axios.post(`/basic/dict/data/page`, this.queryParam).then(data => {
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