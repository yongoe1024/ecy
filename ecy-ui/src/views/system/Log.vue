<template>
  <div>
    <!-- 按钮 -->
    <div class="button">
      <el-button type="primary"
                 size="mini"
                 icon="el-icon-search"
                 @click="getList">刷新</el-button>
      <el-button type="danger"
                 size="mini"
                 icon="el-icon-refresh"
                 @click="clear">清空日志</el-button>

    </div>

    <!-- 表格 -->
    <el-table v-loading="loading"
              element-loading-text="拼命加载中"
              element-loading-spinner="el-icon-loading"
              element-loading-background="rgba(0, 0, 0, 0.8)"
              :data="dataList"
              style="width: 100%">
      <el-table-column prop="createTime"
                       label="时间"
                       width="150"
                       align="center">
        <template slot-scope="scope">
          <span style="font-size: 10px;">{{scope.row.createTime}}</span>
        </template>
      </el-table-column>
      <el-table-column prop="name"
                       label="用户"
                       width="100"
                       align="center"></el-table-column>
      <el-table-column prop="type"
                       label="类型"
                       width="100"
                       align="center"></el-table-column>
      <el-table-column prop="title"
                       label="标题"></el-table-column>
      <el-table-column label="操作"
                       align="center"
                       width="140"
                       fixed="right">
        <template slot-scope="scope">
          <el-button style="padding: 3px"
                     type="primary"
                     @click="handleShowUpdateEdit(scope.row)">详情</el-button>
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
               width="70%">
      <el-form ref="form"
               :model="form"
               label-width="auto"
               style="margin:20px"
               :rules="rules">
        <el-form-item label="标题"
                      prop="title">
          <el-input v-model="form.title"
                    readonly
                    placeholder="请输入标题" />
        </el-form-item>
        <el-form-item label="详情"
                      prop="details">
          <el-input v-model="form.details"
                    readonly
                    type="textarea"
                    :rows="20"
                    placeholder="请输入详情" />
        </el-form-item>
      </el-form>
      <span slot="footer">
        <el-button @click="dialogVisible = false">确 定</el-button>
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
      size: 20,
      loading: false,
      dialogVisible: false,
      dialogTitle: '',

      dataList: [],
      form: {
        name: null,
        type: null,
        title: null,
        details: null,
      },
    }
  },
  mounted () {
    this.getList()
  },
  methods: {
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
    handleShowUpdateEdit (row) {
      this.dialogTitle = '详情'
      Object.assign(this.form, row)
      this.dialogVisible = true
    },
    clear () {
      this.$confirm('此操作将永久删除数据, 是否继续?', '提示', { type: 'warning' }).then(() => {
        this.axios.post('/system/log/delete').then(() => this.getList())
      })
    },
    // 初始化数据
    getList () {
      this.loading = true
      this.axios.post('/system/log/page?current=' + this.current + '&size=' + this.size).then(data => {
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