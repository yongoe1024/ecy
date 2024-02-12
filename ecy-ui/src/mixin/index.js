import Vue from 'vue'

Vue.mixin({
  methods: {
    handleImageError (e) {
      e.target.src = require('@/assets/no-img.jpg')
      e.target.onerror = null
    },
  }
})