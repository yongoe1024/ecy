<template>
  <div>
    <!-- 按钮 -->
    <div class="button">
      <el-button size="mini"
                 plain
                 icon="el-icon-search"
                 @click="getList">刷新</el-button>
      <el-button type="success"
                 size="mini"
                 plain
                 @click="handleShowAddEdit"
                 icon="el-icon-plus">添加</el-button>

    </div>

    <!-- 表格 -->
    <el-table :data="dataList"
              row-key="id"
              :tree-props="{children: 'children'}"
              style="width: 100%"
              :header-cell-style="{background:'#eef1f6'}">
      <el-table-column prop="name"
                       width="150"
                       label="菜单名"></el-table-column>
      <el-table-column prop="type"
                       label="类型"
                       align="center">
        <template slot-scope="scope">
          <span v-if="scope.row.type===1">目录</span>
          <span v-else-if="scope.row.type===2">页面</span>
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
      <el-table-column prop="keepAlive"
                       label="是否缓存"
                       align="center">
        <template slot-scope="scope">
          <el-tag v-if="scope.row.keepAlive"
                  effect="dark"
                  type="success">是
          </el-tag>
          <el-tag v-else
                  effect="dark"
                  type="danger">否
          </el-tag>
        </template>
      </el-table-column>
      <el-table-column prop="isShow"
                       label="是否显示"
                       align="center">
        <template slot-scope="scope">
          <el-tag v-if="scope.row.isShow"
                  effect="dark"
                  type="success">是
          </el-tag>
          <el-tag v-else
                  effect="dark"
                  type="danger">否
          </el-tag>
        </template>
      </el-table-column>
      <el-table-column prop="enabled"
                       label="是否启用"
                       align="center">
        <template slot-scope="scope">
          <el-tag v-if="scope.row.enabled"
                  effect="dark"
                  type="success">启用
          </el-tag>
          <el-tag v-else
                  effect="dark"
                  type="danger">禁用
          </el-tag>
        </template>
      </el-table-column>
      <el-table-column label="操作"
                       align="center"
                       width="200"
                       fixed="right">
        <template slot-scope="scope">
          <el-button type="text"
                     size="mini"
                     icon="el-icon-plus"
                     @click="handleShowRowAddEdit(scope.row)">添加</el-button>
          <el-button type="text"
                     size="mini"
                     icon="el-icon-edit"
                     @click="handleShowUpdateEdit(scope.row)">编辑</el-button>
          <el-button type="text"
                     size="mini"
                     icon="el-icon-delete"
                     @click="handleDelete(scope.row)">删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <el-dialog :visible.sync="dialogVisible"
               :title="dialogTitle"
               :destroy-on-close="true"
               @close="reset"
               :close-on-click-modal="false"
               width="600px">
      <el-form ref="form"
               :model="form"
               label-width="auto"
               style="margin:20px"
               :rules="rules">
        <el-form-item label="类型"
                      prop="type">
          <el-radio v-for="(item,index) in [{name:'目录',type:1},{name:'页面',type:2},{name:'按钮',type:3}]"
                    :key="index"
                    v-model="form.type"
                    :label="item.type">{{item.name}}</el-radio>
          <el-tooltip placement="top">
            <div slot="content">
              <p>目录：用于组织页面，可放根节点和目录下</p>
              <p>页面：用于页面显示，放在目录下</p>
              <p>按钮：用于页面按钮，可指定接口url精确权限，放在页面下</p>
            </div>
            <i class="el-icon-question" />
          </el-tooltip>
        </el-form-item>
        <el-form-item label="上级菜单"
                      prop="parentId">
          <e-input-tree :data="dataList"
                        v-model="form.parentId"
                        :tree_props="{ children: 'children', label: 'name', keyname: 'id' }"
                        placeholder="请选择上级菜单"></e-input-tree>
        </el-form-item>
        <el-form-item label="菜单名"
                      prop="name">
          <el-input v-model="form.name"
                    placeholder="请输入菜单名" />
        </el-form-item>
        <el-form-item label="组件位置"
                      prop="component">
          <el-input v-model="form.component"
                    :disabled="componentDisabled"
                    placeholder="请输入组件位置" />
        </el-form-item>
        <el-form-item label="接口url"
                      prop="url">
          <el-input v-model="form.url"
                    :disabled="urlDisabled"
                    placeholder="请输入接口路径" />
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
        <el-form-item label="是否缓存"
                      prop="keepAlive">
          <el-switch v-model="form.keepAlive"
                     :disabled="componentDisabled"
                     active-text="是"
                     inactive-text="否"
                     active-color="#13ce66"
                     inactive-color="#ff4949"></el-switch>
        </el-form-item>
        <el-form-item label="是否显示"
                      prop="isShow">
          <el-switch v-model="form.isShow"
                     active-text="是"
                     inactive-text="否"
                     active-color="#13ce66"
                     inactive-color="#ff4949"></el-switch>
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
        <el-button size="medium"
                   @click="dialogVisible = false">取 消</el-button>
        <el-button size="medium"
                   type="primary"
                   @click="handleAddOrUpdate">确 定</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
export default {
  computed: {
    type () {
      return this.form.type
    }
  },
  watch: {
    type (n) {
      if (n == 1) {
        this.componentDisabled = true
        this.urlDisabled = true
        this.form.url = ''
        this.form.component = ''
      } else if (n == 3) {
        this.componentDisabled = true
        this.urlDisabled = false
        this.form.url = ''
        this.form.component = ''
      } else {
        this.componentDisabled = false
        this.urlDisabled = false
      }
    }
  },
  data () {
    return {
      componentDisabled: true,
      urlDisabled: true,

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
        keepAlive: false,
        isShow: true,
        enabled: true,
      },
      rules: {
        parentId: [{ required: true, message: '请选择上级菜单', trigger: 'change' }],
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
      this.form.parentId = 0
      this.dialogVisible = true
    },
    handleShowRowAddEdit (row) {
      this.dialogTitle = '添加'
      this.form.parentId = row.id
      if (row.type == 2)
        this.form.type = 3
      this.dialogVisible = true
    },
    handleShowUpdateEdit (row) {
      this.dialogTitle = '修改'
      Object.assign(this.form, row)
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
      }).catch(e => { })
    },
    // 初始化数据
    getList () {
      this.axios.post('/system/menu/tree').then(data => {
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