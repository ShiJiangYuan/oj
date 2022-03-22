<template>
<!-- 解决渲染问题 -->
  <div v-if="data.radio && data.judge && data.fill" style="overflow-x: hidden;" class="app-container">
    <el-row :gutter="20">
      <!-- 左侧题目列表 -->
      <el-col :span="5">
        <el-card class="box-card">
          <el-button round class="selected el-button-round ">1</el-button>
          <el-button class="success el-button-round " round>1</el-button>
          <el-button round class="el-button-round ">1</el-button>
          <p class="tip">
            <span>当前</span>
            <span>已答</span>
            <span>未答</span>
          </p>
        </el-card>
         <el-card v-if="data.radio.length !== 0" class="box-card">
          <div slot="header" class="clearfix">
            <span>选择题</span>
          </div>
          <el-button class="el-button-round " :class="selectColor(item)"  @click="headleRadio(item)" round v-for="item in data.radio" :key="item.id">{{item.number}}</el-button>
        </el-card>
        <!-- 填空 -->
         <el-card v-if="data.fill.length !== 0" class="box-card">
          <div slot="header" class="clearfix">
            <span>填空题</span>
          </div>
          <el-button class="el-button-round" :class="selectColor(item)" @click="headleFill(item)" round v-for="item in data.fill" :key="item.id">{{item.number}}</el-button>
        </el-card>
        <!-- 判断 -->
         <el-card v-if="data.judge.length !== 0" class="box-card">
          <div slot="header" class="clearfix">
            <span>判断题</span>
          </div>
          <el-button class="el-button-round" :class="selectColor(item)" @click="headleJudge(item)" round v-for="item in data.judge" :key="item.id">{{item.number}}</el-button>
        </el-card>
        <!-- 简答 -->
        <el-card v-if="data.programme.length !== 0" class="box-card">
          <div slot="header" class="clearfix">
            <span>简答题</span>
          </div>
          <el-button class="el-button-round" :class="selectColor(item)" @click="headlePorgramme(item)" round v-for="item in data.programme" :key="item.id">{{item.number}}</el-button>
        </el-card>
      </el-col>
      <!-- 中间答题区域 -->
      <el-col :span="14">
       <el-card class="box-card" style="width:90%">
          <div slot="header" class="clearfix">
            <span>{{currentSelect}}、</span>
            <span>{{content.title}}</span>
            <span v-text="source"></span>
          </div>
          <!-- 选择 -->
          <div v-if="currentSelect <= radioPos">
            <el-radio v-model="result.radio[currentSelect]" label="A">A：{{content.planA}}</el-radio>
            <el-radio v-model="result.radio[currentSelect]" label="B">B：{{content.planB}}</el-radio>
            <el-radio v-model="result.radio[currentSelect]" label="C">C：{{content.planC}}</el-radio>
            <el-radio v-model="result.radio[currentSelect]" label="D">D：{{content.planD}}</el-radio>
          </div>
          <!-- 填空 -->
          <div v-else-if="currentSelect <= fillPos && currentSelect > radioPos">
            <el-input @paste.native.capture.prevent="paste" type="textarea" :autosize="{ minRows: 2, maxRows: 4}" placeholder="请输入内容" v-model="result.fill[currentSelect - radioPos]"></el-input>
          </div>
          <!-- 判断 -->
          <div v-else-if="currentSelect <= judgePos && currentSelect > fillPos">
            <el-radio v-model="result.judge[currentSelect - fillPos]" label="1">对</el-radio>
            <el-radio v-model="result.judge[currentSelect - fillPos]" label="0">错</el-radio>
          </div>
          <div v-else>
            模块暂不可用
          </div>
        </el-card>
        <div style="margin:20px 0 0 20px">
          <el-button type="primary" @click="preTitle" :disabled="shang">上一题</el-button>
          <el-button type="primary" style="margin-left: 60px" @click="nextTitle">{{currentSelect !== totalNumber ? '下一题' : '提交试卷'}}</el-button>
          <el-button type="success" @click="saveTime" style="margin-left: 60px">暂存</el-button>
        </div>
      </el-col>
      <!-- 右侧考试的信息 -->
      <el-col :span="5">
        <div style="margin-top:10px">
          <p>
            考试科目：{{data.name}}
          </p>
          <p style="margin-top:10px">
            卷面成绩：{{totalValue}} 分
          </p>
          <p style="margin-top:10px">
            开始时间：{{data.form.startTime}}
          </p>
          <p style="margin-top:10px">
            结束时间：{{data.form.endTime}}
          </p>
          <div style="margin-top:20px">
            <p style="font:normal bold 20px/20px arial,sans-serif;color:red">距考试结束：</p>
            <p class="dao">
              <span>{{ timeCal.h >= 10 ? timeCal.h : '0' + timeCal.h  }}</span>
              <span> 时 </span>
              <span>{{ timeCal.m >= 10 ? timeCal.m : '0' + timeCal.m  }}</span>
              <span> 分 </span>
              <span>{{ timeCal.s >= 10 ? timeCal.s : '0' + timeCal.s  }}</span>
              <span> 秒 </span>
              <span>
              </span>
            </p>
          </div>
        </div>
      </el-col>
    </el-row>
  </div>
