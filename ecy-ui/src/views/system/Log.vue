<template>
  <div>
    <!-- 按钮 -->
    <div class="button">
      <el-button type="primary"
                 size="mini"
                 plain
                 icon="el-icon-search"
                 @click="getList">刷新</el-button>
      <el-button size="mini"
                 plain
                 @click="getType">{{queryParam.name=='系统'?'全部日志':'系统日志'}}</el-button>
      <el-button type="danger"
                 size="mini"
                 plain
                 icon="el-icon-refresh"
                 @click="clear">清空日志</el-button>
    </div>

    <!-- 表格 -->
    <el-table :data="dataList"
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
                       align="center">
        <template slot-scope="scope">
          <el-tag v-if="scope.row.type=='操作'"
                  type="info">{{scope.row.type}}
          </el-tag>
          <el-tag v-else
                  effect="dark"
                  type="danger">{{scope.row.type}}
          </el-tag>
        </template>
      </el-table-column>
      <el-table-column prop="title"
                       label="标题"></el-table-column>
      <el-table-column label="操作"
                       align="center"
                       width="140"
                       fixed="right">
        <template slot-scope="scope">
          <el-button style="padding: 3px"
                     type="text"
                     @click="handleShowUpdateEdit(scope.row)">详情</el-button>
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
               :close-on-click-modal="false"
               width="70%">
      <el-form ref="form"
               :model="form"
               label-width="auto"
               style="margin:20px">
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
import page from '@/mixin/page.js'
export default {
  mixins: [page],
  data () {
    return {
      dialogVisible: false,
      dialogTitle: '',

      dataList: [],
      queryParam: {
        name: null,
        type: null,
        title: null,
        details: null,
      },
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
    getType () {
      this.queryParam.name == '系统' ? this.queryParam.name = '' : this.queryParam.name = '系统'
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
      }).catch(e => { })
    },
    // 初始化数据
    getList () {
      this.axios.post(`/system/log/page`, this.queryParam).then(data => {
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