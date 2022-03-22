<template>
  <div>
    <el-card class="box-card" style="min-width:25%;float:left;margin-right:30px; box-sizing: border-box;">
      <div style="text-align: center;margin-top:30px;">
        <el-upload class="avatar-uploader"  :action="avatarUrl" :show-file-list="false" :on-success="handleAvatarSuccess" :before-upload="beforeAvatarUpload">
          <img v-if="imageUrl" :src="imageUrl" class="avatar">
          <i v-else class="el-icon-plus avatar-uploader-icon"></i>
        </el-upload>
      </div>
      <div style="margin:20px 0">
        <h4 style="text-align: center;color:#777;">{{ userInfo.role }}</h4>
      </div>
    </el-card>
    <el-card class="box-card" style="min-width:70%;float:left;box-sizing: border-box;">
      <el-tabs v-model="activeName">
        <el-tab-pane label="基本信息" name="first" style="max-width:500px;" >
          <el-descriptions class="margin-top" title="" :column="1" size="medium">
            <el-descriptions-item :labelStyle="labelStyle" label="用户名">{{userInfo.username}}</el-descriptions-item>
            <el-descriptions-item :labelStyle="labelStyle" label="昵称">{{userInfo.nickname}}</el-descriptions-item>
            <el-descriptions-item :labelStyle="labelStyle" label="邮箱">{{userInfo.email}}</el-descriptions-item>
            <el-descriptions-item :labelStyle="labelStyle" label="创建时间">{{createTime}}</el-descriptions-item>
          </el-descriptions>
        </el-tab-pane>
        <el-tab-pane label="账号信息" name="second">
          <Radio v-show="modifyButton === '1'" @childByValue="childByValue"></Radio>
          <Email ref="emailRef" v-show="modifyButton === '2'" :email="userInfo.email" @childByValue="childByValue"></Email>
          <Nickname ref="nicknameRef" :nickname="userInfo.nickname" v-show="modifyButton === '3'" @childByValue="childByValue"></Nickname>
          <Password  ref="passwordRef" v-show="modifyButton === '4'" @childByValue="childByValue"></Password>
          <el-button :loading="loading" style="margin-top:20px" type="primary" v-show="modifyButton !== '1'" @click="modify">修改</el-button>
        </el-tab-pane>
      </el-tabs>
    </el-card>
  </div>
</template>

<script>
import Radio from './info/Radio'
import Nickname from './info/Nickname'
import Password from './info/Password'
import Email from './info/Email'
import moment from 'moment'
export default {
  data () {
    return {
      activeName: 'first',
      userInfo: localStorage.getItem('userInfo') ? JSON.parse(localStorage.getItem('userInfo')) : {},
      labelStyle: {
        width: '80px'
      },
      modifyButton: '1',
      loading: false,
      avatarUrl: '',
      imageUrl: '',
      createTime: moment(JSON.parse(localStorage.getItem('userInfo')).createTime).format('YYYY-MM-DD H:mm:ss')
    }
  },
  methods: {
    childByValue: function (chilrenValue) {
      if (chilrenValue === '4') {
        this.$refs.passwordRef.$refs.form.clearValidate()
      } else if (chilrenValue === '3') {
        this.$refs.nicknameRef.newNickname = this.userInfo.nickname
      } else if (chilrenValue === '2') {
        this.$refs.emailRef.newEmail = this.userInfo.email
      }
      this.modifyButton = chilrenValue
    },
    async modify () {
      if (this.modifyButton === '2') {
        var emailTest = /^[a-zA-Z0-9]+([-_.][a-zA-Z0-9]+)*@[a-zA-Z0-9]+([-_.][a-zA-Z0-9]+)*\.[a-z]{2,}$/
        if (emailTest.test(this.$refs.emailRef.newEmail)) {
          this.loading = true
          const { code, msg } = await this.$axios.patch(`user/update/email/${this.userInfo.username}/${this.$refs.emailRef.newEmail}`)
          if (code === '200') {
            this.$notify({
              title: '修改成功',
              message: '邮箱已经修改成功',
              offset: 40,
              type: 'success'
            })
            this.userInfo.email = this.$refs.emailRef.newEmail
            localStorage.setItem('userInfo', JSON.stringify(this.userInfo))
            this.modifyButton = '1'
          } else {
            this.$message.error(msg)
          }
        } else {
          this.$message.error('请输入合法的邮箱')
        }
        this.loading = false
      } else if (this.modifyButton === '3') {
        const nickname = this.$refs.nicknameRef.newNickname
        if (nickname === '') {
          this.$message.error('请输入名称')
        } else {
          this.loading = true
          const { code, msg } = await this.$axios.patch(`user/update/nickname/${this.userInfo.username}/${nickname}`)
          if (code === '200') {
            this.$notify({
              title: '修改成功',
              message: '昵称已经修改成功',
              offset: 40,
              type: 'success'
            })
            this.userInfo.nickname = nickname
            this.$emit('referNickname', nickname)
            this.modifyButton = '1'
          } else {
            this.$message.error(msg)
          }
        }
        this.loading = false
      } else if (this.modifyButton === '4') {
        await this.$refs.passwordRef.$refs.form.validate()
        if (this.$refs.passwordRef.form.oldPwd === this.userInfo.password) {
          this.loading = true
          const { code, msg } = await this.$axios.patch(`user/update/password/${this.userInfo.username}/${this.$refs.passwordRef.form.newPwd}`)
          if (code === '200') {
            this.$notify({
              title: '修改成功',
              message: '密码已经修改成功，请重新登录',
              type: 'success',
              offset: 40
            })
            // 这里是因为后端是使用密码进行加密验证的token，如果不进行跳转的话，token验证失败，用户不会有任何权限
            localStorage.removeItem('userInfo')
            this.$router.push({ name: '登录' })
          } else {
            this.$message.error(msg)
          }
        } else {
          this.$message.error('旧密码错误')
        }
        this.loading = false
      }
    },
    handleAvatarSuccess (res) {
      this.imageUrl = res
      this.$emit('referUser', res)
    },
    beforeAvatarUpload (file) {
      const isJPG = file.type === 'image/jpeg'
      const isLt2M = file.size / 1024 / 1024 < 2

      if (!isJPG) {
        this.$message.error('上传头像图片只能是 JPG 格式!')
      }
      if (!isLt2M) {
        this.$message.error('上传头像图片大小不能超过 2MB!')
      }
      return isJPG && isLt2M
    }
  },
  components: {
    Radio,
    Nickname,
    Password,
    Email
  },
  mounted () {
    this.avatarUrl = this.$axios.defaults.baseURL + '/user/upload/' + this.userInfo.username
    this.imageUrl = this.userInfo.avatar
  }
}
</script>

<style lang="less" scoped>
/* 覆盖tab的默认样式 */
.el-tabs__item:focus.is-active.is-focus:not(:active){
  box-shadow: none!important;
  border-radius: 0px!important;
}
/* 重写样式 */
.el-descriptions-item__label.has-colon::after {
  content: ''!important;
}
.avatar-uploader .el-upload {
  border: 1px dashed #d9d9d9;
  border-radius: 6px;
  cursor: pointer;
  position: relative;
  overflow: hidden;
}
.avatar-uploader .el-upload:hover {
  border-color: #409EFF;
}
.avatar-uploader-icon {
  font-size: 28px;
  color: #8c939d;
  width: 178px;
  height: 178px;
  line-height: 178px;
  text-align: center;
}
.avatar {
  width: 178px;
  height: 178px;
  display: block;
}
</style>
