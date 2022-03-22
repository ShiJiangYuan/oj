<template>
  <div class="app-analyse">
    <el-row :gutter="20">
      <el-col :span="6">
        <el-card class="box-card rank">
          <div slot="header" class="clearfix">
            <span>成绩排名（总分：{{summary.totalValue}}）</span>
          </div>
          <el-table :data="tableData" border style="width: 100%" max-height=650 :default-sort = "{prop: 'score', order: 'descending'}" v-loading="loading">
            <el-table-column prop="number" width="50" label="排名" align="center">
            </el-table-column>
            <el-table-column prop="username" label="用户名" align="center" width="109" :show-overflow-tooltip='true'>
            </el-table-column>
            <el-table-column prop="score" label="分数" width="74" align="center" :sortable="true">
            </el-table-column>
             <el-table-column center label="操作" width="85" align="center">
               <template slot-scope="scope">
                <el-button @click.native.prevent="detialInfo(scope.row.username)" type="text" size="small">
                  查看详情
                </el-button>
              </template>
            </el-table-column>
          </el-table>
        </el-card>
      </el-col>
      <el-col :span="12">
        <div id="score"></div>
        <div id="pie"></div>
      </el-col>
      <el-col :span="6">
        <div id="radio"></div>
        <div id="fill"></div>
        <div id="judge"></div>
      </el-col>
    </el-row>
    <el-drawer :title="drawerTitle" :visible.sync="drawer" direction="ltr" size=320>
      <div v-if="drawerTitle === '选择题'" style="width:320px">
        <el-button class="el-button-round" :class="selectColor(item)"  @click="radioChange(item.number)" round v-for="item in summary.radio" :key="item.id">{{item.number}}</el-button>
        <div class="title">
          {{summary.radio[radioPos - 1].number}}、{{summary.radio[radioPos - 1].title}}
          <p>A：{{summary.radio[radioPos - 1].planA}}</p>
          <p>B：{{summary.radio[radioPos - 1].planB}}</p>
          <p>C：{{summary.radio[radioPos - 1].planC}}</p>
          <p>D：{{summary.radio[radioPos - 1].planD}}</p>
        </div>
      </div>
      <div v-else-if="drawerTitle === '填空题'" style="width:320px">
        <el-button class="el-button-round" :class="selectColorFill(item)"  @click="fillChange(item.number)" round v-for="item in summary.fill" :key="item.id">{{item.number}}</el-button>
        <div class="title">
          {{summary.fill[fillPos - 1].number}}、{{summary.fill[fillPos - 1].title}}
          <p>参考答案：{{summary.fill[fillPos - 1].sure}}</p>
        </div>
      </div>
      <div v-else-if="drawerTitle === '判断题'" style="width:320px">
        <el-button class="el-button-round" :class="selectColorJudge(item)"  @click="judgeChange(item.number)" round v-for="item in summary.judge" :key="item.id">{{item.number}}</el-button>
        <div class="title">
          {{summary.judge[judgePos - 1].number}}、{{summary.judge[judgePos - 1].title}}
          <p>参考答案：{{summary.judge[judgePos - 1].sure === '1' ? '对' : '错'}}</p>
        </div>
      </div>
    </el-drawer>
  </div>
</template>

