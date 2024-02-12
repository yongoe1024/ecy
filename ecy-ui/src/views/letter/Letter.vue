<template>
  <div>
    <el-tabs v-model="activeName">
      <el-tab-pane label="收信箱"
                   name="recv"><letter-recv></letter-recv></el-tab-pane>
      <el-tab-pane label="发信箱"
                   name="send"><letter-send></letter-send></el-tab-pane>
    </el-tabs>
  </div>
</template>

<script>
import LetterRecv from './LetterRecv.vue'
import LetterSend from './LetterSend.vue'
export default {
  components: { LetterSend, LetterRecv },
  data () {
    return {
      activeName: 'recv'
    }
  },
}
</script>