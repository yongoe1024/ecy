<template>
  <div>
    <el-tabs v-model="activeName"
             @tab-click="handleClick">
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
  props: {},
  computed: {},
  data () {
    return {
      activeName: 'recv'
    }
  },
  methods: {
    handleClick (tab, event) {
    }
  }
}

</script>
<style scoped>
</style>