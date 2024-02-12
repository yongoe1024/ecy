<template>
  <div>
    <el-page-header @back="$router.back()"
                    style="margin-bottom:30px"
                    content="详情"></el-page-header>
    <el-descriptions class="margin-top"
                     :title="letter.title"
                     :column="2"
                     border>
      <el-descriptions-item>
        <template slot="label">
          发件人id
        </template>
        {{letter.addresserId}}
      </el-descriptions-item>
      <el-descriptions-item>
        <template slot="label">
          发件人
        </template>
        {{letter.addresser}}
      </el-descriptions-item>
      <el-descriptions-item>
        <template slot="label">
          收件人id
        </template>
        {{letter.addresseeId}}
      </el-descriptions-item>
      <el-descriptions-item>
        <template slot="label">
          收件人
        </template>
        {{letter.addressee}}
      </el-descriptions-item>
      <el-descriptions-item>
        <template slot="label">
          状态
        </template>
        <el-tag v-if="letter.state"
                effect="dark"
                type="success">已读
        </el-tag>
        <el-tag v-else
                effect="dark"
                type="danger">未读
        </el-tag>
      </el-descriptions-item>
      <el-descriptions-item>
        <template slot="label">
          发送时间
        </template>
        {{letter.createTime}}
      </el-descriptions-item>
    </el-descriptions>
    <el-card>
      <span class="ql-editor"
            v-html="letter.content"> </span>
    </el-card>
  </div>

</template>

<script>
export default {
  data () {
    return {
      letter: {
        addresserId: null,
        addresser: null,
        addresseeId: null,
        addressee: null,
        title: null,
        content: null,
        state: null,
        createTime: null
      }
    }
  },
  mounted () {
    let id = this.$route.query.id
    this.axios.post('/basic/letter/info?id=' + id).then(data => {
      this.letter = data
    })
  }
}
</script> 