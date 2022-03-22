<template>
<!-- 解决渲染问题 -->
  <div v-if="data.radio && data.judge && data.fill" style="overflow-x: hidden;" class="app-container">
    <el-row :gutter="20">
      <!-- 左侧题目列表 -->
      <el-col :span="5">
        <el-card class="box-card">
          <el-button round class="selected el-button-round ">1</el-button>
          <el-button class="success el-button-round " round>1</el-button>
          <el-button round class="el-button-round error">1</el-button>
          <p class="tip">
            <span>当前</span>
            <span>正确</span>
            <span>错误</span>
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
            <el-radio v-model="result.radio[currentSelect - 1]" label="A" disabled>A：{{content.planA}}</el-radio>
            <el-radio v-model="result.radio[currentSelect - 1]" label="B" disabled>B：{{content.planB}}</el-radio>
            <el-radio v-model="result.radio[currentSelect - 1]" label="C" disabled>C：{{content.planC}}</el-radio>
            <el-radio v-model="result.radio[currentSelect - 1]" label="D" disabled>D：{{content.planD}}</el-radio>
            <div style="margin-top:20px">
              <p>你的答案：{{result.radio[currentSelect - 1]}}</p>
              <p style="margin-top:10px">参考答案：{{data.radio[currentSelect - 1].sure.toUpperCase()}}</p>
            </div>
            <svg class="icon" aria-hidden="true">
                <use v-if="result.radio[currentSelect - 1] === data.radio[currentSelect - 1].sure.toUpperCase()" xlink:href="#icon-duihao"></use>
                <use v-else xlink:href="#icon-cuohao"></use>
            </svg>
          </div>
          <!-- 填空 -->
          <div v-else-if="currentSelect <= fillPos && currentSelect > radioPos">
            <el-input @paste.native.capture.prevent="paste" type="textarea" :autosize="{ minRows: 2, maxRows: 4}" placeholder="请输入内容" v-model="result.fill[currentSelect - radioPos - 1]" disabled></el-input>
            <div style="margin-top:20px">
              <p>你的答案：{{result.fill[currentSelect - radioPos - 1]}}</p>
              <p style="margin-top:10px">参考答案：{{data.fill[currentSelect - radioPos - 1].sure}}</p>
            </div>
            <svg class="icon" aria-hidden="true">
                <use v-if="result.fill[currentSelect - radioPos - 1] === data.fill[currentSelect - radioPos - 1].sure" xlink:href="#icon-duihao"></use>
                <use v-else xlink:href="#icon-cuohao"></use>
            </svg>
          </div>
          <!-- 判断 -->
          <div v-else-if="currentSelect <= judgePos && currentSelect > fillPos">
            <el-radio v-model="result.judge[currentSelect - fillPos - 1]" label="1" disabled>对</el-radio>
            <el-radio v-model="result.judge[currentSelect - fillPos - 1]" label="0" disabled>错</el-radio>
            <div style="margin-top:20px">
              <p>你的答案：{{result.judge[currentSelect - fillPos - 1] === '1' ? '对' : '错'}}</p>
              <p style="margin-top:10px">参考答案：{{data.judge[currentSelect - fillPos - 1].sure === '1' ? '对' : '错'}}</p>
            </div>
            <svg class="icon" aria-hidden="true">
                <use v-if="result.judge[currentSelect - fillPos - 1] === data.judge[currentSelect - fillPos - 1].sure" xlink:href="#icon-duihao"></use>
                <use v-else xlink:href="#icon-cuohao"></use>
            </svg>
          </div>
          <div v-else>
            模块暂不可用
          </div>
        </el-card>
        <div style="margin:20px 0 0 20px">
          <el-button type="primary" @click="preTitle" :disabled="shang">上一题</el-button>
          <el-button type="primary" style="margin-left: 60px" @click="nextTitle" :disabled="xia">下一题</el-button>
        </div>
      </el-col>
      <!-- 右侧考试的信息 -->
      <el-col :span="5">
        <div style="margin-top:10px">
          <p>
            考试科目：{{data.name}}
          </p>
          <p style="margin-top:10px">
            卷面总分：{{totalValue}} 分
          </p>
          <p style="margin-top:10px">
            开始时间：{{data.form.startTime}}
          </p>
          <p style="margin-top:10px">
            结束时间：{{data.form.endTime}}
          </p>
          <p style="margin-top:10px;color:red;font-size:20px">
            考试成绩：{{data.score}} 分
          </p>
        </div>
      </el-col>
    </el-row>
  </div>
