# ecy

## 数据字典组件
name为字典名，v-model 为字典值
```
<Dict name="性别" v-model="xxx" ></Dict>
参数：
name  字典名
placeholder  下拉框中提示文字
icon        下拉框中的输入框图标
size： 下拉框尺寸 default: 'small'
```

## 上传按钮组件  
url  接口
size 尺寸，medium/small/mini
on-success  成功的回调，传入后端返回的信息
```
<upload url="/api/upload"  @on-success="handleSuccess"></upload>
```

## Tree 选择器组件  
data传入List数据 ，格式为树形  
data ：数据
tree_props：默认为{ children: 'children', label: 'name', keyname: 'id' }  
v-model: 被选数据的主键 keyname
open_all  树形选择器的折叠状态，默认flase，不展开
placeholder  输入框提示文字
```
<tree :data="dataList"  
    :tree_props="{ children: 'children', label: 'name', keyname: 'id' } "
    v-model="form.id"
    placeholder="请选择"
    :openall:"true"></tree>
```

## 环境
接口参数与环境变量，在 `/ecy-ui/.env`
```
npm config set registry https://registry.npm.taobao.org
npm install 或者 npm i
```

##  运行
```
npm run serve  
```

## 部署
```
npm run build 
```
## docker部署
```
docker-compose方法: 需结合/ecy/中的docker-compose.yml，无法单独启动

dockerfile方法:

在 /ecy-ui目录下,执行
docker build -t  ecy-ui:1.0 . 
```
