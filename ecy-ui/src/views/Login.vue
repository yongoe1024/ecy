<template>
  <div class="root">

    <div class="login">
      <el-form v-loading="loading"
               :rules="rules"
               ref="form"
               :model="form"
               class="login-box">
        <h1 class="titleText">{{$TITLE}}</h1>

        <el-form-item prop="username">
          <el-input v-model="form.username"
                    placeholder="请输入用户名或邮箱"></el-input>
        </el-form-item>

        <el-form-item prop="password">
          <el-input show-password
                    v-model="form.password"
                    @keyup.enter.native="submitForm"
                    placeholder="请输入密码"></el-input>
        </el-form-item>
        <!-- <el-row>
          <el-col :span="14">
            <el-form-item prop="code">
              <el-input type="text"
                        @keydown.enter.native="submitForm"
                        v-model="form.code"
                        placeholder="点击图片更换验证码"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="9"
                  :offset="1">
            <img @click="updateCaptcha"
                 :src="captchaUrl" />
          </el-col>
        </el-row> -->

        <el-form-item>
          <el-button style="width: 100%"
                     type="primary"
                     @click="submitForm">登录</el-button>
        </el-form-item>
        <div class="end">
          <div class="registered">
            <el-link :underline="false"
                     href="/register">注册账号</el-link>
          </div>
          <div class="forget">
            <el-link :underline="false"
                     href="/forget">忘记密码？</el-link>
          </div>
        </div>
      </el-form>
    </div>

  </div>
</template>

<script>
export default {
  data () {
    return {
      loading: false,
      captchaUrl: this.$BASE_URL + '/captcha',
      form: {
        username: '',
        password: '',
        code: '',
      },
      rules: {
        username: [{ required: true, message: '请输入用户名', trigger: 'change' }],
        password: [{ required: true, message: '请输入密码', trigger: 'change' }],
        code: [{ required: true, message: '请输入验证码', trigger: 'change' }],
      },
    }
  },
  methods: {
    updateCaptcha () {
      this.captchaUrl = this.$BASE_URL + '/captcha' + '?' + new Date()
    },
    submitForm () {
      this.$refs.form.validate((valid) => {
        if (valid) {
          this.loading = true
          this.form.identifier = this.form.username
          this.form.credential = this.form.password
          this.axios.post('/login', this.form).then((token) => {
            // 存入 session
            window.localStorage.setItem('token', token)
            // 取出重定向的网页
            let path = this.$route.query.redirect
            this.$router.replace(
              path == '/login' || path == undefined ? '/index' : path
            )
            this.loading = false
          }).catch(() => {
            this.updateCaptcha()
            this.form.code = ''
            this.loading = false
          })
        }
      })
    },
  },
}
</script>

<style scoped>
.root {
  height: 100%;
  position: absolute;
  width: 100%;
  background-image: url("../assets/back.jpg");
  background-size: 100% 100%;
}
.titleText {
  text-align: left;
  color: rgb(0, 0, 0);
  font-size: 20px;
  font-family: 楷体;
}
.login {
  float: right;
  height: 100%;
  width: 450px;
  background-color: white;
  display: flex;
  justify-content: center;
  align-items: center;
}
.login-box {
  width: 320px;
}
.end {
  display: flex;
  margin: 20px auto;
  justify-content: space-between;
}
.registered {
  color: rgb(110, 119, 120);
  font-size: 13px;
  margin: 0 5px;
}
.forget {
  color: rgb(110, 119, 120);
  font-size: 13px;
  margin: 0 5px;
}
</style>