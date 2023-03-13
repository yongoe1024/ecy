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
on-success  成功的回调，传入接口返回的信息
```
<upload url="/api/upload"  @on-success="handleSuccess"></upload>
```

## Tree 选择器组件  
快捷方法：url为请求路径，接口必须返回List，树形结构
正常方法：data传入List数据 ，格式为树形  

data ：数据
url：请求接口
tree_props：默认为{ children: 'children', label: 'name', keyname: 'id' }  
open_all  树形选择器， 默认折叠状态，默认flase
placeholder  输入框提示文字
```
<tree  url="/menu/tree" 或 :data="dataList"  
    :tree_props="{ children: 'children', label: 'name', keyname: 'id' } "
    placeholder="请选择"
    :openall:"true"></tree>
```

## 环境
接口参数与环境变量，在 `/ecy-ui/.env.*`
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
docker-compose方法:

docker-compose down 
docker-compose build --no-cache
docker-compose up -d

dockerfile方法:

在 /ecy-ui目录下,执行
docker build -t  ecy-ui:1.0 . 
```
