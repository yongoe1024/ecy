<template>
  <div class="box">
    <el-form :rules="rules"
             ref="form"
             :model="form"
             class="login-box">
      <h3>修改密码</h3>

      <el-form-item prop="credential"
                    label="新密码">
        <el-input v-model="form.credential"> </el-input>
      </el-form-item>

      <el-form-item>
        <el-button style="width: 100%"
                   type="primary"
                   @click="update">修改密码</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>


<script>
export default {
  data () {
    return {
      form: {
        credential: '',
      },
      rules: {
        credential: [
          { required: true, message: "用户密码不能为空", trigger: "change" },
          { min: 5, message: '密码长度必须大于6', trigger: 'change' }
        ],
      },
    }
  },
  methods: {
    update () {
      this.$refs.form.validate((valid) => {
        if (valid) {
          this.axios.post('/user/password', this.form)
        }
      })
    }
  },
}

</script>
<style scoped>
.box {
  margin: 8% auto;
  width: 40%;
  height: 300px;
}
.login-box {
  margin: 0 10%;
}
</style>