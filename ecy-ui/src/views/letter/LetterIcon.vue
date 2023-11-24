<template>
  <div>
    <el-popover placement="bottom"
                width="400"
                trigger="click">
      <div style="height:410px">
        <el-button circle
                   size="mini"
                   @click="getletter"
                   icon="el-icon-refresh"> </el-button>
        <div class="letter"
             v-for="(item,index) in letterList"
             @click="getletterById(item.id)"
             :key="index">
          <div style="font-size:13px;"
               class="content">
            <div class="blue"
                 v-if="!item.state"> </div>
            <div style="margin-left:10px">{{item.title.substring(0, 15)}}</div>
            <div style="font-size:10px;color:#a09999">{{item.createBy}}</div>
          </div>
          <div class="content">
            <span> </span>
            <!-- <div>{{item.content.substring(0, 15)}}...</div> -->
            <div style="font-size:10px;color:#a09999">{{item.createTime}}</div>
          </div>
        </div>
        <el-link type="primary"
                 @click="getAll"
                 style="font-size:13px;margin:10px 0px 0px 25px">查看全部</el-link>
      </div>

      <el-badge :value="num"
                slot="reference">
        <i class="el-icon-message"
           style="font-size:25px;margin-bottom:20px;cursor: pointer;"></i>
      </el-badge>

    </el-popover>
  </div>
</template>


<script>
export default {
  data () {
    return {
      num: '',
      letterList: [],
    }
  },
  mounted () {
    this.getletter()
  },
  methods: {
    getletter () {
      this.axios.post('/basic/letter/list').then(data => {
        this.letterList = data.list
        this.num = data.num == 0 ? '' : data.num
      }).catch(e => { })
    },
    getletterById (id) {
      this.$router.push('/letter-info?id=' + id)
    },
    getAll () {
      this.$router.push('/letter-recv')
    }
  },
}

</script>
<style scoped>
.letter {
  padding: 10px 20px 10px 30px;
  border-bottom: 1px solid #ddd;
  cursor: pointer;
}
.letter .content {
  display: flex;
  flex-direction: row;
  justify-content: space-between;
}
.blue {
  content: "";
  width: 6px;
  height: 6px;
  background-color: #006eff;
  border-radius: 3px;
  position: absolute;
  margin: 5px 0;
}
</style>