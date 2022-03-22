<template>
  <el-container>
    <el-aside :width="sideWidth + 'px'" style="box-shadow: 2px 0 6px rgb(0 21 41 / 35%);overflow-x:hidden">
      <Aside :isCollapse="isCollapse" />
    </el-aside>
    <el-container>
      <!-- 头部 -->
      <el-header style="box-shadow: 0 1px 4px rgb(0 21 41 / 8%);">
      <!-- 侧边缩放 -->
        <Header :collapseBtn="collapseBtn" :collapseAside="collapseAside" :user="user"/>
      </el-header>
      <el-main>
        <!-- 主页面 -->
        <router-view @referUser="getUser" @referNickname="referNickname" :user="user" />
      </el-main>
      </el-container>
  </el-container>
</template>

<script>
import Aside from '@/components/Aside'
import Header from '@/components/Header'
export default {
  data () {
    return {
      // 判断是否折叠
      isCollapse: false,
      // 折叠图标的显示
      collapseBtn: 'el-icon-s-fold',
      // 侧边框的宽度
      sideWidth: 200,
      user: localStorage.getItem('userInfo') ? JSON.parse(localStorage.getItem('userInfo')) : {}
    }
  },
  methods: {
    // 侧边折叠，边框宽度和折叠图标发生变化
    collapseAside () {
      this.isCollapse = !this.isCollapse
      if (this.isCollapse) {
        this.sideWidth = 64
        this.collapseBtn = 'el-icon-s-unfold'
      } else {
        this.sideWidth = 200
        this.collapseBtn = 'el-icon-s-fold'
      }
    },
    getUser (res) {
      this.user.avatar = res
      localStorage.setItem('userInfo', JSON.stringify(this.user))
    },
    referNickname (nickname) {
      this.user.nickname = nickname
      localStorage.setItem('userInfo', JSON.stringify(this.user))
    }
  },
  components: {
    Aside,
    Header
  }
}
</script>

<style lang="less" scoped>
.el-menu-vertical-demo:not(.el-menu--collapse) {
  width: 200px;
  min-height: 400px;
}
.el-container {
  height: 100%;
  width: 100%;
  .el-header {
    width: 100%;
    background: #fff;
    height: 50px !important;
    line-height: 50px;
    position: relative;
    user-select: none;
  }
}

a{
  text-decoration: none;
  color: #fff;
  font-size: 18px
}
</style>
