<template>
  <div class="box">
    <el-page-header @back="$router.back()"
                    style="margin-bottom:30px"
                    content="个人信息"></el-page-header>
    <el-row :gutter="20">
      <el-col :offset="1"
              :span="6">
        <el-upload :action="$BASE_URL+'/user/avatar'"
                   :headers="headers"
                   :show-file-list="false"
                   :on-success="onSuccess">
          <div class="user-info-img">
            <img style="width:100px;height:100px;"
                 @error="handleImageError"
                 :src="form.avatar" />
          </div>
        </el-upload>
      </el-col>

      <el-col :span="16">
        <el-form ref="form"
                 label="right"
                 label-width="auto">
          <el-row>
            <el-col :span="12">
              <el-form-item label="角色:">
                <el-tag v-for="(role, index) in form.roleList"
                        style="margin-right:5px"
                        :key="index">{{ role.name }} </el-tag>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row>
            <el-col :span="12">
              <el-form-item label="账号:"
                            prop="username">
                {{form.username}}
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="姓名:"
                            prop="name">
                {{form.name}}
              </el-form-item>
            </el-col>
          </el-row>
        </el-form>
      </el-col>
    </el-row>
    <el-divider></el-divider>
    <el-form ref="form"
             style="margin:20px"
             label="right"
             label-width="auto"
             :rules="rules"
             :model="form">

      <el-form-item label="部门"
                    prop="departmentName">
        {{form.departmentName}}
      </el-form-item>
      <el-form-item label="职位"
                    prop="positionName">
        {{form.positionName}}
      </el-form-item>
      <el-form-item label="邮箱"
                    prop="email">
        <el-input v-model="form.email"
                  placeholder="请输入邮箱" />
      </el-form-item>
      <el-form-item label="联系电话"
                    prop="phone">
        <el-input v-model="form.phone"
                  placeholder="请输入联系电话" />
      </el-form-item>
      <el-form-item label="备注"
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
  margin: 0 auto;
  width: 50%;
  box-shadow: 0 1px 4px rgba(0, 21, 41, 0.2);
  padding: 20px;
}
.user-info-img {
  position: relative;
  display: inline-block;
  border: 2px solid #e9e4e4;
}
.user-info-img:hover:after {
  content: "+";
  position: absolute;
  left: 0;
  right: 0;
  top: 0;
  bottom: 0;
  color: #eee;
  background: rgba(0, 0, 0, 0.5);
  font-size: 24px;
  font-style: normal;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  cursor: pointer;
  line-height: 100px;
}
</style>