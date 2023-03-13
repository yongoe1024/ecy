<template>
  <div class="box">
    <el-form ref="form"
             style="margin:20px"
             label="right"
             label-width="auto"
             :rules="rules"
             :model="form">
      <el-form-item label="头像:">
        <el-upload :action="uploadURL"
                   :headers="headers"
                   :show-file-list="false"
                   :on-success="onSuccess">
          <img style="width:150px;"
               :src="form.avatar" />
        </el-upload>
        <div style="color:white;position:relative;bottom:48px;background-color: rgb(255,255,255,0.5);">&emsp;&emsp;点击修改头像&emsp;&emsp;&emsp;</div>
      </el-form-item>
      <el-form-item label="角色:">
        <el-tag v-for="(role, index) in form.roleList"
                :key="index">{{ role.nameZh }} </el-tag>
      </el-form-item>
      <el-form-item label="账号:"
                    prop="identifier">
        <el-input v-model="form.identifier"
                  placeholder="请输入" />
      </el-form-item>
      <el-form-item label="姓名:"
                    prop="name">
        <el-input v-model="form.name"
                  placeholder="请输入姓名" />
      </el-form-item>
      <el-form-item label="邮箱:"
                    prop="email">
        <el-input v-model="form.email"
                  placeholder="请输入邮箱" />
      </el-form-item>
      <el-form-item label="手机号码:"
                    prop="phone">
        <el-input v-model="form.phone"
                  placeholder="请输入手机号码" />
      </el-form-item>
      <el-form-item label="备注:"
                    prop="remark">
        <el-input v-model="form.remark"
                  placeholder="请输入备注" />
      </el-form-item>
      <el-form-item>
        <el-button type="primary"
                   size="small"
                   @click="handleUpdate">修改信息</el-button>
        <el-button type="danger"
                   size="small"
                   @click="$router.replace('/password')">修改密码</el-button>
      </el-form-item>

    </el-form>
  </div>
</template>


<script>
export default {
  data () {
    return {
      uploadURL: this.$BASE_URL + '/user/avatar',
      headers: {
        Authorization: window.localStorage.getItem('token')
      },

      form: {
        name: null,
        email: null,
        phone: null,
        avatar: null,
        remark: null,
        roleList: [],
        identifier: null
      },
      rules: {
        identifier: [{ required: true, message: '请输入账号', trigger: 'blur' }],
        name: [{ required: true, message: '请输入昵称', trigger: 'blur' }],
        phone: [{ required: true, message: '请输入号码', trigger: 'blur' }],
        email: [{ type: 'email', required: true, message: '请输入号码', trigger: 'blur' }],
      },
    }
  },
  mounted () {
    this.initUser()
  },
  methods: {
    onSuccess () {
      this.initUser()
    },
    handleUpdate () {
      this.axios.post('/user/update', this.form).then((data) => {
        setTimeout(() => { this.initUser() }, 1000)
      })
    },
    initUser () {
      this.axios.post('/user/info').then(data => {
        this.$store.commit('initUser', data)
        this.form = data
      })
    }
  },
}

</script>
<style scoped>
.box {
  margin: 20px auto;
  width: 50%;
  min-height: 70%;
}
.box-card {
  width: 500px;
}
.login-box {
  margin: 0 auto;
  width: 320px;
}
</style>