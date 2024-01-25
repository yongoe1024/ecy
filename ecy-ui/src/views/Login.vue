<template>
  <div class="root">
    <div class="right-box">
      <el-form :rules="rules"
               ref="form"
               :model="form"
               style="width: 320px;">
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
        <!-- <el-form-item prop="code">
          <el-input type="text"
                    @keydown.enter.native="submitForm"
                    v-model="form.code"
                    placeholder="点击图片更换验证码"></el-input>
          <img @click="updateCaptcha"
               class="captcha"
               :src="captchaUrl" />
        </el-form-item> -->
        <el-form-item>
          <el-button style="width: 100%"
                     type="primary"
                     @click="submitForm">登录</el-button>
        </el-form-item>
        <div class="end">
          <router-link :to="{path: 'register'}">注册账号</router-link>
          <router-link :to="{path: 'forget'}">忘记密码？</router-link>
        </div>
        <!-- <div class="qq">
          社交账号登录<br>
          <a :href="$BASE_URL+'/oauth/qq/redirect'">
            <img style="width:25px;margin:10px"
                 src="../assets/icon_QQ.png">
          </a>
        </div> -->
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
            window.localStorage.clear()
            window.sessionStorage.clear()
            window.localStorage.setItem('token', token)
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
  position: fixed !important;
  background-image: url("../assets/back.jpg");
  background-size: 100% 100%;
}
.titleText {
  font-size: 25px;
}
.right-box {
  float: right;
  height: 100%;
  width: 450px;
  background-color: white;
  display: flex;
  justify-content: center;
  align-items: center;
}
.captcha {
  position: absolute;
  height: 40px;
  width: 110px;
  margin-left: -110px;
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
  color: rgb(110, 119, 120);
}
.qq {
  width: 50%;
  margin: 40px auto;
  text-align: center;
  color: rgb(140, 149, 150);
  font-size: 13px;
}
</style>