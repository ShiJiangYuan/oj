<template>
  <div>
    <el-page-header style="color: #1890ff;user-select: none;" @back="goBack" content="更改昵称">
    </el-page-header>
    <el-input v-model="newNickname" style="margin-top:20px" placeholder="请输入昵称"  maxlength="20"></el-input>
  </div>
</template>

<script>
export default {
  name: 'Nickname',
  props: {
    nickname: String
  },
  data () {
    return {
      newNickname: this.nickname
    }
  },
  methods: {
    goBack () {
      this.$emit('childByValue', '1')
      this.$emit('nicknameUpdate', this.nickname)
    }
  }
}
</script>

<style scoped>
</style>
