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
          <h1>注册新账号</h1>
        </el-form-item>

        <el-form-item prop="username"
                      label="账号:">
          <el-input v-model="form.username"
                    placeholder="请输入账号">
          </el-input>
        </el-form-item>

        <el-form-item prop="password"
                      label="密码:">
          <el-input show-password
                    v-model="form.password"
                    placeholder="请输入密码"></el-input>
        </el-form-item>

        <el-form-item prop="name"
                      label="姓名:">
          <el-input v-model="form.name"
                    placeholder="请输入姓名">
          </el-input>
        </el-form-item>
        <el-form-item prop="email"
                      label="邮箱:">
          <el-input v-model="form.email"
                    placeholder="请输入邮箱">
          </el-input>
        </el-form-item>
        <el-form-item prop="phone"
                      label="手机号:">
          <el-input v-model="form.phone"
                    placeholder="请输入手机号">
          </el-input>
        </el-form-item>

        <el-form-item>
          <el-button style="width: 100%"
                     type="primary"
                     @click="submitForm">立即注册</el-button>
        </el-form-item>

        <el-link @click="$router.push('/login')"
                 type="primary">已有账号？立即登录</el-link>
      </el-form>
    </el-card>
  </div>
</template>


<script>
export default {
  data () {
    return {
      form: {
        name: null,
        phone: null,
        email: null,
        username: null,
        password: null,
      },
      rules: {
        name: [{ required: true, message: '请输入姓名', trigger: 'change' }],
        phone: [{ required: true, message: '请输入手机', trigger: 'change' }],
        email: [{ type: 'email', required: true, message: '请输入邮箱', trigger: 'change' }],
        username: [{ required: true, message: '请输入用户名', trigger: 'change' }],
        password: [
          { required: true, message: "请输入密码", trigger: "change" },
          { min: 6, message: '密码长度必须大于6', trigger: 'change' }
        ],
      },
    }
  },
  methods: {
    submitForm () {
      this.$refs.form.validate((valid) => {
        if (valid) {
          this.axios.post('/register', this.form).then((token) => {
            window.localStorage.setItem('token', token)
            setTimeout(() => { this.$router.replace('/index') }, 1000)
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
  position: fixed !important;
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

