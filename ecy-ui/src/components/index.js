import Vue from 'vue'
import Dict from '@/components/e-dict'
import Upload from '@/components/e-upload'
import Tree from '@/components/e-input-tree'
import UploadChunk from '@/components/chunk-upload/e-chunk-upload.vue'
import Editor from '@/components/e-editor'
import Download from '@/components/e-download'

Vue.component('e-dict', Dict)
Vue.component('e-upload', Upload)
Vue.component('e-input-tree', Tree)
Vue.component('e-chunk-upload', UploadChunk)
Vue.component('e-editor', Editor)
Vue.component('e-download', Download)