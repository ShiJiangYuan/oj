<template>
  <div id="show">
  </div>
</template>

<script>
export default {
  mounted () {
    this.getActivation()
  },
  methods: {
    async getActivation () {
      const activationAccount = this.$route.query.confirmCode
      const { msg, code } = await this.$axios.get(`user/activation/${activationAccount}`)
      if (code === '200') {
        this.$message.success('激活成功', 2000)
        this.$router.push('/login')
      } else {
        this.$message.error(msg, 2000)
        this.$router.push('/register')
      }
    }
  }
}
</script>

<style lang="less">
#show {
  left: 50%;
  transform: translate(-50%,-50%);
  top: 50%;
  position: absolute;
}
</style>
