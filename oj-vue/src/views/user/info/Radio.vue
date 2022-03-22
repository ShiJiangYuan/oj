<template>
  <div>
    <el-button type="success" @click="emailUpdate">更改邮箱</el-button>
    <el-button type="primary" @click="nicknameUpdate">更改昵称</el-button>
    <el-button type="warning" @click="passwordUpdate">更改密码</el-button>
  </div>
</template>

<script>
export default {
  name: 'Radio',
  data () {
    return {
    }
  },
  methods: {
    emailUpdate () {
      this.$emit('childByValue', '2')
    },
    nicknameUpdate () {
      this.$emit('childByValue', '3')
    },
    passwordUpdate () {
      this.$emit('childByValue', '4')
    }
  }
}
</script>

<style lang="less" scoped>
/* 重写button样式 */
.el-button--success {
    color: #fff;
    background-color: #13ce66;
    border-color: #13ce66;
}
.el-button--success:focus, .el-button--success:hover {
    background: #42d885!important;
    border-color: #42d885!important;
    color: #fff;
}
.el-button--primary {
    color: #fff;
    background-color: #1890ff;
    border-color: #1890ff;
}
.el-button--primary:focus, .el-button--primary:hover {
    background: #46a6ff!important;
    border-color: #46a6ff!important;
    color: #fff;
}
.el-button--warning {
    color: #fff;
    background-color: #ffba00;
    border-color: #ffba00;
}
.el-button--warning:focus, .el-button--warning:hover {
    background: #ffc833!important;
    border-color: #ffc833!important;
    color: #fff;
}
</style>
