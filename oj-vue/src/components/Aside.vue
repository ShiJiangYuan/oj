<template>
  <el-menu :default-active="current" text-color="#fff" :collapse-transition="false" :collapse="isCollapse" style="min-height:100%;overflow-x:hidden" background-color="rgb(48,65,86)" class="el-menu-vertical-demo" router>
    <div style="width:50px;height:50px">
      <a style="width:200px;height:50px;display:block;line-height:50px" href="/">
        <img style="width:32px;height:32px;display:inline-block;box-sizing: border-box;vertical-align: middle;margin: 0 15px" src="../assets/logo.png">
        <span style="display:inline-block;margin-left:0px;height:50px;line-height:50px;font-weight:600" v-show="!isCollapse">在线评测系统</span>
      </a>
    </div>
    <el-menu-item index="/">
        <i class="el-icon-s-home"></i>
        <span slot="title">首页</span>
    </el-menu-item>
    <el-submenu v-for="menu in listMenu" :key="menu.id" :index="menu.path">
      <template slot="title">
        <i :class="menu.icon"></i>
        <span slot="title">{{ menu.name }}</span>
      </template>
      <el-menu-item v-for="item in menu.children" :key="item.id" :index="item.path">
          <i :class="item.icon"></i>
          <span slot="title">{{ item.name }}</span>
      </el-menu-item>
    </el-submenu>
  </el-menu>
</template>

<script>
export default {
  name: 'Aside',
  props: {
    isCollapse: Boolean
  },
  data () {
    return {
      listMenu: []
    }
  },
  async created () {
    try {
      const { username } = localStorage.getItem('userInfo') ? JSON.parse(localStorage.getItem('userInfo')) : {}
      const { msg, code, data } = await this.$axios.get(`role/getMenu/${username}`)
      if (code === '200') {
        this.listMenu = data
      } else {
        this.$message.error(msg)
      }
    } catch (e) {}
  },
  computed: {
    current: function () {
      const path = this.$route.path.slice(1)
      return path === 'index' ? '/' : path
    }
  }
}
</script>

<style lang="less" scoped>
a{
  text-decoration: none;
  color: #fff;
  font-size: 18px
}
.el-menu-vertical-demo:not(.el-menu--collapse) {
  width: 200px;
  min-height: 400px;
}
.el-menu-item:hover {
  background-color: #263445 !important;
}
/deep/ .el-submenu__title:hover {
  background-color: #263445!important;
}
.el-menu-item:focus {
  background-color: #263445!important;
}
</style>
