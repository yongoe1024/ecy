<template>
  <div class="box">

    <el-card class="box-card">
      <el-form :rules="rules"
               ref="form"
               :model="form"
               label="right"
               label-width="auto"
               class="login-box">

        <el-form-item>
          <h1>找回密码</h1>
        </el-form-item>

        <el-form-item prop="identifier"
                      label="账号:">
          <el-input v-model="form.identifier"
                    placeholder="请输入账号">
          </el-input>
        </el-form-item>
        <el-form-item prop="email"
                      label="邮箱:">
          <el-input v-model="form.email"
                    placeholder="请输入邮箱"></el-input>
        </el-form-item>

        <el-form-item prop="credential"
                      label="新密码:">
          <el-input show-credential
                    v-model="form.credential"
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
        identifier: null,
        credential: null,
        code: null
      },
      rules: {
        email: [{ type: "email", message: "邮箱不能为空", required: true, trigger: 'change' }],
        identifier: [{ required: true, message: "账号不能为空", trigger: 'change' }],
        credential: [
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
          this.$message.success({ message: '已发送邮件，请查看邮箱完成后续步骤' })
          this.axios.get('/captcha/email?email=' + this.form.email).then(() => {
          })
          this.$prompt('请输入验证码', '提示', {
            confirmButtonText: '确定',
            cancelButtonText: '取消',
          }).then(({ value }) => {
            this.form.code = value
            this.axios.post('/forget', this.form).then(() => {
              setTimeout(() => { this.$router.replace('/login') }, 1000)
            })
          })
        }
      })
    },
  },
}

</script>
<style scoped>
.box {
  display: flex;
  justify-content: center;
  align-items: center;
  height: 100vh;
}
.box-card {
  width: 500px;
}
.login-box {
  margin: 0 auto;
  width: 320px;
}
</style>

