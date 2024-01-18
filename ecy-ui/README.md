# ecy-ui

## 环境

接口参数与环境变量，在 `/ecy-ui/.env`
axios请求：this.axios为原生axios方法，但设置了请求前缀、成功失败拦截器等

```
this.axios.get()
this.axios.post('/logout')
```

## 运行

```
npm config set registry https://registry.npm.taobao.org
npm install 或者 npm i
npm run serve  
```

## 部署

```
npm run build 
```

此时请求前缀为`/ecy`,发到页面所在的地址，不是后端。

```
vue.config设置过转发
 proxy: {
      '/ecy': {
        target: 'http://localhost:8081',
        ws: true,
        changeOrigin: true
      },
    }
```

但打包html，解决方法是nginx配置转发，在`ecy-ui/nginx/nginx.conf`中,已配置好结合docker所用的nginx，例如docker网络

```
server {
    listen       80 default;
    server_name  localhost;
	location   / {
		root   /usr/share/nginx/html;
		index   index.html index.htm;
		try_files $uri $uri/  /index.html;
	}
	location  /ecy {
		proxy_pass  http://ecy-server:8081;
	}
}
```

解决方法2是 env中请求前缀改为 http://localhost:8081/ecy
但是会影响 `<img src>`的链接，问题多不建议

## docker

```
dockerfile打包image方法:
在 /ecy-ui目录下,执行
docker build -t  ecy-ui:1.0 . 

docker-compose方法: 需结合/ecy/中的docker-compose.yml，无法单独启动
```

## 数据字典组件

| 参数          | 值 |
|-------------|---|
| name        |字典名
| placeholder |提示文字
| icon        |输入框图标
| type        |select , radio , tag , checkbox , checkboxtag
| size：       |【medium , small , mini】

```
<e-dict name="性别" v-model="xxx" size="mini" ></e-dict>
<e-dict name="启用"
        type="tag"
        :value="true"></e-dict>
```

## 上传按钮组件

| 参数 | 值 |
|---|---|
|url|上传接口，默认通用上传接口不需要改
|size|【medium , small , mini】
|success|成功的回调，传入后端返回的数据

```
<e-upload  @success="handleSuccess"></e-upload>
handleSuccess(data){
   console.log('图片链接为'+data);
   // data可直接用于 <img :src=""data>
}
```

## 树状输入框组件

| 参数 | 值 |
|---|---|
|data|传入数组数据
|v-model|选中数据的指定属性
|open_all|树形选择器的折叠状态，默认flase
|placeholder|输入框提示
|size| 【medium , small , mini】
|tree_props|每条数据的子属性children，对外显示名称label，v-model中的字段keyname。默认为{ children: 'children', label: 'name', keyname: 'id' }  

```
<e-input-tree
    :data="dataList"  
    v-model="form.id"></e-input-tree>
<e-input-tree
    :data="dataList"  
    :tree_props="{ children: 'children', label: 'name', keyname: 'id' } "
    v-model="form.id"
    placeholder="请选择"
    size="medium"
    :openall:"true"></e-input-tree>
```

## 富文本组件

！！！注意，对外显示时，必须加上富文本内容专用class="ql-editor"
| 参数 | 值 |
|---|---|
|v-model|内容，html格式的字符串

```
<e-editor v-model="v"></e-editor>
 <span class="ql-editor"  v-html="content"></span>
```

## 断点续传组件

固定接口，不需要改变

```
<e-chunk-upload></e-chunk-upload>
```