</template>

<script>

import { Loading } from 'element-ui'
import '../../assets/judge/iconfont'
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
      // 动画
      loadingInstance: null,
      localName: '',
      totalValue: 0,
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
      const { code, data, msg } = await this.$axios.post(`exam/finished/${this.$route.params.id}/${this.$route.params.username}`)
      if (code !== '200') {
        this.loadingInstance.close()
        // 跳404
        this.$message.error(msg)
        this.$router.push({ name: '404' })
      } else {
        if (msg === 'absent') {
          this.$message.warning('本次考试缺考！成绩为0分')
        }
        let number = +1
        if (typeof (data.radio) === 'undefined') {
          data.radio = []
        } else {
          for (let i = 0; i < data.radio.length; i++) {
            data.radio[i].number = number++
            this.result.radio[i] = ''
          }
          this.result.radio[data.radio.length] = ''
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
          this.result.programme = new Array(data.programme.length + 1)
          if (number === 1 && data.programme.length > 0) {
            this.content = data.programme[0]
          }
          for (let i = 0; i < data.programme.length; i++) {
            data.programme[i].number = number++
            this.result.programme[i] = ''
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
        // 未处理简答题
        this.totalNumber = number - 1
        this.data = data
        this.result.radio = typeof data.radioSure !== 'undefined' ? data.radioSure : new Array(this.radioPos)
        this.result.fill = typeof data.fillSure !== 'undefined' ? data.fillSure : new Array(this.fillPos - this.radioPos)
        this.result.judge = typeof data.judgeSure !== 'undefined' ? data.judgeSure : new Array(this.judgePos - this.fillPos)
        this.getTotalValue()
        this.loadingInstance.close()
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
      }
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
          // 判断属于哪个题目
          if (this.result.radio.length >= item.number) {
            // 先判断是否答题
            if (typeof this.result.radio === 'undefined') {
              return 'error'
            } else {
              // 判断答案是否正确
              if (this.result.radio[item.number - 1] === this.data.radio[item.number - 1].sure.toUpperCase()) {
                return 'success'
              } else {
                return 'error'
              }
            }
          } else if (this.result.fill.length >= item.number - this.radioPos && item.number - this.radioPos > 0) {
            if (this.result.fill[item.number - this.radioPos - 1] !== null && this.result.fill[item.number - this.radioPos - 1] !== '' && typeof this.result.fill[item.number - this.radioPos - 1] !== 'undefined') {
              if (this.result.fill[item.number - this.radioPos - 1] === this.data.fill[item.number - this.radioPos - 1].sure) {
                return 'success'
              } else {
                return 'error'
              }
            } else {
              return 'error'
            }
          } else if (this.result.judge.length >= item.number - this.fillPos && item.number - this.fillPos > 0) {
            if (this.result.judge[item.number - this.fillPos - 1] !== null && this.result.judge[item.number - this.fillPos - 1] !== '' && typeof this.result.judge[item.number - this.fillPos - 1] !== 'undefined') {
              // 判断答案是否正确
              if (this.result.judge[item.number - this.fillPos - 1] === this.data.judge[item.number - this.fillPos - 1].sure) {
                return 'success'
              } else {
                return 'error'
              }
            } else {
              return 'error'
            }
          }
        }
      }
    },
    shang () {
      return this.currentSelect === 1
    },
    xia () {
      return this.currentSelect === this.totalNumber
    }
  }
}
</script>

<style lang="less" scoped>
.icon {
  width: 1em;
  height: 1em;
  vertical-align: -0.15em;
  fill: currentColor;
  overflow: hidden;
  position: absolute;
  bottom: 10%;
  right: 15%;
  font-size: 100px;
}
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
  background-color: rgb(40, 175, 40);
  color: #ffffff;
}
.el-card{
  width:290px;
  margin:10px 20px 0;
  position: relative;
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
.error {
  background-color: red;
  color: #ffffff;
}
</style>
