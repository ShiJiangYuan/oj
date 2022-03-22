# 项目演示

http://sjyweb.top/

账号：test

密码：111

# 项目配置

## 1、项目整体导入IDEA

![image-20220322154237037](https://gitee.com/ShiJiangYuan/imgs/raw/master/img/image-20220322154237037.png)

## 2、构建项目

### 打开方式

![image-20220322154335557](https://gitee.com/ShiJiangYuan/imgs/raw/master/img/image-20220322154335557.png)

### 将文件进行标记

![image-20220322154419773](https://gitee.com/ShiJiangYuan/imgs/raw/master/img/image-20220322154419773.png)

**配置SDK之类的自行配置（一般之前别的项目配置过的就不用再配置了）**

### 记得配置maven仓库（不然依赖可能下载不下来

### 项目文件的存储路径等都在yml里有对应的注释

## 3、安装yarn

`yarn install`报以下错误

![在这里插入图片描述](https://gitee.com/ShiJiangYuan/imgs/raw/master/img/image-20220322172048072.png)

解决办法：以管理员身份运行`powershell`

输入
`set-ExecutionPolicy RemoteSigned`
执行策略 输入 `Y`，完毕。

**执行`yarn install`报错的话一般是node版本问题，下面有版本信息** 

## 4、数据库（必须）

创建一个名为oj的数据库

然后将oj.sql导入即可

## 5、 修改配置文件（必须）

**修改`OjApplication.java`里的配置内容（路径）**

# 版本信息

### node: 16.13.1（14.xx.xx版本也可以，12.xx.xx的版本不行，其他没有试）

### Vue: 2

### SpringBoot: 2.5.9 

# bug

目前项目存在一些已知bug（安全性方面的），项目运行没有任何问题