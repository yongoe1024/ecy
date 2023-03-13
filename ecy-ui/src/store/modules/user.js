export default {

  state: {
    user: {}
  },
  getters: {
    getUser (state) {
      return state.user
    }
  },
  //同步
  mutations: {
    initUser (state, data) {
      state.user = data
    }
  },
  //异步
  actions: {
    InitUser (context, data) {
      context.commit('initUser', data)
    }
  }

}
