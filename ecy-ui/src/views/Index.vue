<template>
  <!-- 页面主体 -->
  <div>
    <el-container style="height:100vh">
      <el-aside :width="isCollapse?'64px':'200px'">
        <!-- 启动路由模式 只打开一个菜单 -->
        <el-menu router
                 :collapse="isCollapse"
                 :collapse-transition="false"
                 style="height:100%"
                 class="el-menu-vertical"
                 background-color="#3e506b"
                 text-color="#FFFFFF"
                 :default-active="$router.currentRoute.path"
                 unique-opened>
          <el-menu-item index="/home">
            <i class="fa fa-home"
               style="margin-left:3px;color:#FFFFFF;font-size:23px"></i>
            <span style="margin-left:20px;font-size:15px">首页</span>
          </el-menu-item>
          <e-menu-item v-for="(item, index) in routes"
                       :key="index"
                       :item="item"></e-menu-item>
        </el-menu>
      </el-aside>

      <el-container>
        <!-- 页头 -->
        <el-header class="homeHeader"
                   height="50px">
          <div>
            <i class="el-icon-s-fold"
               style="font-size:25px;cursor:pointer;style:margin-right:10px;"
               @click="isCollapse=!isCollapse"></i>
            <!-- 面包屑 -->
            <el-breadcrumb>
              <el-breadcrumb-item v-for="(item,index) in pathList"
                                  :key="index">{{item.name }}</el-breadcrumb-item>
            </el-breadcrumb>
          </div>

          <div>
            <letter-icon></letter-icon>
            <el-dropdown @command="handleCommand">
              <div>
                <img :src="user.avatar"
                     class="userimg"
                     @error="setDefaultImage" />
                <i class="el-icon-caret-bottom"
                   style="margin-top:30px;"></i>
              </div>
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
            <router-view v-if="$route.meta.keepAlive" />
          </keep-alive>
          <router-view v-if="!$route.meta.keepAlive" />
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
  },
  watch: {
    $route: {
      immediate: true,
      handler (to, from) {
        this.pathList = to.matched
      }
    }
  },
  data () {
    return {
      pathList: [],
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
        this.axios.post('/logout').then(() => {
          window.localStorage.clear()
          window.sessionStorage.clear()
          this.$router.replace('/login')
          location.reload()
        }).catch(e => {
          window.localStorage.clear()
          window.sessionStorage.clear()
          this.$router.replace('/login')
          location.reload()
        })
      }).catch(e => { })
    },
  },
};
</script>
<style >
.homeHeader {
  display: flex;
  justify-content: space-between;
  box-shadow: 0 1px 4px rgba(0, 21, 41, 0.3);
  background: #fbfbfb;
}
.homeHeader * {
  display: flex;
  align-items: center;
  justify-content: center;
  margin-right: 5px;
}
.userimg {
  width: 42px;
  height: 42px;
  border-radius: 20%;
  border: 1px solid #c0bbbb;
  cursor: pointer;
}
.el-aside {
  transition: width 0.3s;
  -webkit-transition: width 0.3s;
  -moz-transition: width 0.3s;
  -webkit-transition: width 0.3s;
  -o-transition: width 0.3s;
  color: #76bdda;
  overflow: hidden;
}
</style>