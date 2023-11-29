<template>
  <!-- 页面主体 -->
  <div>
    <el-container style=" height:100vh">
      <el-aside :width="isCollapse?'64px':'200px'">
        <!-- 启动路由模式 只打开一个菜单 -->
        <el-menu router
                 :collapse="isCollapse"
                 :collapse-transition="false"
                 style="height:100%"
                 class="el-menu-vertical"
                 background-color="#3e506b"
                 text-color="#FFFFFF"
                 active-text-color="#0080ff"
                 :default-active="path"
                 unique-opened>
          <el-menu-item index="/index"
                        class="el-menu-item2">
            <i class="fa fa-home"
               style="margin-left:3px;color:#FFFFFF;font-size:23px"></i>
            <span style="margin-left:20px;font-size:15px">首页</span>
          </el-menu-item>
          <el-submenu v-for="(item, index) in routes"
                      :index="index+''"
                      :key="index"
                      v-show="item.isShow">
            <template slot="title">
              <i :class="item.icon"
                 style="color:#FFFFFF;font-size:18px"></i>
              <span style="margin-left:5px;font-size:15px">{{ item.name }}</span>
            </template>
            <!-- 子选项 -->
            <el-menu-item style="width:180px"
                          :index="children.path"
                          v-for="(children, indexj) in item.children"
                          :key="indexj"
                          v-show="children.isShow">
              <i :class="children.icon"
                 style="color: #FFFFFF;font-size:15px"></i>
              <span style="font-size:15px">{{ children.name }}</span>
            </el-menu-item>
          </el-submenu>
        </el-menu>
      </el-aside>

      <el-container>
        <!-- 页头 -->
        <el-header class="homeHeader "
                   height="50px">
          <div class="homeHeaderLeft">
            <i class="el-icon-s-fold"
               style="font-size:25px;cursor:pointer;"
               @click="collapse"></i>
            <!-- 面包屑 -->
            <div style="margin-left:10px">
              <el-breadcrumb>
                <el-breadcrumb-item>首页</el-breadcrumb-item>
                <el-breadcrumb-item v-if="$router.currentRoute.path != '/index'">{{$router.currentRoute.name }}</el-breadcrumb-item>
              </el-breadcrumb>
            </div>
          </div>

          <div class="homeHeaderLeft">
            <letter-icon style="margin:0 20px"></letter-icon>
            <el-dropdown class="userinfo"
                         @command="handleCommand">
              <img :src="user.avatar"
                   @error="setDefaultImage" />
              <el-dropdown-menu slot="dropdown">
                <el-dropdown-item command="userinfo">个人中心</el-dropdown-item>
                <el-dropdown-item command="logout">退出登录</el-dropdown-item>
              </el-dropdown-menu>
            </el-dropdown>
          </div>

        </el-header>
        <!-- 主体内容 -->
        <el-main>
          <keep-alive>
            <router-view />
          </keep-alive>
        </el-main>
      </el-container>
    </el-container>
  </div>
</template>

<script>
export default {
  components: {
    letterIcon: () => import('./letter/LetterIcon.vue')
  },
  computed: {
    user () {
      return this.$store.state.user.user
    },
    routes () {
      return this.$store.state.menus.routes
    },
    path () {
      return this.$router.currentRoute.path
    }
  },
  data () {
    return {
      imgUrl: require('../assets/no-img.jpg'),
      isCollapse: false,
      asideWidth: '200px',
    }
  },
  mounted () {
    //获取用户信息
    this.axios.post('/user/info').then(data => this.$store.commit('initUser', data)).catch(e => { })
  },
  methods: {
    setDefaultImage (e) {
      e.target.src = this.imgUrl
      e.target.onerror = null
    },
    collapse () {
      this.isCollapse = !this.isCollapse
    },
    // 头像个人信息
    handleCommand (command) {
      if (command == 'userinfo') {
        this.$router.push('/userinfo')
      } else if (command == 'logout') {
        this.logout()
      }
    },
    logout () {
      this.$confirm('此操作将退出登录, 是否继续?', '提示', { type: 'warning' }).then(() => {
        this.axios.post('/logout')
        this.$store.commit('initRoutes', [])
        this.$store.commit('initUser', {})
        window.localStorage.removeItem('token')
        this.$router.replace('/login')
        location.reload()
      }).catch(e => { })
    },
  },
};
</script>
<style >
.el-submenu__title {
  display: flex;
  align-items: center;
}
.el-submenu__title span {
  white-space: normal;
  line-height: 20px;
  padding-right: 20px;
}
.el-menu-item {
  display: flex;
  align-items: center;
  padding-right: 20px !important;
}
.el-menu-item span {
  white-space: normal;
  line-height: 20px;
}
.homeHeader {
  display: flex;
  justify-content: space-between;
  box-shadow: 0 1px 4px rgba(0, 21, 41, 0.3);
  background: #f3f3f6;
}
.homeHeaderLeft {
  display: flex;
  align-items: center;
  justify-content: center;
}
.userinfo {
  cursor: pointer;
  margin-top: 3px;
  margin-right: 50px;
  color: #3e506b;
}
.userinfo img {
  width: 42px;
  height: 42px;
  border-radius: 45%;
  border: 1px solid #c0bbbb;
}
/*.el-menu-item { 子选项背景
  background-color: #3f4761 !important;
}*/
.el-menu-item2 {
  background-color: #3e506b !important;
}
.el-menu {
  border-right-width: 0 !important;
}
.el-menu-vertical:not(.el-menu--collapse) {
  width: 200px;
}
.el-aside {
  transition: width 0.5s;
  -webkit-transition: width 0.5s;
  -moz-transition: width 0.5s;
  -webkit-transition: width 0.5s;
  -o-transition: width 0.5s;
}
</style>