<template>
  <div class="home">
    <el-row :gutter="20">
      <el-col :span="12">
        <h2>在线评测系统</h2>
        <p>一直想做一款Online Judge。但是时间有限，目前仅仅开发了权限分配、客观题考试等功能，目前仅支持客观题考试，简答题的存储目前暂未解决（不想使用文本存储），正在寻求更好的解决方法。所有前端后台代码封装过后十分精简易上手，出错概率低。目前不支持移动客户端访问。系统会陆续更新一些实用功能。下一步会优先开发代码评测功能。</p>
        <b>当前版本：</b>
        <span>v1.0.0</span>
        <p style="margin-top:10px;margin-bottom:20px">
          <span class="free">¥免费开源</span>
        </p>
        <el-button type="primary" plain size=mini icon="el-icon-cloudy" @click="gitee">访问码云</el-button>
        <el-button style="box-sizing: border-box;" type="primary" plain size=mini @click="github">
          <i class="iconfont icon-github-fill" style="font-size:12px"></i> 访问GitHub
        </el-button>
      </el-col>
      <el-col :span="12">
        <h2>技术选型</h2>
        <el-row :gutter="5">
          <el-col :span="5">
            <h4>后端技术</h4>
            <ul>
              <li>SpringBoot</li>
              <li>JWT</li>
              <li>MyBatis-Plus</li>
              <li>Lombok</li>
              <li>Swagger</li>
              <li>Hutool</li>
              <li>Thymeleaf</li>
              <li>...</li>
            </ul>
          </el-col>
          <el-col :span="5">
            <h4>前端技术</h4>
            <ul>
              <li>Vue</li>
              <li>VueRouter</li>
              <li>Vuex</li>
              <li>elementUI</li>
              <li>Axios</li>
              <li>Echarts</li>
              <li>Moment</li>
              <li>...</li>
            </ul>
          </el-col>
        </el-row>
      </el-col>
    </el-row>
    <el-divider class="fen"></el-divider>
    <el-row :gutter="20">
      <!-- 联系信息 -->
      <el-col :span="8">
        <el-card class="box-card">
          <div slot="header" class="clearfix">
            <span>联系信息</span>
          </div>
          <p>
            <span class="el-icon-s-promotion"> 官网：<a href="http://sjyweb.top" target="_blank">http://sjyweb.top</a></span>
          </p>
          <p>
            <span class="el-icon-user-solid"> QQ：2577380101</span>
          </p>
        </el-card>
      </el-col>
      <!-- 更新日志 -->
      <el-col :span="8">
        <el-card class="box-card">
          <div slot="header" class="clearfix">
            <span>更新日志</span>
          </div>
          <el-collapse>
            <el-collapse-item title="v1.0.0 - 2022-03-05">
              <ol start="1">
                <li>在线测试系统前后端分离测试版本正式发布</li>
              </ol>
            </el-collapse-item>
          </el-collapse>
        </el-card>
      </el-col>
      <!-- 捐赠支持 -->
      <el-col :span="8">
        <el-card class="box-card">
          <div slot="header" class="clearfix">
            <span>捐赠支持</span>
          </div>
           <img style="display:block;width:100%" src="../../assets/pay.jpg"/>
          <span>你可以请作者喝杯咖啡表示鼓励</span>
        </el-card>
      </el-col>
    </el-row>
  </div>
</template>

<script>

export default {
  methods: {
    gitee () {
      const href = 'https://gitee.com/ShiJiangYuan/oj'
      window.open(href, '_blank')
    },
    github () {
      const href = 'https://github.com/ShiJiangYuan/oj'
      window.open(href, '_blank')
    }
  }
}
</script>

<style lang="less" scoped>
h2 {
  margin-top: 10px;
  font-size: 26px;
  font-weight: 100;
  color: #676a6c;
  margin-bottom: 30px;
}
p {
  font-family: open sans,Helvetica Neue,Helvetica,Arial,sans-serif;
  font-size: 13px;
  color: #676a6c;
  margin-bottom: 10px;
}
b {
  font-family: open sans,Helvetica Neue,Helvetica,Arial,sans-serif;
  font-size: 13px;
  color: #676a6c;
}
span {
  font-family: open sans,Helvetica Neue,Helvetica,Arial,sans-serif;
  font-size: 13px;
  color: #676a6c;
}
.free {
  background-color: #ffeded;
  border-color: #ffdbdb;
  color: #ff4949;    height: 28px;
  line-height: 26px;
}
h4 {
  font-family: open sans,Helvetica Neue,Helvetica,Arial,sans-serif;
  font-size: 13px;
  color: #676a6c;
  font-weight: 500;
  line-height: 1.1;
  margin-bottom: 20px;
}
ul li {
  font-family: open sans,Helvetica Neue,Helvetica,Arial,sans-serif;
  font-size: 13px;
  color: #676a6c;
  text-decoration: none;
  list-style: none;
  margin: 2px 0;
}
.fen {
  font-family: open sans,Helvetica Neue,Helvetica,Arial,sans-serif;
  font-size: 13px;
  color: #676a6c;
  overflow-x: hidden;
  margin: 24px 0;
  display: block;
  height: 2px;
  width: 100%;
  background-color: #dcdfe6;
}
.el-card p {
  margin-bottom: 15px;
}
ol li {
    list-style: decimal !important;
    margin-left: 24px;
}
a {
  color: #606266;
  text-decoration: none;
}
</style>
