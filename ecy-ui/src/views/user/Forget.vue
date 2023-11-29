<template>
  <div class="box">
    <el-card style="width: 500px;">
      <el-form :rules="rules"
               ref="form"
               :model="form"
               label="right"
               label-width="auto"
               class="login-box">

        <el-form-item>
          <h1>找回密码</h1>
        </el-form-item>

        <el-form-item prop="username"
                      label="账号:">
          <el-input v-model="form.username"
                    placeholder="请输入账号">
          </el-input>
        </el-form-item>
        <el-form-item prop="email"
                      label="邮箱:">
          <el-input v-model="form.email"
                    placeholder="请输入邮箱"></el-input>
        </el-form-item>

        <el-form-item prop="password"
                      label="新密码:">
          <el-input show-password
                    v-model="form.password"
                    placeholder="请输入密码"></el-input>
        </el-form-item>

        <el-form-item>
          <el-button style="width: 100%"
                     type="primary"
                     @click="submitForm">确认</el-button>
        </el-form-item>

        <el-link href="/login"
                 type="primary">返回</el-link>
      </el-form>
    </el-card>
  </div>
</template>


<script>
export default {
  data () {
    return {
      form: {
        email: null,
        username: null,
        password: null,
      },
      rules: {
        email: [{ type: "email", message: "邮箱不能为空", required: true, trigger: 'change' }],
        username: [{ required: true, message: "账号不能为空", trigger: 'change' }],
        password: [
          { required: true, message: "用户密码不能为空", trigger: "change" },
          { min: 6, message: '密码长度必须大于6', trigger: 'change' }
        ],
      },
    }
  },
  methods: {
    submitForm () {
      this.$refs.form.validate((valid) => {
        if (valid) {
          this.axios.post('/forget', this.form).then((result) => {
            this.$router.replace('/login')
          }).catch(e => { })
        }
      })
    },
  },
}

</script>
<style scoped>
.box {
  height: 100%;
  width: 100%;
  position: absolute;
  background-image: url("../../assets/back.jpg");
  background-size: 100% 100%;
  display: flex;
  justify-content: center;
  align-items: center;
}
.login-box {
  margin: 0 auto;
  padding: 20px;
}
</style>

