<template>
  <div class="box">
    <div class="form-box">
      <el-page-header @back="$router.back()"
                      style="margin-bottom:30px"
                      content="修改密码"></el-page-header>
      <el-form :rules="rules"
               ref="form"
               :model="form">
        <el-form-item prop="password"
                      label="新密码">
          <el-input v-model="form.password"> </el-input>
        </el-form-item>

        <el-form-item>
          <el-button style="width: 100%"
                     type="primary"
                     @click="update">修改密码</el-button>
        </el-form-item>
      </el-form>
    </div>
  </div>
</template>


<script>
export default {
  data () {
    return {
      form: {
        password: '',
      },
      rules: {
        password: [
          { required: true, message: "用户密码不能为空", trigger: "change" },
          { min: 6, message: '密码长度必须大于6', trigger: 'change' }
        ],
      },
    }
  },
  methods: {
    update () {
      this.$refs.form.validate((valid) => {
        if (valid) {
          this.axios.post('/user/password', this.form).catch(e => { })
        }
      })
    }
  },
}

</script>
<style scoped>
.box {
  display: flex;
  justify-content: center;
}
.form-box {
  margin-top: 10%;
  width: 400px;
  height: 300px;
}
</style>