</template>

<script>

import { Loading } from 'element-ui'
import moment from 'moment'
export default {
  data () {
    return {
      data: [],
      user: localStorage.getItem('userInfo') ? JSON.parse(localStorage.getItem('userInfo')) : {},
      currentSelect: 1,
      result: {
        radio: [],
        fill: [],
        judge: [],
        programme: []
      },
      radioPos: 0,
      fillPos: 0,
      judgePos: 0,
      content: [],
      totalNumber: 0,
      source: '',
      // 动画
      loadingInstance: null,
      localName: '',
      // 倒计时
      timeCal: {
        h: '',
        m: '',
        s: ''
      },
      totalValue: '',
      timer: null
    }
  },
  async created () {
    this.loadingInstance = Loading.service({
      text: '加载中',
      target: '.app-container'
    })
    try {
      this.localName = 'exam' + this.$route.params.id
      const { code, data, msg } = await this.$axios.post(`exam/authenticate/${this.$route.params.id}/${this.user.role}/${this.user.username}`)
      if (code !== '200') {
        this.loadingInstance.close()
        // 跳404
        if (code !== '409') {
          this.$router.push({ name: '404' })
        } else {
          this.$message.warning(msg)
          this.$router.push('/contest')
        }
      } else {
        let number = +1
        if (typeof (data.radio) === 'undefined') {
          data.radio = []
        } else {
          this.result.radio = new Array(data.radio.length + 1)
          for (let i = 0; i < data.radio.length; i++) {
            data.radio[i].number = number++
            this.result.radio[i] = null
          }
          this.result.radio[data.radio.length] = null
          this.content = data.radio[0]
          this.source = '（分值：' + data.radioValue + '分）'
          this.radioPos = number - 1
        }
        if (typeof (data.fill) === 'undefined') {
          this.fillPos = this.radioPos
          data.fill = []
        } else {
          if (number === 1 && data.fill.length > 0) {
            this.content = data.fill[0]
          }
          for (let i = 0; i < data.fill.length; i++) {
            data.fill[i].number = number++
            this.result.fill[i] = ''
          }
          this.source = '（分值：' + data.fillValue + '分）'
          this.result.fill[data.fill.length] = ''
          this.fillPos = number - 1
        }
        if (typeof (data.judge) === 'undefined') {
          this.judgePos = this.fillPos
          data.judge = []
        } else {
          if (number === 1 && data.judge.length > 0) {
            this.content = data.judge[0]
          }
          for (let i = 0; i < data.judge.length; i++) {
            data.judge[i].number = number++
            this.result.judge[i] = ''
          }
          this.source = '（分值：' + data.judgeValue + '分）'
          this.result.judge[data.judge.length] = ''
          this.judgePos = number - 1
        }
        if (typeof (data.programme) === 'undefined') {
          this.programmePos = this.judgePos
          data.programme = []
        } else {
          if (number === 1 && data.programme.length > 0) {
            this.content = data.programme[0]
          }
          for (let i = 0; i < data.programme.length; i++) {
            data.programme[i].number = number++
            this.result.programmeId[i] = ''
          }
          this.source = '（分值：' + data.programmeValue[0] + '分）'
          this.result.programme[data.programme.length] = ''
          this.programmePos = number - 1
        }
        for (let j = 0; j < data.programme.length; j++) {
          for (let i = 0; i < data.programmeId.length; i++) {
            if (Number(data.programmeId[i]) === Number(data.programme[j].id)) {
              data.programme[j].value = data.programmeValue[i]
            }
          }
        }
        this.totalNumber = number - 1
        this.data = data
        this.loadingInstance.close()
        await this.$nextTick(() => {
          this.result.radio = JSON.parse(localStorage.getItem(this.localName + 'radio')) ? JSON.parse(localStorage.getItem(this.localName + 'radio')) : []
          this.result.judge = JSON.parse(localStorage.getItem(this.localName + 'judge')) ? JSON.parse(localStorage.getItem(this.localName + 'judge')) : []
          this.result.fill = JSON.parse(localStorage.getItem(this.localName + 'fill')) ? JSON.parse(localStorage.getItem(this.localName + 'fill')) : []
          this.result.programme = JSON.parse(localStorage.getItem(this.localName + 'programme')) ? JSON.parse(localStorage.getItem(this.localName + 'programme')) : []
          this.getTotalValue()
          const t = moment(this.data.form.endTime).diff(moment(new Date()).format('YYYY-MM-DD H:mm:ss'), 'second')
          this.timeCal.s = t % 60
          this.timeCal.m = ((t - this.timeCal.s) / 60) % 60
          this.timeCal.h = Math.ceil(t / 3600)
          this.timer = setInterval(() => {
            if (this.timeCal.s > 0) {
              this.timeCal.s--
            } else {
              if (this.timeCal.m > 0) {
                this.timeCal.m--
                this.timeCal.s = 59
              } else {
                if (this.timeCal.h > 0) {
                  this.timeCal.h--
                  this.timeCal.m = 59
                  this.timeCal.s = 59
                } else {
                  this.$message.success('考试结束时间已到，强制收卷！')
                  this.$router.push('/')
                  this.submitExam()
                }
              }
            }
          }, 1000)
        })
      }
    } catch (e) {
      this.loadingInstance.close()
    }
  },
  methods: {
    // 四个函数通过点击获取题目信息
    headleRadio (item) {
      this.currentSelect = item.number
      this.content = item
      this.source = '（分值：' + this.data.radioValue + '分）'
    },
    headleFill (item) {
      this.currentSelect = item.number
      this.content = item
      this.source = '（分值：' + this.data.fillValue + '分）'
    },
    headleJudge (item) {
      this.currentSelect = item.number
      this.content = item
      this.source = '（分值：' + this.data.judgeValue + '分）'
    },
    headleProgramme (item) {
      this.currentSelect = item.number
      this.content = item
      this.source = '（分值：' + this.data.programmeValue[this.currentSelect - this.judgePos - 1] + '分）'
    },
    // 更新题目序号
    updateTitle () {
      if (this.currentSelect <= this.radioPos) {
        this.content = this.data.radio[this.currentSelect - 1]
        this.source = '（分值：' + this.data.radioValue + '分）'
      } else if (this.currentSelect <= this.fillPos && this.currentSelect > this.radioPos) {
        this.content = this.data.fill[this.currentSelect - this.radioPos - 1]
        this.source = '（分值：' + this.data.fillValue + '分）'
      } else if (this.currentSelect <= this.judgePos && this.currentSelect > this.fillPos) {
        this.content = this.data.judge[this.currentSelect - this.fillPos - 1]
        this.source = '（分值：' + this.data.judgeValue + '分）'
      } else {
        this.content = this.data.programme[this.currentSelect - this.judgePos - 1]
        this.source = '（分值：' + this.data.programmeValue[this.currentSelect - this.judgePos - 1] + '分）'
      }
    },
    // 上一题
    preTitle () {
      if (this.currentSelect > 1) {
        this.currentSelect--
        this.updateTitle()
      }
    },
    // 下一题/提交试卷
    async nextTitle () {
      if (this.currentSelect < this.totalNumber) {
        this.currentSelect++
        this.updateTitle()
      } else {
        // 判断是否答完题
        var number = 0
        for (let i = 1; i < this.result.radio.length; i++) {
          if (this.result.radio[i] !== null && this.result.radio[i] !== '' && typeof this.result.radio[i] !== 'undefined') {
            number++
          }
        }
        if (number !== this.radioPos) {
          this.$notify.warning({
            title: '提示',
            message: '选择题未答完，请勿交卷！'
          })
          return
        }
        number = 0
        for (let i = 1; i < this.result.fill.length; i++) {
          if (this.result.fill[i] !== null && this.result.fill[i] !== '' && typeof this.result.fill[i] !== 'undefined') {
            number++
          }
        }
        if (number !== this.fillPos - this.radioPos) {
          this.$notify.warning({
            title: '提示',
            message: '填空题未答完，请勿交卷！'
          })
          return
        }
        number = 0
        for (let i = 1; i < this.result.judge.length; i++) {
          if (this.result.judge[i] !== null && this.result.judge[i] !== '' && typeof this.result.judge[i] !== 'undefined') {
            number++
          }
        }
        if (number !== this.judgePos - this.fillPos) {
          this.$notify.warning({
            title: '提示',
            message: '判断题未答完，请勿交卷！'
          })
          return
        }
        number = 0
        for (let i = 1; i < this.result.programme.length; i++) {
          if (this.result.programme[i] !== null && this.result.programme[i] !== '' && typeof this.result.programme[i] !== 'undefined') {
            number++
          }
        }
        if (number !== this.totalNumber - this.judgePos) {
          this.$notify.warning({
            title: '提示',
            message: '简答题未答完，请勿交卷！'
          })
          return
        }
        this.submitExam()
      }
    },
    // 提交试卷
    async submitExam () {
      var score = []
      var t = []
      t[0] = this.user.username
      var ids = []
      ids[0] = this.$route.params.id
      score[0] = +0
      for (let i = 0; i < this.data.radio.length; i++) {
        if (this.result.radio[i + 1] === '' || this.result.radio[i + 1] === null || typeof this.result.radio[i + 1] === 'undefined') {
          continue
        }
        if (this.data.radio[i].sure.toUpperCase() === this.result.radio[i + 1].toUpperCase()) {
          score[0] += Number(this.data.radioValue)
        }
      }
      for (let i = 0; i < this.data.judge.length; i++) {
        if (this.result.judge[i + 1] === '' || this.result.radio[i + 1] === null || typeof this.result.judge[i + 1] === 'undefined') {
          continue
        }
        if (this.data.judge[i].sure === this.result.judge[i + 1]) {
          score[0] += Number(this.data.judgeValue)
        }
      }
      for (let i = 0; i < this.data.fill.length; i++) {
        if (this.data.fill[i].sure === this.result.fill[i + 1]) {
          score[0] += Number(this.data.fillValue)
        }
      }
      // 预防没有答完题，对脏数据进行处理
      if (this.result.radio.length - 1 < this.radioPos) {
        this.result.radio[this.radioPos] = null
      }
      if (this.result.fill.length - 1 < this.fillPos - this.radioPos) {
        this.result.fill[this.fillPos - this.radioPos] = null
      }
      if (this.result.judge.length - 1 < this.judgePos - this.fillPos) {
        this.result.judge[this.judgePos - this.fillPos] = null
      }
      // 这里没有处理简答题
      const { msg, code } = await this.$axios.post('contest/submit', {
        radio: this.result.radio,
        judge: this.result.judge,
        fill: this.result.fill,
        programme: this.result.programme,
        username: t,
        score: score,
        examId: ids
      })
      if (code === '200') {
        this.$message.success('成功交卷')
        this.$router.push('/')
        clearInterval(this.timer)
        this.timer = null
        localStorage.removeItem(this.localName + 'radio')
        localStorage.removeItem(this.localName + 'fill')
        localStorage.removeItem(this.localName + 'judge')
        localStorage.removeItem(this.localName + 'programme')
      } else {
        this.$$message.error(msg)
      }
    },
    // 暂存
    saveTime () {
      localStorage.setItem(this.localName + 'radio', JSON.stringify(this.result.radio))
      localStorage.setItem(this.localName + 'programme', JSON.stringify(this.result.programme))
      localStorage.setItem(this.localName + 'judge', JSON.stringify(this.result.judge))
      localStorage.setItem(this.localName + 'fill', JSON.stringify(this.result.fill))
      this.$message.success('暂存成功')
    },
    // 阻止粘贴事件
    paste (e) {
      e.preventDefault()
      return ''
    },
    // 获取各个模块的分数
    getTotalValue () {
      const radio = this.data.radio.length * this.data.radioValue
      const judge = this.data.judge.length * this.data.judgeValue
      const fill = this.data.fill.length * this.data.fillValue
      var programme = +0
      this.data.programme.forEach(item => {
        programme += item.value
      })
      this.totalValue = radio + judge + fill + programme
    }
  },
  computed: {
    selectColor () {
      return function (item) {
        if (this.currentSelect === item.number) {
          return 'selected'
        } else {
          if (this.result.radio.length - 1 >= item.number) {
            if (this.result.radio[item.number] !== null && this.result.radio[item.number] !== '' && typeof this.result.radio[item.number] !== 'undefined') {
              return 'success'
            }
          } else if (this.result.fill.length - 1 >= item.number - this.radioPos && item.number - this.radioPos > 0) {
            if (this.result.fill[item.number - this.radioPos] !== null && this.result.fill[item.number - this.radioPos] !== '' && typeof this.result.fill[item.number - this.radioPos] !== 'undefined') {
              return 'success'
            }
          } else if (this.result.judge.length - 1 >= item.number - this.fillPos && item.number - this.fillPos > 0) {
            if (this.result.judge[item.number - this.fillPos] !== null && this.result.judge[item.number - this.fillPos] !== '' && typeof this.result.judge[item.number - this.fillPos] !== 'undefined') {
              return 'success'
            }
          }
        }
      }
    },
    shang () {
      return this.currentSelect === 1
    }
  }
}
</script>

<style lang="less" scoped>
.el-button-round {
  margin: 5px 7px!important;
  height: 30px;
  width: 30px;
}
.el-button.is-round {
  border-radius: 15px;
  padding: 0;
}
/deep/ .el-card__body {
  text-align: left;
  padding: 10px !important;
}
/deep/ .el-card__header {
  padding: 10px 20px;
}
.selected {
  color: #fff;
  background-color: #409EFF;
}
.success {
  background-color: skyblue;
}
.el-card{
  width:290px;
  margin:10px 20px 0
}
.tip span {
  font-size: 14px;
  margin: 8px;
}
.el-radio {
  margin-top: 10px;
  display: block;
}
.dao {
  margin-top: 20px;
}
.dao span {
  font-size: 28px;
}
</style>
