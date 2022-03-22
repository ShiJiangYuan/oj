<template>
  <div>
    <el-page-header style="color: #1890ff;user-select: none;" @back="goBack" content="更改邮箱">
    </el-page-header>
    <el-input v-model="newEmail" style="margin-top:20px" placeholder="请输入邮箱"></el-input>
  </div>
</template>

<script>
export default {
  name: 'Email',
  props: {
    email: String
  },
  data () {
    return {
      newEmail: this.email
    }
  },
  methods: {
    goBack () {
      this.$emit('childByValue', '1')
    }
  }
}
</script>

<style>

</style>
