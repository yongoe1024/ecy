import Vue from 'vue'
import Vuex from 'vuex'

import menus from './modules/menus'
import user from './modules/user'

Vue.use(Vuex)

export default new Vuex.Store({
  modules: {
    menus,
    user,
  }
})
