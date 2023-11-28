<template>
  <div>
    <!-- 搜索 -->
    <div class="head">
      <el-input size="small"
                prefix-icon="el-icon-search"
                placeholder="请输入收件人"
                v-model="queryParam.addressee"></el-input>
      <el-input size="small"
                prefix-icon="el-icon-search"
                placeholder="请输入标题"
                v-model="queryParam.title"></el-input>
      <!-- <el-input size="small"
                prefix-icon="el-icon-search"
                placeholder="请输入内容"
                v-model="queryParam.content"></el-input> -->
      <el-select v-model="queryParam.state"
                 size="small"
                 placeholder="请选择状态">
        <el-option label="未读"
                   :value="false"></el-option>
        <el-option label="已读"
                   :value="true"></el-option>
      </el-select>
    </div>
    <!-- 按钮 -->
    <div class="button">
      <el-button type="text"
                 icon="el-icon-search"
                 @click="getList">搜索</el-button>
      <el-button type="text"
                 icon="el-icon-refresh"
                 @click="resetQuery">重置</el-button>
      <el-button type="text"
                 @click="handleShowAddEdit"
                 icon="el-icon-plus">写信</el-button>
    </div>

    <!-- 表格 -->
    <el-table v-loading="loading"
              element-loading-text="拼命加载中"
              element-loading-spinner="el-icon-loading"
              element-loading-background="rgba(0, 0, 0, 0.8)"
              :data="dataList"
              style="width: 100%">
      <!-- <el-table-column prop="addresseeId"
                       label="收件人id"
                       width="100"
                       align="center"></el-table-column> -->
      <el-table-column prop="addressee"
                       label="收件人"
                       width="200"
                       align="center"></el-table-column>
      <el-table-column prop="title"
                       label="标题"
                       align="left"></el-table-column>
      <el-table-column prop="state"
                       label="状态"
                       width="100"
                       align="center">
        <template slot-scope="scope">
          <el-tag v-if="scope.row.state"
                  type="success">已读
          </el-tag>
          <el-tag v-else
                  type="danger">未读
          </el-tag>
        </template>
      </el-table-column>
      <el-table-column prop="createTime"
                       label="时间"
                       width="170"
                       align="center"></el-table-column>
      <el-table-column label="操作"
                       align="center"
                       width="140"
                       fixed="right">
        <template slot-scope="scope">
          <el-button style="padding: 3px"
                     @click="getInfo(scope.row)">详情</el-button>
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
               :fullscreen="true"
               :close-on-click-modal="false"
               width="600px">
      <el-form ref="form"
               :model="form"
               label-width="auto"
               style="margin:20px"
               :rules="rules">
        <el-form-item label="收件人id"
                      style="width:200px"
                      prop="addresseeId">
          <el-input v-model="form.addresseeId"
                    disabled />
        </el-form-item>
        <el-form-item label="收件人"
                      prop="addressee">
          <el-select v-model="form.addressee"
                     placeholder="请选择">
            <el-option v-for="item in userList"
                       :key="item.id"
                       :label="item.name"
                       :value="item.name">
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="标题"
                      prop="title">
          <el-input v-model="form.title"
                    style="width:300px"
                    placeholder="请输入标题" />
        </el-form-item>
        <el-form-item label="内容"
                      prop="content">
          <e-editor v-model="form.content"></e-editor>
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
import EEditor from '@/components/e-editor.vue'
export default {
  components: { EEditor },
  props: {},
  data () {
    return {
      total: 0,
      current: 1,
      size: 10,
      loading: false,
      dialogVisible: false,
      dialogTitle: '',
      userList: [],
      dataList: [],
      form: {
        addresseeId: null,
        addressee: null,
        title: null,
        content: null,
      },
      queryParam: {
        addresserId: null,
        addressee: null,
        title: null,
        content: null,
        state: null,
      },
      rules: {
        addresseeId: [{ required: true, message: '请输入收件人id', trigger: 'change' }],
        addressee: [{ required: true, message: '请输入收件人', trigger: 'change' }],
        title: [{ required: true, message: '请输入标题', trigger: 'change' },
        { max: 15, message: '最多15个字', trigger: 'change' }],
        content: [{ required: true, message: '请输入内容', trigger: 'change' },
        { max: 1000, message: '最多1000个字', trigger: 'change' }],
      },
    }
  },
  computed: {
    addressee () {
      return this.form.addressee
    }
  },
  watch: {
    addressee (nv) {
      this.userList.forEach(item => {
        if (nv == item.name)
          this.form.addresseeId = item.id
      })
    }
  },
  mounted () {
    this.getList()
    this.axios.post('/basic/letter/user').then(data => {
      this.userList = data
    }).catch(e => { })
  },
  methods: {
    getInfo (row) {
      this.$router.push('/letter-info?id=' + row.id)
    },
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
      this.dialogTitle = '写信'
      this.dialogVisible = true
    },
    handleAddOrUpdate () {
      this.$refs.form.validate((valid) => {
        if (valid) {
          this.axios.post('/basic/letter/add', this.form).then(() => {
            this.getList()
            this.dialogVisible = false
          }).catch(e => { })
        }
      })
    },
    // 初始化数据
    getList () {
      this.queryParam.addresserId = this.$store.state.user.user.id
      this.loading = true
      this.axios.post('/basic/letter/page?current=' + this.current + '&size=' + this.size, this.queryParam).then(data => {
        this.loading = false
        this.dataList = data.list
        this.total = data.total - 0
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