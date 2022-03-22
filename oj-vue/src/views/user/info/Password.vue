<template>
  <div>
    <el-page-header style="color: #1890ff;user-select: none;" @back="goBack" content="更改密码">
    </el-page-header>
     <el-form ref="form"  :model="form" label-width="0px" @keyup.enter.native="login" :rules="rules">
       <el-form-item class='el-form-item-input' prop="oldPwd">
         <el-input v-model="form.oldPwd" label="旧密码" style="margin-top:10px" placeholder="请输入旧密码" show-password></el-input>
      </el-form-item>
      <el-form-item class='el-form-item-input' prop="newPwd">
        <el-input v-model="form.newPwd" label="新密码" style="margin-top:10px" placeholder="请输入新密码" show-password></el-input>
      </el-form-item>
      <el-form-item class='el-form-item-input' prop="newPwdAgain">
        <el-input v-model="form.newPwdAgain" label="确认密码"  style="margin-top:10px" placeholder="请再次输入新密码" show-password></el-input>
      </el-form-item>
    </el-form>
  </div>
</template>

<script>
export default {
  name: 'Password',
  data () {
    // 自定义校验两次密码输入一致
    var validatePass = (rule, value, callback) => {
      if (value !== this.form.newPwd) {
        callback(new Error('两次输入密码不一致!'))
      } else {
        callback()
      }
    }
    return {
      form: {
        oldPwd: '',
        newPwd: '',
        newPwdAgain: ''
      },
      // 配置验证规则
      rules: {
        oldPwd: [
          { required: true, message: '请输入旧密码', trigger: ['blur', 'change'] },
          { min: 3, max: 10, message: '长度在 3 到 15 个字符', trigger: ['blur', 'change'] }
        ],
        newPwd: [
          { required: true, message: '输入新密码', trigger: ['blur', 'change'] },
          { min: 3, max: 10, message: '长度在 3 到 15 个字符', trigger: ['blur', 'change'] }
        ],
        newPwdAgain: [
          { required: true, message: '请再次输入密码', trigger: ['blur', 'change'] },
          { validator: validatePass, trigger: ['blur', 'change'] }
        ]
      }
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
