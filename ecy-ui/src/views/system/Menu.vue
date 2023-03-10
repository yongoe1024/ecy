<template>
  <div>
    <!-- 按钮 -->
    <div class="button">
      <el-button type="primary"
                 size="small"
                 icon="el-icon-search"
                 @click="getList">刷新</el-button>
      <el-button type="primary"
                 size="small"
                 @click="handleShowAddEdit"
                 icon="el-icon-plus">添加</el-button>

    </div>

    <!-- 表格 -->
    <el-table v-loading="loading"
              element-loading-text="拼命加载中"
              element-loading-spinner="el-icon-loading"
              element-loading-background="rgba(0, 0, 0, 0.8)"
              :data="dataList"
              row-key="id"
              :tree-props="{children: 'children'}"
              stripe
              border
              style="width: 100%">
      <el-table-column prop="name"
                       width="150"
                       label="菜单名"></el-table-column>
      <el-table-column prop="type"
                       label="类型"
                       align="center">
        <template slot-scope="scope">
          <span v-if="scope.row.type===1">目录</span>
          <span v-else-if="scope.row.type===2">菜单</span>
          <span v-else-if="scope.row.type===3">按钮</span>
        </template>
      </el-table-column>
      <el-table-column prop="url"
                       label="接口url"
                       align="center"></el-table-column>
      <el-table-column prop="component"
                       label="组件位置"
                       align="center"></el-table-column>
      <el-table-column prop="icon"
                       label="图标"
                       align="center">
        <template slot-scope="scope">
          <i :class="scope.row.icon"></i>
        </template>
      </el-table-column>
      <el-table-column prop="sort"
                       label="顺序"
                       align="center"></el-table-column>
      <el-table-column prop="enabled"
                       label="是否启用"
                       align="center">
        <template slot-scope="scope">
          <el-tag v-if="scope.row.enabled"
                  type="success">启用
          </el-tag>
          <el-tag v-else
                  type="danger">禁用
          </el-tag>
        </template>
      </el-table-column>
      <el-table-column label="操作"
                       align="center"
                       width="140"
                       fixed="right">
        <template slot-scope="scope">
          <el-button style="padding: 3px"
                     type="primary"
                     @click="handleShowUpdateEdit(scope.row)">编辑</el-button>
          <el-button style="padding: 3px"
                     type="danger"
                     @click="handleDelete(scope.row)">删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <el-dialog :visible.sync="dialogVisible"
               :title="dialogTitle"
               @close="reset"
               :close-on-click-modal="false"
               width="600px">
      <el-form ref="form"
               :model="form"
               label-width="auto"
               style="margin:20px"
               :rules="rules">
        <el-form-item label="菜单名"
                      prop="name">
          <el-input v-model="form.name"
                    placeholder="请输入菜单名" />
        </el-form-item>
        <el-form-item label="类型"
                      prop="type">
          <el-select v-model="form.type"
                     placeholder="请选择类型">
            <el-option v-for="item in [{name:'目录',type:1},{name:'菜单',type:2},{name:'按钮',type:3}]"
                       :key="item.type"
                       :label="item.name"
                       :value="item.type">
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="父菜单"
                      prop="parentId">
          <Tree :data="dataList"
                v-model="form.parentId"
                :tree_props="{ children: 'children', label: 'name', keyname: 'id' }"
                placeholder="请选择父菜单"></Tree>
        </el-form-item>
        <el-form-item label="组件位置"
                      prop="component">
          <el-input v-model="form.component"
                    :disabled="popoverDisabled"
                    placeholder="请输入组件位置" />
        </el-form-item>
        <el-form-item label="接口url"
                      prop="url">
          <el-input v-model="form.url"
                    placeholder="请输入接口url(ant规则)" />
        </el-form-item>

        <el-form-item label="图标"
                      prop="icon">
          <e-icon-picker v-model="form.icon" />
        </el-form-item>
        <el-form-item label="顺序"
                      prop="sort">
          <el-input-number v-model="form.sort"
                           :min="0"></el-input-number>
        </el-form-item>
        <el-form-item label="是否启用"
                      prop="enabled">
          <el-switch v-model="form.enabled"
                     active-text="启用"
                     inactive-text="禁用"
                     active-color="#13ce66"
                     inactive-color="#ff4949"></el-switch>
        </el-form-item>
      </el-form>
      <span slot="footer">
        <el-button @click="dialogVisible = false">取 消</el-button>
        <el-button type="primary"
                   @click="handleAddOrUpdate">确 定</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
export default {
  components: {},
  props: {},
  computed: {
    type () {
      return this.form.type
    }
  },
  watch: {
    type (n) {
      if (n == 1) {
        this.popoverDisabled = true
        this.form.parentId = 0
        this.form.url = ''
        this.form.component = ''
      } else {
        this.popoverDisabled = false
      }
    }
  },
  data () {
    return {
      popoverDisabled: true,

      loading: false,
      dialogVisible: false,
      dialogTitle: '',

      dataList: [],
      form: {
        parentId: null,
        name: null,
        type: null,
        url: null,
        component: null,
        icon: null,
        sort: null,
        enabled: true,
      },
      rules: {
        parentId: [{ required: true, message: '请选择父菜单', trigger: 'change' }],
        name: [{ required: true, message: '请输入菜单名', trigger: 'change' }],
        type: [{ required: true, message: '请输入类型', trigger: 'change' }],
        sort: [{ required: true, message: '请输入顺序', trigger: 'change' }],
      },
    }
  },
  mounted () {
    this.getList()
  },
  methods: {
    reset () {
      this.form = this.$options.data().form
    },
    handleShowAddEdit () {
      this.dialogTitle = '添加'
      this.dialogVisible = true
    },
    handleShowUpdateEdit (row) {
      this.dialogTitle = '修改'
      Object.assign(this.form, row)
      this.form.parentId = this.form.parentId + ''
      this.dialogVisible = true
    },
    handleAddOrUpdate () {
      this.$refs.form.validate((valid) => {
        if (valid) {
          if (this.form.id) {
            this.axios.post('/system/menu/update', this.form).then(() => {
              this.getList()
              this.dialogVisible = false
            })
          } else {
            this.axios.post('/system/menu/add', this.form).then(() => {
              this.getList()
              this.dialogVisible = false
            })
          }
        }
      })
    },
    handleDelete (row) {
      this.$confirm('此操作将永久删除这条数据, 是否继续?', '提示', { type: 'warning' }).then(() => {
        this.axios.post('/system/menu/delete/' + row.id).then(() => this.getList())
      })
    },
    // 初始化数据
    getList () {
      this.loading = true
      this.axios.post('/system/menu/tree').then(data => {
        this.loading = false
        this.dataList = data
      })
    },
  },
}
</script>
<style scoped>
.button {
  margin: 0 0 15px 0;
  display: flex;
}
.button * {
  margin: 0 8px 0 0;
}
</style>