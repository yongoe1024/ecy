<#assign myParam= ["create_time","update_time","create_by","update_by"] />
<template>
    <div>
        <!-- 搜索 -->
        <#if get>
        <div class="head" v-auth="'查'">
            <#list table.fields as field>
            <#if field.keyFlag || myParam?seq_contains(field.name)>
            <#elseif  field.propertyType == "Boolean">
            <el-select v-model="queryParam.${field.propertyName}" size="small" placeholder="${field.comment}">
                <el-option label="是" :value="true"></el-option>
                <el-option label="否" :value="false"></el-option>
            </el-select>
            <#elseif field.propertyType=="LocalDate">
            <el-date-picker v-model="queryParam.${field.propertyName}"
                            value-format="yyyy-MM-dd"
                            type="date"
                            size="small"
                            placeholder="${field.comment}"></el-date-picker>
            <#elseif field.propertyType=="LocalDateTime">
            <el-date-picker v-model="queryParam.${field.propertyName}"
                            value-format="yyyy-MM-dd HH:mm:ss"
                            type="datetime"
                            size="small"
                            placeholder="${field.comment}"></el-date-picker>
            <#else>
            <el-input size="small"
                      prefix-icon="el-icon-search"
                      placeholder="请输入${field.comment}"
                      v-model="queryParam.${field.propertyName}"></el-input>
            </#if>
            </#list>
        </div>
        </#if>
        <!-- 按钮 -->
        <div class="button">
            <#if get><el-button type="primary" v-auth="'查'"
                       size="small"
                       icon="el-icon-search"
                       @click="getList">搜索</el-button></#if>
            <#if get><el-button type="primary" v-auth="'查'"
                       size="small"
                       icon="el-icon-refresh"
                       @click="resetQuery">重置</el-button></#if>
            <#if add><el-button type="primary" v-auth="'增'"
                       size="small"
                       @click="handleShowAddEdit"
                       icon="el-icon-plus">添加</el-button></#if>
            <#if upload><upload size="small" :url="$BASE_URL+'/${packageName}/${entity?lower_case}/upload'" v-auth="'导入'"
                    @success="getList"></upload></#if>
            <#if export><el-button type="success" v-auth="'导出'"
                       icon="el-icon-download"
                       size="small"
                       @click="handleExport">导出数据</el-button></#if>
        </div>

        <!-- 表格 -->
        <el-table v-loading="loading"
                  element-loading-text="拼命加载中"
                  element-loading-spinner="el-icon-loading"
                  element-loading-background="rgba(0, 0, 0, 0.8)"
                  :data="dataList"
                  stripe
                  border
                  style="width: 100%"
                  @selection-change="handleSelectionChange">
            <el-table-column type="selection"
                             width="55"></el-table-column>
            <el-table-column align="center"
                             type="index"></el-table-column>
            <#list table.fields as field>
                <#if !field.keyFlag>
                    <#if field.propertyType=="Boolean">
            <el-table-column prop="${field.propertyName}"
                             label="${field.comment}"
                             align="center">
                <template slot-scope="scope">
                    <el-tag v-if="scope.row.${field.propertyName}"
                            type="success">启用
                    </el-tag>
                    <el-tag v-else
                            type="danger">禁用
                    </el-tag>
                </template>
            </el-table-column>
                    <#else >
            <el-table-column prop="${field.propertyName}"
                             label="${field.comment}"
                             align="center"></el-table-column>
                    </#if>
                </#if>
            </#list>
            <el-table-column label="操作"
                             align="center"
                             width="140"
                             fixed="right">
                <template slot-scope="scope">
                    <#if update><el-button style="padding: 3px" v-auth="'改'"
                               type="primary"
                               @click="handleShowUpdateEdit(scope.row)">编辑</el-button></#if>
                    <#if delete><el-button style="padding: 3px" v-auth="'删'"
                               type="danger"
                               @click="handleDelete(scope.row)">删除</el-button></#if>
                </template>
            </el-table-column>
        </el-table>

        <!-- 批量删除 -->
        <#if delete><el-button style="margin-top: 8px" v-auth="'删'"
                   type="danger"
                   size="small"
                   :disabled="multipleSelection.length == 0"
                   @click="handleDeleteMany">批量删除</el-button></#if>
        <!-- 分页 -->
        <el-pagination background
                       style="display:flex;justify-content:center;"
                       @size-change="handleSizeChange"
                       @current-change="handleCurrentChange"
                       :page-size="size"
                       layout="total, sizes, prev, pager, next, jumper"
                       :total="total"></el-pagination>

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
                <#list table.fields as field>
                <#if field.keyFlag || myParam?seq_contains(field.name)>
                <#elseif field.propertyType=="Boolean">
                <el-form-item label="${field.comment}"  prop="${field.propertyName}">
                    <el-switch v-model="form.${field.propertyName}"
                               active-text="启用"
                               inactive-text="禁用"
                               active-color="#13ce66"
                               inactive-color="#ff4949"></el-switch>
                </el-form-item>
                <#elseif field.propertyType=="LocalDate">
                <el-form-item label="${field.comment}"  prop="${field.propertyName}">
                    <el-date-picker v-model="form.${field.propertyName}"
                                    value-format="yyyy-MM-dd"
                                    type="date"
                                    placeholder="${field.comment}"></el-date-picker>
                </el-form-item>
                <#elseif field.propertyType=="LocalDateTime">
                <el-form-item label="${field.comment}"  prop="${field.propertyName}">
                    <el-date-picker v-model="form.${field.propertyName}"
                                    value-format="yyyy-MM-dd HH:mm:ss"
                                    type="datetime"
                                    placeholder="${field.comment}"></el-date-picker>
                </el-form-item>
                <#else>
                <el-form-item label="${field.comment}"  prop="${field.propertyName}">
                    <el-input v-model="form.${field.propertyName}"
                              placeholder="请输入${field.comment}"/>
                </el-form-item>
                </#if>
                </#list>
            </el-form>
            <span slot="footer">
                <el-button @click="dialogVisible = false">取 消</el-button>
                <el-button type="primary" @click="handleAddOrUpdate">确 定</el-button>
            </span>
        </el-dialog>
    </div>
