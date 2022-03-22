<template>
  <div class="login">
    <el-form :model="form" label-width="0px" @keyup.enter.native="login">
      <h3>欢迎使用 Online Judge</h3>
      <el-form-item class='el-form-item-input'>
        <i class="el-icon-user-solid"></i>
        <el-input v-model="form.username" placeholder="用户名"></el-input>
      </el-form-item>
      <el-form-item class='el-form-item-input'>
        <i class="iconfont icon-icon"></i>
        <el-input ref="password" v-model="form.password" :type="inputType" placeholder="密码"></el-input>
        <i id="eye" @click="passwordView" class="iconfont" :class="eyeClass"></i>
      </el-form-item>
      <el-form-item>
        <el-button :loading="loadingState" type="primary" @click="login" >登录</el-button>
      </el-form-item>
      <div id="form-bottom">
        <span class="info-tab">
          <router-link to="/forgot" >忘记密码</router-link>
        </span>
        <span style="color:gray">&nbsp; | &nbsp;</span>
        <span class="info-tab">
          <router-link to="/register" >注册</router-link>
        </span>
        <span style="color:gray">&nbsp; | &nbsp;</span>
        <span class="info-tab">关于</span>
      </div>
    </el-form>
    <div id="bottom">
      <p ref="copyright"></p>
      <a href="https://beian.miit.gov.cn/" target="_blank">蒙ICP备2020004885号-1</a>
    </div>
  </div>
</template>

<script>
export default {
  data () {
    return {
      // 用户名和密码
      form: {
        username: '',
        password: ''
      },
      // 判断是否显示密码，true表示不显示密码（默认）
      eye: true,
      loadingState: false
    }
  },
  methods: {
    async login () {
      const form = this.form
      if (form.username === '') {
        this.$message.error({ message: '用户名不能为空', duration: 2000 })
      } else if (form.password === '') {
        this.$message.error({ message: '密码不能为空', duration: 2000 })
      } else {
        this.loadingState = true
        try {
          const { code, data, msg } = await this.$axios.post('user/login', {
            data: {
              username: form.username,
              password: form.password
            }
          })
          if (code === '200') {
            localStorage.setItem('userInfo', JSON.stringify(data))
            this.$message.success({ message: '登录成功', duration: 2000 })
            this.$router.push({ name: '首页' })
          } else {
            this.$message.error({ message: msg, duration: 2000 })
            this.loadingState = false
          }
        } catch (e) {
          this.$message.error({ message: '请求超时', duration: 2000 })
          this.loadingState = false
        }
      }
    },
    passwordView () {
      this.eye = !this.eye
    }
  },
  mounted () {
    // 自动更新版权的时间
    this.$refs.copyright.innerText = `COPYRIGHT © 2022-${(new Date()).getFullYear()} ShiJiangYuan. ALL RIGHTS RESERVED.`
  },
  computed: {
    // 密码框判断用闭眼还是睁眼的字符图标
    eyeClass: function () {
      return { 'icon-icon-test1': this.eye, 'icon-icon-test': !this.eye }
    },
    // 判断密码框是否显示密码
    inputType: function () {
      if (this.eye === true) {
        return 'password'
      }
      return 'text'
    }
  },
  created () {
    // 目前app端不能访问
    if ((navigator.userAgent.match(/ (phone| padlpodI iPhone liPodI iosI iPad |Android|IMobile|BlackBerry|IEMobile|IMQQBrowse|JUC|Fennec|wOSBrowser|BrowserNG|WebosISymbian|Windows Phone)/i))) {
      window.location.href = '/500'
    }
  }
}
</script>

<style lang="less">
#form-bottom {
  span {
    color: #fff;
    cursor: pointer;
  }
  .info-tab:hover {
    color: #00bfff;
  }
}
.el-button i {
  padding: 0px !important;
}
// 解决自动填充央视改变问题
.login input:-webkit-autofill {
  box-shadow: 0 0 0px 1000px #c7c6c6 inset !important;
  -webkit-text-fill-color: #ededed !important;
  -webkit-box-shadow: 0 0 0px 1000px transparent inset !important;
  background-color: transparent;
  background-image: none;
  transition: background-color 50000s ease-in-out 0s;
}
.login {
  background-color: #2d3a4b;
  min-height: 100%;
  width: 100%;
  overflow: hidden;
  position: relative;
  .el-form {
    width: 520px;
    position: relative;
    padding: 160px 35px 0px;
    margin: 0 auto;
    max-width: 100%;
    overflow: hidden;
    box-sizing:border-box;
    margin-bottom: 100px;
    font-size: 14px;
  }
  .el-form-item-input {
    width: 450px;
    height: 50px;
    background-color:rgba(0,0,0,.1);
    border: 1px solid hsla(0,0%,100%,.1);
    max-width: 100%;
    box-sizing:border-box;
    position: relative;
  }
  .el-input {
    caret-color: #fff;
    height: 47px;
    line-height: 36px;
    width: 75%;
  }
  .el-input__inner {
    height: 50px;
    width: 100%;
    background-color: transparent !important;
    color: #fff;
    border: 0;
    height: 47px;
    padding: 12px 5px 12px 15px;
    display: inline-block;
    line-height: 48px;
  }
  i {
    padding: 0px 5px 0px 15px;
    color: #889aa4;
    vertical-align: middle;
    width: 20px;
    font-size: 18px;
  }
  h3 {
    width: 450px;
    font-size: 26px;
    color: #eee;
    margin: 0 auto 40px auto;
    text-align: center;
    font-weight: 700;
    max-width: 100%;
  }
  .icon-icon-test1,
  .icon-icon-test {
    display: inline-block;
    padding: 0;
  }
  a {
    cursor: pointer;
    text-decoration: none;
    color: inherit;
    text-align: center;
    display: inline-block;
    margin: 5px 0;
  }
  #bottom {
    width: 520px;
    color: white;
    text-align: center;
    font-size: 12px;
    max-width: 100%;
    position: absolute;
    left: 50%;
    transform: translateX(-50%);
    bottom: 20px;
  }
  #eye {
    position: absolute;
    right: 10px;
    top: 4px;
  }
  p {
    margin: 5px 0;
  }
  .el-button {
    width: 100%;
  }
}
</style>
