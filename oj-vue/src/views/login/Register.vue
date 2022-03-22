<template>
  <div class="login">
    <el-form ref="form" :model="form" label-width="0px" @keyup.enter.native="login" :rules="rules">
      <h3>欢迎使用 Online Judge</h3>
      <el-form-item class='el-form-item-input' prop="username">
        <i class="el-icon-user-solid"></i>
        <el-input v-model="form.username"  placeholder="用户名"></el-input>
      </el-form-item>
      <el-form-item class='el-form-item-input' prop="email">
        <i class="el-icon-message"></i>
        <el-input ref="password" v-model="form.email" placeholder="邮箱"></el-input>
      </el-form-item>
      <el-form-item class='el-form-item-input' prop="password">
        <i class="iconfont icon-icon"></i>
        <el-input ref="password" v-model="form.password" type="password"  placeholder="密码"></el-input>
      </el-form-item>
      <el-form-item class='el-form-item-input' prop="passwordAgain">
        <i class="iconfont icon-icon"></i>
        <el-input ref="password" v-model="form.passwordAgain" type="password" placeholder="确认密码"></el-input>
      </el-form-item>
      <el-form-item>
        <el-button :loading="loadingState" type="primary" @click="register" >注册</el-button>
      </el-form-item>
      <div id="form-bottom">
        <span class="info-tab">
          <router-link to="/forgot" >忘记密码</router-link>
        </span>
        <span style="color:gray">&nbsp; | &nbsp;</span>
        <span class="info-tab">
          <router-link to="/login" >登录</router-link>
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
    var validQC = (rule, value, callback) => {
      if (value) {
        if (/[\u4E00-\u9FA5]/g.test(value)) {
          callback(new Error('不能输入汉字'))
        } else {
        // 验证通过
          callback()
        }
        callback()
      }
    }
    // 自定义校验两次密码输入一致
    var validatePass = (rule, value, callback) => {
      if (value !== this.form.password) {
        callback(new Error('两次输入密码不一致!'))
      } else {
        callback()
      }
    }
    return {
      // 用户名和密码
      form: {
        username: '',
        password: '',
        email: '',
        passwordAgain: ''
      },
      // 配置验证规则
      rules: {
        username: [
          { required: true, message: '请输入用户名', trigger: ['blur', 'change'] },
          { min: 3, max: 10, message: '长度在 3 到 10 个字符', trigger: ['blur', 'change'] },
          { validator: validQC, trigger: ['blur', 'change'] }
        ],
        password: [
          { required: true, message: '请输入密码', trigger: ['blur', 'change'] },
          { min: 3, max: 10, message: '长度在 3 到 15 个字符', trigger: ['blur', 'change'] }
        ],
        passwordAgain: [
          { required: true, message: '请再次输入密码', trigger: ['blur', 'change'] },
          { validator: validatePass, trigger: ['blur', 'change'] }
        ],
        email: [
          { required: true, message: '请输入邮箱地址', trigger: ['blur', 'change'] },
          { type: 'email', message: '请输入正确的邮箱地址', trigger: ['blur', 'change'] }
        ]
      },
      loadingState: false
    }
  },
  methods: {
    async register () {
      await this.$refs.form.validate()
      const form = this.form
      if (form.username === '') {
        this.$message.error({ message: '用户名不能为空', duration: 2000 })
      } else if (form.password === '') {
        this.$message.error({ message: '密码不能为空', duration: 2000 })
      } else {
        this.loadingState = true
        try {
          const { code, msg } = await this.$axios.post('user/register', this.form)
          if (code === '200') {
            this.$message.success({ message: '注册成功，请前往邮箱进行账号激活', duration: 2000 })
            this.$router.push({ name: '登录' })
          } else {
            this.$message.error({ message: msg, duration: 2000 })
          }
          this.loadingState = false
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
    background-color: transparent !important;
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