</template>

<script>
export default {
    components: {},
    props: {},
    data() {
        return {
            // 多选框数据
            multipleSelection: [],
            total: 0,
            current: 1,
            size: 10,
            loading: false,
            dialogVisible: false,
            dialogTitle: '',

            dataList: [],
            form: {
                <#list table.fields as field >
                <#if field.keyFlag || myParam?seq_contains(field.name)>
                <#elseif field.propertyType=="Boolean">
                ${field.propertyName}: true,
                <#else>
                ${field.propertyName}: null,
                </#if>
                </#list >
            },
            queryParam: {
                <#list table.fields as field >
                <#if field.keyFlag || myParam?seq_contains(field.name)>
                <#else >
                ${field.propertyName}: null,
                </#if>
                </#list >
            },
            rules: {
                <#list table.fields as field >
                <#if !field.keyFlag && !myParam?seq_contains(field.name)>
                ${field.propertyName}: [{required: true, message: '请输入${field.comment}', trigger: 'change'}],
                </#if>
                </#list>
            },
        }
    },
    mounted() {
        this.getList()
    },
    methods: {
        reset(){
            this.form = this.$options.data().form
        },
        resetQuery(){
            this.queryParam = this.$options.data().queryParam
        },
        handleExport() {
            this.$downloadRequest('/${packageName}/${entity?lower_case}/export', this.queryParam)
        },
        // 多选框回调
        handleSelectionChange(val) {
            <#list table.fields as field >
            <#if field.keyFlag>
            this.multipleSelection = val.map(item => item.${field.propertyName})
            </#if>
            </#list >
        },
        // 改变页码
        handleSizeChange(val) {
            this.size = val
            this.getList()
        },
        // 点击页数
        handleCurrentChange(val) {
            this.current = val
            this.getList()
        },
        handleShowAddEdit() {
            this.dialogTitle = '添加'
            this.dialogVisible = true
        },
        handleShowUpdateEdit(row) {
            this.dialogTitle = '修改'
            Object.assign(this.form, row)
            this.dialogVisible = true
        },
        handleAddOrUpdate() {
            this.$refs.form.validate((valid) => {
                if (valid) {
                    <#list table.fields as field >
                    <#if field.keyFlag>
                    if (this.form.${field.propertyName}) {
                        this.axios.post('/${packageName}/${entity?lower_case}/update', this.form).then(() => {
                            this.getList()
                            this.dialogVisible = false
                        })
                    } else {
                        this.axios.post('/${packageName}/${entity?lower_case}/add', this.form).then(() => {
                            this.getList()
                            this.dialogVisible = false
                        })
                    }
                    </#if>
                    </#list >
                }
            })
        },
        handleDelete(row) {
            this.$confirm('此操作将永久删除这条数据, 是否继续?', '提示', {type: 'warning'}).then(() => {
                <#list table.fields as field >
                <#if field.keyFlag>
                this.axios.post('/${packageName}/${entity?lower_case}/delete/' + row.${field.propertyName}).then(() => this.getList())
                </#if>
                </#list >
            })
        },
        handleDeleteMany() {
            this.$confirm('此操作将永久删除 [' + this.multipleSelection.length + '] 条数据, 是否继续?', '提示', {type: 'warning'}).then(() => {
                this.axios.post('/${packageName}/${entity?lower_case}/delete/' + this.multipleSelection).then(() => this.getList())
            })
        },
        // 初始化数据
        getList() {
            this.loading = true
            this.axios.post('/${packageName}/${entity?lower_case}/page?current=' + this.current + '&size=' + this.size, this.queryParam).then(data => {
                this.loading = false
                this.dataList = data.list
                this.total = data.total - 0
            })
        },
    },
}
</script>
<style scoped>
.head {
  display: flex;
  flex-wrap: wrap;
}
.head .el-input {
  width: 200px !important;
}
.head * {
  margin: 0 8px 8px 0;
}

.button {
    margin: 0 0 15px 0;
    display: flex;
}
.button * {
    margin: 0 8px 0 0;
}
</style>