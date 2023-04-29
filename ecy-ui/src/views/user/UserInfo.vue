<template>
  <div class="box">
    <el-page-header @back="$router.back()"
                    style="margin-bottom:30px"
                    content="用户信息"></el-page-header>
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
               @error="setDefaultImage"
               :src="form.avatar" />
        </el-upload>
        <div style="color:white;position:relative;bottom:48px;background-color: rgb(255,255,255,0.5);">&emsp;&emsp;点击修改头像&emsp;&emsp;&emsp;</div>
      </el-form-item>
      <el-form-item label="角色:">
        <el-tag v-for="(role, index) in form.roleList"
                :key="index">{{ role.name }} </el-tag>
      </el-form-item>
      <el-form-item label="账号:"
                    prop="username">
        {{form.username}}
      </el-form-item>
      <el-form-item label="姓名:"
                    prop="name">
        {{form.name}}
      </el-form-item>
      <el-form-item label="部门:"
                    prop="departmentName">
        {{form.departmentName}}
      </el-form-item>
      <el-form-item label="职位:"
                    prop="positionName">
        {{form.positionName}}
      </el-form-item>
      <el-form-item label="邮箱:"
                    prop="email">
        <el-input v-model="form.email"
                  placeholder="请输入邮箱" />
      </el-form-item>
      <el-form-item label="联系电话:"
                    prop="phone">
        <el-input v-model="form.phone"
                  placeholder="请输入联系电话" />
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
                   @click="$router.push('/password')">修改密码</el-button>
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
      imgUrl: require('../../assets/no-img.png'),
      form: {
        name: null,
        email: null,
        phone: null,
        avatar: null,
        remark: null,
        roleList: [],
        username: null
      },
      rules: {
        phone: [{ required: true, message: '请输入联系电话', trigger: 'blur' }],
        email: [{ type: 'email', required: true, message: '请输入邮箱', trigger: 'blur' }],
      },
    }
  },
  mounted () {
    this.initUser()
  },
  methods: {
    setDefaultImage (e) {
      e.target.src = this.imgUrl
      e.target.onerror = null
    },
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