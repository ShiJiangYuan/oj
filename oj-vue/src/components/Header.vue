<template>
  <div style="display:flex;justify-content: space-between;height:50px;box-sizing: border-box;">
    <el-radio-group style="margin-bottom:20px;height:50px;line-height:50px">
      <i class="menu-flod-show" :class="collapseBtn" style="cursor: pointer;display:inline-block" @click="collapseAside"></i>
      <!-- 面包屑 -->
      <el-breadcrumb style="height:50px;font-size:14px;line-height:50px;display:inline-block;overflow:hidden;float:left" separator="/">
        <el-breadcrumb-item :to="{ path: '/' }">首页</el-breadcrumb-item>
        <el-breadcrumb-item>{{ currentPathName }}</el-breadcrumb-item>
      </el-breadcrumb>
    </el-radio-group>
    <!-- 用户名信息 -->
    <el-dropdown style="height:50px;position: relative;">
      <span>
        <el-avatar style="margin:5px 5px 0px 0px;cursor: pointer;" :src="user.avatar">
        </el-avatar>
        <div style="float:right; cursor: pointer;margin-right:10px">{{ user.nickname }}</div>
      </span>
      <el-dropdown-menu slot="dropdown" placement="bottom-start">
        <el-dropdown-item @click.native="person">个人中心</el-dropdown-item>
        <el-dropdown-item @click.native="logout" >退出oj</el-dropdown-item>
      </el-dropdown-menu>
    </el-dropdown>
</div>
</template>

<script>
export default {
  name: 'Header',
  props: {
    collapseBtn: String,
    collapseAside: Function,
    user: Object
  },
  data () {
    return {
    }
  },
  methods: {
    // 退出登录
    logout () {
      localStorage.removeItem('userInfo')
      this.$router.push({ name: '登录' })
    },
    person () {
      this.$router.push('/person')
    }
  },
  computed: {
    currentPathName () {
      return this.$store.state.currentPathName
    }
  }
}
</script>

<style lang="less" scoped>
.menu-flod-show {
  display: block;
  float: left;
  width: 50px;
  height: 50px;
  line-height: 50px;
  font-size: 23px;
}
</style>
