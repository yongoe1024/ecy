export default {

  state: {
    routes: []
  },
  getters: {
    getRoutes: (state) => {
      return state.routes
    }
  },
  //同步
  mutations: {
    initRoutes (state, data) {
      state.routes = data
    }
  },
  //异步
  actions: {
    InitRoutes (context, data) {
      context.commit('initRoutes', data)
    }
  }

}
