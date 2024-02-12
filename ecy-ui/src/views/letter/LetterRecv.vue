<template>
  <div>
    <!-- 搜索 -->
    <div class="head">
      <el-input size="small"
                prefix-icon="el-icon-search"
                placeholder="请输入发件人"
                v-model="queryParam.addresser"></el-input>
      <el-input size="small"
                prefix-icon="el-icon-search"
                placeholder="请输入标题"
                v-model="queryParam.title"></el-input>
      <el-select v-model="queryParam.state"
                 size="small"
                 placeholder="请选择状态">
        <el-option label="未读"
                   :value="false"></el-option>
        <el-option label="已读"
                   :value="true"></el-option>
      </el-select>
      <el-button icon="el-icon-search"
                 size="mini"
                 plain
                 type="primary"
                 @click="getList">搜索</el-button>
      <el-button icon="el-icon-refresh"
                 size="mini"
                 plain
                 @click="resetQuery">重置</el-button>
    </div>

    <!-- 表格 -->
    <el-table :header-cell-style="{background:'#eef1f6'}"
              :data="dataList"
              style="width: 100%">
      <el-table-column prop="addresser"
                       label="发件人"
                       width="200"
                       align="center"></el-table-column>
      <el-table-column prop="title"
                       label="标题"
                       align="center"></el-table-column>
      <el-table-column prop="state"
                       label="状态"
                       width="100"
                       align="center">
        <template slot-scope="scope">
          <el-tag v-if="scope.row.state"
                  effect="dark"
                  type="success">已读
          </el-tag>
          <el-tag v-else
                  effect="dark"
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
          <el-button type="text"
                     @click="getInfo(scope.row)">详情</el-button>
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
  </div>
</template>

<script>
import page from '@/mixin/page.js'
export default {
  mixins: [page],
  data () {
    return {
      dataList: [],
      queryParam: {
        current: 1,
        size: 10,
        addresseeId: null,
        addresser: null,
        title: null,
        content: null,
        state: null,
      },
    }
  },
  mounted () {
    this.getList()
  },
  methods: {
    getInfo (row) {
      this.$router.push('/letter-info?id=' + row.id)
    },
    resetQuery () {
      this.queryParam = this.$options.data().queryParam
    },
    // 初始化数据
    getList () {
      this.axios.post(`/basic/letter/recv`, this.queryParam).then(data => {
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