<script>
import { Loading } from 'element-ui'
export default {
  data () {
    return {
      // 动画
      loadingInstance: null,
      tableData: [],
      examId: '',
      map: '',
      summary: [],
      loading: true,
      radioPos: 1,
      fillPos: 1,
      judgePos: 1,
      drawer: false,
      drawerTitle: ''
    }
  },
  methods: {
    // 获取用户信息
    async getUserList () {
      const { code, data, msg } = await this.$axios.post(`exam/getUserList/${this.examId}`)
      if (code === '200') {
        this.tableData = data
        // 添加排名
        var number = 1
        for (let i = 0; i < this.tableData.length; i++) {
          this.tableData[i].number = number
          for (let j = i + 1; j < this.tableData.length; j++) {
            if (this.tableData[i].score === this.tableData[j].score) {
              i = j
              this.tableData[j].number = number
              continue
            }
            break
          }
          number++
        }
        // 遍历获取成绩以及对应人数
        var map = new Map()
        for (let i = 0; i < data.length; i++) {
          if (map.has(data[i].score)) {
            map.set(data[i].score, +map.get(data[i].score) + 1)
          } else {
            map.set(data[i].score, 1)
          }
        }
        this.achievement(map)
      } else if (code !== '401') {
        this.$message.error(msg)
      } else {
        this.$router.push('/404')
      }
    },
    // 查看用户答题详情
    detialInfo (username) {
      const p = this.$router.resolve({ path: `/finished/${this.examId}/${username}` })
      window.open(p.href, '_blank')
    },
    // 成绩柱状图
    achievement (map) {
      var chartDom = document.getElementById('score')
      var myChart = this.$echarts.init(chartDom, 'light')
      var option
      var x = []
      var y = []
      map.forEach((value, key) => {
        x.unshift(key)
        y.unshift(value)
      })
      option = {
        tooltip: {
          trigger: 'axis',
          axisPointer: {
            type: 'shadow'
          },
          transitionDuration: 0
        },
        title: {
          show: true,
          text: '成绩分析柱状图',
          x: 'center'
        },
        grid: {
          left: '3%',
          right: '4%',
          bottom: '5%',
          containLabel: true
        },
        xAxis: [
          {
            type: 'category',
            data: x,
            axisTick: {
              alignWithLabel: true
            },
            name: '分数',
            nameLocation: 'middle',
            nameTextStyle: {
              fontSize: 15,
              padding: 5
            }
          }
        ],
        yAxis: [
          {
            type: 'value',
            name: '人数',
            nameTextStyle: {
              fontSize: 15
            }
          }
        ],
        series: [
          {
            name: '人数',
            type: 'bar',
            barWidth: '60%',
            data: y
            // 设置平均值线
            // markLine: {
            //   data: [
            //     {
            //       type: 'average',
            //       name: '平均值'
            //     }
            //   ]
            // }
          }
        ],
        // 下载
        toolbox: {
          show: true,
          feature: {
            mark: { show: true },
            saveAsImage: { show: true }
          }
        }
      }
      // option && myChart.setOption(option)
      // 调用showLoading方法
      myChart.showLoading({
        text: 'loading',
        color: '#73c0de',
        textColor: '#000',
        maskColor: 'rgba(255, 255, 255, 0.2)',
        zlevel: 0
      })
      setTimeout(() => {
        // setOption前隐藏loading事件
        myChart.hideLoading()
        myChart.setOption(option)
      }, 1000)
    },
    // 获取答题统计信息
    async getSureDetail () {
      const { code, data, msg } = await this.$axios.post(`exam/getDetailScore/${this.examId}`)
      if (code === '200') {
        for (let i = 0; i < this.tableData.length; i++) {
          this.tableData[i].hundredScore = this.tableData[i].score / (data.totalValue / 100)
        }
        this.summary = data
        for (let i = 0; i < this.summary.radio.length; i++) {
          this.summary.radio[i].number = i + 1
        }
        for (let i = 0; i < this.summary.fill.length; i++) {
          this.summary.fill[i].number = i + 1
        }
        for (let i = 0; i < this.summary.judge.length; i++) {
          this.summary.judge[i].number = i + 1
        }
        this.pieChart()
        this.loading = false
        this.radioAnalyse()
        this.fillAnalyse()
        this.judgeAnalyse()
      } else {
        this.$message.error(msg)
      }
    },
    // 成绩分析饼状图
    pieChart () {
      // 获取每个等级的人数
      var grade = []
      grade[0] = grade[1] = grade[2] = grade[3] = grade[4] = 0
      for (let i = 0; i < this.tableData.length; i++) {
        if (this.tableData[i].hundredScore >= 90) {
          grade[0]++
        } else if (this.tableData[i].hundredScore >= 80) {
          grade[1]++
        } else if (this.tableData[i].hundredScore >= 70) {
          grade[2]++
        } else if (this.tableData[i].hundredScore >= 60) {
          grade[3]++
        } else {
          grade[4]++
        }
      }
      var chartDom = document.getElementById('pie')
      var myChart = this.$echarts.init(chartDom)
      var option

      option = {
        legend: {
          // data: ['A+：90分以上（百分制）', 'A：80-90（百分制）', 'B：70-80分（百分制）', 'C：60-70分（百分制）', 'D：低于60分（百分制）'],
          x: 'left',
          y: '100px',
          orient: 'vertical'
        },
        title: {
          text: '成绩等级划分（百分制）',
          x: 'center'
        },
        tooltip: {
          trigger: 'item',
          formatter: '{a}<br/>{b}的有{c}人 ({d}%) '
        },
        toolbox: {
          show: true,
          feature: {
            mark: { show: true },
            saveAsImage: { show: true }
          }
        },
        series: [
          {
            name: '成绩等级划分',
            type: 'pie',
            radius: [50, 150],
            center: ['50%', '50%'],
            roseType: 'area',
            itemStyle: {
              borderRadius: 8,
              emphasis: {
                label: {
                  show: true
                },
                labelLine: {
                  show: true
                }
              }
            },
            data: [
              { value: grade[0], name: 'A+：90分以上' },
              { value: grade[1], name: 'A：80-90' },
              { value: grade[2], name: 'B：70-80分）' },
              { value: grade[3], name: 'C：60-70分' },
              { value: grade[4], name: 'D：低于60分' }
            ],
            color: ['#5470c6', '#91cc75', '#fac858', '#ee6666', '#73c0de']
          }
        ]
      }
      // option && myChart.setOption(option)
      myChart.showLoading({
        text: 'loading',
        color: '#73c0de',
        textColor: '#000',
        maskColor: 'rgba(255, 255, 255, 0.2)',
        zlevel: 0
      })
      setTimeout(() => {
        // setOption前隐藏loading事件
        myChart.hideLoading()
        myChart.setOption(option)
      }, 1000)
    },
    // 选择题分析
    radioAnalyse () {
      var pos = '第' + this.radioPos + '题'
      var chartDom = document.getElementById('radio')
      var myChart = this.$echarts.init(chartDom)
      var option
      var grade = []
      var sum = this.tableData.length
      grade[0] = (this.summary.radio[this.radioPos - 1].numberA * 1.0 / sum * 100).toFixed(2)
      grade[1] = (this.summary.radio[this.radioPos - 1].numberB * 1.0 / sum * 100).toFixed(2)
      grade[2] = (this.summary.radio[this.radioPos - 1].numberC * 1.0 / sum * 100).toFixed(2)
      grade[3] = (this.summary.radio[this.radioPos - 1].numberD * 1.0 / sum * 100).toFixed(2)
      // 显示正确选项
      var selected = []
      selected[0] = 'A选项'
      selected[1] = 'B选项'
      selected[2] = 'C选项'
      selected[3] = 'D选项'
      selected[(this.summary.radio[this.radioPos - 1].sure.toUpperCase()).charCodeAt() - 65] += '（正确选项）'
      option = {
        title: {
          text: '选择题分析',
          subtext: pos,
          left: 'center',
          triggerEvent: true
        },
        tooltip: {
          trigger: 'item',
          formatter: '{a}<br/>{b}：{d}%'
        },
        legend: {
          top: 'bottom'
        },
        toolbox: {
          show: true,
          feature: {
            mark: { show: true },
            saveAsImage: { show: true }
          }
        },
        series: [
          {
            name: '选择题选项',
            type: 'pie',
            radius: '50%',
            data: [
              { value: grade[0], name: selected[0] },
              { value: grade[1], name: selected[1] },
              { value: grade[2], name: selected[2] },
              { value: grade[3], name: selected[3] }
            ],
            color: ['#5470c6', '#fac858', '#ee6666', '#73c0de'],
            emphasis: {
              itemStyle: {
                shadowBlur: 10,
                shadowOffsetX: 0,
                shadowColor: 'rgba(0, 0, 0, 0.5)'
              }
            }
          }
        ]
      }
      // 页面第一次加载显示延迟加载
      if (this.drawerTitle === '') {
        myChart.showLoading({
          text: 'loading',
          color: '#73c0de',
          textColor: '#000',
          maskColor: 'rgba(255, 255, 255, 0.2)',
          zlevel: 0
        })
        setTimeout(() => {
          // setOption前隐藏loading事件
          myChart.hideLoading()
          myChart.setOption(option)
        }, 1000)
      } else {
        option && myChart.setOption(option)
      }
      myChart.on('click', (params) => {
        if (params.componentType === 'title') {
          if (params.event.target.style.text.length === 3) {
            this.drawerTitle = '选择题'
            this.drawer = true
          }
        }
      })
    },
    // 点击更换选择题
    radioChange (id) {
      this.radioPos = id
      this.radioAnalyse()
    },
    // 点击更换填空题
    fillChange (id) {
      this.fillPos = id
      this.fillAnalyse()
    },
    // 点击更换判断题
    judgeChange (id) {
      this.judgePos = id
      this.judgeAnalyse()
    },
    // 填空题分析
    fillAnalyse () {
      var pos = '第' + this.fillPos + '题'
      var chartDom = document.getElementById('fill')
      var myChart = this.$echarts.init(chartDom)
      var option
      option = {
        title: {
          text: '填空题分析',
          subtext: pos,
          left: 'center',
          triggerEvent: true
        },
        tooltip: {
          trigger: 'item',
          formatter: '{b}率：{d}%'
        },
        legend: {
          top: 'bottom'
        },
        toolbox: {
          show: true,
          feature: {
            mark: { show: true },
            saveAsImage: { show: true }
          }
        },
        series: [
          {
            type: 'pie',
            radius: '50%',
            data: [
              { value: this.summary.fill[this.fillPos - 1].correctNumber, name: '正确' },
              { value: this.summary.fill[this.fillPos - 1].errorNumber, name: '错误' }
            ],
            color: ['#73c0de', '#ee6666'],
            emphasis: {
              itemStyle: {
                shadowBlur: 10,
                shadowOffsetX: 0,
                shadowColor: 'rgba(0, 0, 0, 0.5)'
              }
            }
          }
        ]
      }
      // 页面第一次加载显示延迟加载
      if (this.drawerTitle === '') {
        myChart.showLoading({
          text: 'loading',
          color: '#73c0de',
          textColor: '#000',
          maskColor: 'rgba(255, 255, 255, 0.2)',
          zlevel: 0
        })
        setTimeout(() => {
          // setOption前隐藏loading事件
          myChart.hideLoading()
          myChart.setOption(option)
        }, 1000)
      } else {
        option && myChart.setOption(option)
      }
      myChart.on('click', (params) => {
        if (params.componentType === 'title') {
          if (params.event.target.style.text.length === 3) {
            this.drawerTitle = '填空题'
            this.drawer = true
          }
        }
      })
    },
    // 判断题分析
    judgeAnalyse () {
      var pos = '第' + this.judgePos + '题'
      var chartDom = document.getElementById('judge')
      var myChart = this.$echarts.init(chartDom)
      var option
      option = {
        title: {
          text: '判断题分析',
          subtext: pos,
          left: 'center',
          triggerEvent: true
        },
        tooltip: {
          trigger: 'item',
          formatter: '{b}率：{d}%'
        },
        legend: {
          top: 'bottom'
        },
        toolbox: {
          show: true,
          feature: {
            mark: { show: true },
            saveAsImage: { show: true }
          }
        },
        series: [
          {
            type: 'pie',
            radius: '50%',
            data: [
              { value: this.summary.judge[this.judgePos - 1].correctNumber, name: '正确' },
              { value: this.summary.judge[this.judgePos - 1].errorNumber, name: '错误' }
            ],
            color: ['#3ba272', '#fc8452'],
            emphasis: {
              itemStyle: {
                shadowBlur: 10,
                shadowOffsetX: 0,
                shadowColor: 'rgba(0, 0, 0, 0.5)'
              }
            }
          }
        ]
      }
      // 页面第一次加载显示延迟加载
      if (this.drawerTitle === '') {
        myChart.showLoading({
          text: 'loading',
          color: '#73c0de',
          textColor: '#000',
          maskColor: 'rgba(255, 255, 255, 0.2)',
          zlevel: 0
        })
        setTimeout(() => {
          // setOption前隐藏loading事件
          myChart.hideLoading()
          myChart.setOption(option)
        }, 1000)
      } else {
        option && myChart.setOption(option)
      }
      myChart.on('click', (params) => {
        if (params.componentType === 'title') {
          if (params.event.target.style.text.length === 3) {
            this.drawerTitle = '判断题'
            this.drawer = true
          }
        }
      })
    }
  },
  async created () {
    this.examId = this.$route.params.id
    this.loadingInstance = Loading.service({
      text: '加载中',
      target: '.app-analyse'
    })
    this.getUserList()
    this.getSureDetail()
    // 加载完成，关闭动画
    this.loadingInstance.close()
  },
  computed: {
    selectColor () {
      return function (item) {
        return item.number === this.radioPos ? 'selected' : ''
      }
    },
    selectColorFill () {
      return function (item) {
        return item.number === this.fillPos ? 'selected' : ''
      }
    },
    selectColorJudge () {
      return function (item) {
        return item.number === this.judgePos ? 'selected' : ''
      }
    }
  }
}
</script>

<style lang="less" scoped>
.selected {
  color: #fff;
  background-color: #409EFF;
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
body,html,.app-analyse {
  overflow-x: hidden;
}
.rank {
  margin: 10px 0 0 10px;
  width: 330px;
}
/deep/ .el-card__header {
  padding: 10px 20px!important;
}
/deep/ .el-card__body {
  padding: 10px 5px;
}
#score,
#pie {
  width: 100%;
  height: 300px;
  margin-top: 10px;
}
#pie {
  margin-top: 40px;
  height: 400px;
  overflow: hidden;
}
#radio,
#fill,
#judge {
  margin-top: 10px;
  width: 100%;
  height: 236px;
  overflow: hidden;
}
.title {
  margin: 20px 10px;
}
.title p {
  margin-top: 10px;
}
</style>
