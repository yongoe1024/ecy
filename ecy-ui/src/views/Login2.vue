<template>
  <div class="root">

    <div class="box">
      <el-form :rules="rules"
               ref="form"
               :model="form">
        <h1 class="titleText">{{$TITLE}}</h1>

        <el-form-item prop="username">
          <input v-model="form.username"
                 placeholder="请输入用户名或邮箱" />
        </el-form-item>

        <el-form-item prop="password">
          <input type="password"
                 v-model="form.password"
                 @keyup.enter="submitForm"
                 placeholder="请输入密码" />
        </el-form-item>
        <el-form-item>
          <el-button style="width: 100%; border-radius: 50px;"
                     type="primary"
                     @click="submitForm">登录</el-button>
        </el-form-item>
        <div class="end">
          <router-link :to="{path: 'register'}">注册账号</router-link>
          <router-link :to="{path: 'forget'}">忘记密码？</router-link>
        </div>
      </el-form>
    </div>

  </div>
</template>

<script>
export default {
  data () {
    return {
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
          this.axios.post('/login', this.form).then((token) => {
            // 存入 session
            window.localStorage.setItem('token', token)
            // 取出重定向的网页
            let path = this.$route.query.redirect
            this.$router.replace(
              path == '/login' || path == undefined ? '/home' : path
            )
          }).catch(() => {
            this.updateCaptcha()
            this.form.code = ''
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
  width: 100%;
  position: absolute;
  background-image: url("../assets/back2.jpg");
  background-size: 100% 100%;
  display: flex;
  justify-content: center;
  align-items: center;
}
.titleText {
  font: 39px "Times New Roman", Times, serif;
  text-align: center;
  color: #ffffff;
}
.box {
  width: 330px;
  margin: 0 auto;
}
.end {
  display: flex;
  margin: 20px auto;
  justify-content: space-between;
}
.end a {
  font-weight: 500;
  text-decoration: none;
  font-size: 14px;
  color: rgb(203, 215, 217);
}
.qq {
  width: 50%;
  margin: 40px auto;
  text-align: center;
  color: rgb(140, 149, 150);
  font-size: 13px;
}
input {
  width: 300px;
  height: 46px;
  border: rgba(255, 255, 255, 0.2) 2px solid !important;
  border-radius: 50px;
  font: 15px "microsoft yahei", Helvetica, Tahoma, Arial, "Microsoft jhengHei";
  background-color: transparent;
  color: #ffffff !important;
  padding-left: 20px;
}
</style>