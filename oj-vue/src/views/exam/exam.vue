<template>
  <div>
    <el-steps :active="active" align-center>
      <el-step title="步骤1" description="选择题"></el-step>
      <el-step title="步骤2" description="填空题"></el-step>
      <el-step title="步骤3" description="判断题"></el-step>
      <el-step title="步骤4" description="简答题"></el-step>
      <el-step title="步骤5" description="答题信息"></el-step>
    </el-steps>

    <el-tabs v-model="Active" tab-position="left" style="min-height: 230px;margin-top:20px" @tab-click="tabClick">
      <el-tab-pane label="选择题" name="tab-1">
        <el-button style="display:inline-block;margin-right:20px" type="success" @click="addShow">添加选择题</el-button>
        选择题分值：
        <el-select style="width:100px" v-model="form.radioValue" placeholder="请选择">
          <el-option v-for="item in options" :key="item.value" :label="item.label" :value="item.value">
          </el-option>
        </el-select>
        <el-table :data="form.radio" stripe style="width: 100%;margin-top:10px" v-loading="loading" border>
          <el-table-column type="index" width="50">
          </el-table-column>
          <el-table-column prop="title"  label="题干" max-width="300px">
          </el-table-column>
          <el-table-column fixed="right" label="操作"  width="90px">
            <template slot-scope="scope">
              <el-button type="text" size="small" @click="deleteClick(scope.row.id)">移除</el-button>
            </template>
          </el-table-column>
        </el-table>
      </el-tab-pane>
      <el-tab-pane label="填空题" name="tab-2">
        <el-button style="display:inline-block;margin-right:20px" type="success" @click="addShow">添加填空题</el-button>
        填空题分值：
        <el-select style="width:100px" v-model="form.fillValue" placeholder="请选择">
          <el-option v-for="item in options" :key="item.value" :label="item.label" :value="item.value">
          </el-option>
        </el-select>
        <el-table :data="form.fill" stripe style="width: 100%;margin-top:10px" v-loading="loading"  border>
          <el-table-column type="index" width="50">
          </el-table-column>
          <el-table-column prop="title"  label="题干" max-width="300px">
          </el-table-column>
          <el-table-column fixed="right" label="操作"  width="90px">
            <template slot-scope="scope">
              <el-button type="text" size="small" @click="deleteClick(scope.row.id)">移除</el-button>
            </template>
          </el-table-column>
        </el-table>
      </el-tab-pane>
      <el-tab-pane label="判断题" name="tab-3">
        <el-button style="display:inline-block;margin-right:20px" type="success" @click="addShow">添加判断题</el-button>
        判断题分值：
        <el-select style="width:100px" v-model="form.judgeValue" placeholder="请选择">
          <el-option v-for="item in options" :key="item.value" :label="item.label" :value="item.value">
          </el-option>
        </el-select>
        <el-table :data="form.judge" stripe style="width: 100%;margin-top:10px" v-loading="loading"  border>
          <el-table-column type="index" width="50">
          </el-table-column>
          <el-table-column prop="title"  label="题干" max-width="300px">
          </el-table-column>
          <el-table-column fixed="right" label="操作"  width="90px">
            <template slot-scope="scope">
              <el-button type="text" size="small" @click="deleteClick(scope.row.id)">移除</el-button>
            </template>
          </el-table-column>
        </el-table>
      </el-tab-pane>
      <el-tab-pane label="简答题" name="tab-4">
        <el-button style="display:inline-block;margin-right:20px" type="success" @click="addShow">添加简答题</el-button>
        <el-table :data="form.programme" stripe style="width: 100%;margin-top:10px" v-loading="loading"  border>
          <el-table-column type="index" width="50">
          </el-table-column>
          <el-table-column prop="title"  label="题干" max-width="300px">
          </el-table-column>
          <el-table-column v-if="update" prop="value" label="分值" width="100px" >
          </el-table-column>
          <el-table-column  width="80px" label="操作" >
            <template slot-scope="scope">
              <el-button type="text" size="small" @click="changeValue(scope.row)">修改分值</el-button>
            </template>
          </el-table-column>
          <el-table-column fixed="right" width="80px">
            <template slot-scope="scope">
              <el-button type="text" size="small" @click="deleteClick(scope.row.id)">移除</el-button>
            </template>
          </el-table-column>
        </el-table>
      </el-tab-pane>
      <el-tab-pane label="答题信息" name="tab-5">
        考试时间：
        <el-date-picker v-model="date" type="datetimerange" range-separator="至" start-placeholder="考试开始日期" end-placeholder="考试结束日期">
        </el-date-picker>
        <el-divider></el-divider>
        参加考试的角色：
        <el-select v-model="form.form.participate" placeholder="请选择">
          <el-option v-for="item in roles" :key="item.value" :label="item.label" :value="item.value">
          </el-option>
        </el-select>
        <el-divider></el-divider>
        请输入考试科目名称：
        <el-input v-model="form.name" style="width:200px"></el-input>
        <el-divider></el-divider>
        <el-descriptions title="卷面分值">
          <el-descriptions-item label="选择题"> {{totalValue.radio}} 分</el-descriptions-item>
          <el-descriptions-item label="填空题"> {{totalValue.fill}} 分</el-descriptions-item>
          <el-descriptions-item label="判断题"> {{totalValue.judge}} 分</el-descriptions-item>
          <el-descriptions-item label="简答题"> {{totalValue.programme}} 分</el-descriptions-item>
          <el-descriptions-item label="卷面总分"> {{totalValue.total}} 分</el-descriptions-item>
        </el-descriptions>
      </el-tab-pane>
    </el-tabs>
    <el-button type="primary" style="margin-left:105px;margin-top:20px" @click="nextPage">{{ active != 5 ? '下一步' : '完成' }}</el-button>

    <!-- 添加题目页面 -->
    <el-dialog title="添加题目" :visible.sync="dialogTableVisible" :close-on-click-modal="false"  @keyup.enter.native="queryByTitle">
      <el-input v-model="query"  clearable placeholder="请输入题干关键词" class="input-with-select" style="width:300px;margin-right:20px">
        <el-button slot="append" icon="el-icon-search" @click="queryByTitle"></el-button>
      </el-input>
      <el-table @selection-change="handleSelectionChange" :data="dialogTable" stripe style="width: 100%;margin-top:10px"  border>
        <el-table-column type="selection" width="55px">
        </el-table-column>
        <el-table-column prop="title"  label="题干">
        </el-table-column>
      </el-table>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogTableVisible = false">取 消</el-button>
        <el-button type="primary" @click="addTitle">添 加</el-button>
      </div>
    </el-dialog>

  </div>
</template>

<script>
import moment from 'moment'

export default {
  data () {
    return {
      active: 1,
      Active: 'tab-1',
      form: {
        // 选择题
        radio: [],
        radioValue: '1',
        // 判断题
        judge: [],
        judgeValue: '1',
        // 填空题
        fill: [],
        fillValue: '1',
        // 简答题
        programme: [],
        // 考试基本信息(时间、允许谁考等)
        form: {
          startTime: '',
          endTime: '',
          participate: '',
          username: JSON.parse(localStorage.getItem('userInfo')).username
        },
        name: ''
      },
      dialogTableVisible: false,
      dialogTable: [],
      query: '',
      multipleSelection: [],
      options: [{
        value: '1',
        label: '1'
      }, {
        value: '2',
        label: '2'
      }, {
        value: '3',
        label: '3'
      }, {
        value: '4',
        label: '4'
      }, {
        value: '5',
        label: '5'
      }],
      loading: false,
      // 处理局部更新，页面更新的问题，vue官方建议的强制刷新也无效
      update: true,
      date: [],
      roles: [],
      // 每个模块的总分数
      totalValue: {
        radio: 0,
        judge: 0,
        fill: 0,
        programme: 0,
        total: 0
      },
      // 判断是添加还是修改
      examId: ''
    }
  },
  methods: {
    // 获取各个模块的分数
    getTotalValue () {
      this.totalValue.radio = this.form.radio.length * this.form.radioValue
      this.totalValue.judge = this.form.judge.length * this.form.judgeValue
      this.totalValue.fill = this.form.fill.length * this.form.fillValue
      this.totalValue.programme = 0
      this.form.programme.forEach(item => {
        this.totalValue.programme += item.value
      })
      this.totalValue.total = this.totalValue.radio + this.totalValue.judge + this.totalValue.fill + this.totalValue.programme
    },
    // 点击tab进行切换
    tabClick (tab) {
      this.active = +tab.index + 1
      if (this.active === 5) {
        this.getTotalValue()
      } else if (this.active === 4) {
        // 功能暂未开放
        this.$notify.warning({
          title: '提示',
          message: '当前功能未开放，试卷中不能添加简答题'
        })
      }
    },
    // 点击按钮进行下一步/完成添加
    async nextPage () {
      this.getTotalValue()
      if (this.active !== 5) {
        this.active++
        this.Active = 'tab-' + this.active
        // 功能暂未开放
        if (this.active === 4) {
          this.$notify.warning({
            title: '提示',
            message: '当前功能未开放，试卷中不能添加简答题'
          })
        }
      } else {
        // 判断是否创建的是空试卷
        if (this.form.radio.length === 0 && this.form.judge.length === 0 && this.form.fill.length === 0 && this.form.programme.length === 0) {
          this.$notify.error({
            title: '错误',
            message: '请勿创建空试卷'
          })
        } else {
          if (this.date === '') {
            this.$notify.error({
              title: '错误',
              message: '请选择考试的时间范围'
            })
            return
          }
          if (moment(this.date[0], moment.ISO_8601).isBefore(new Date(new Date().getTime() + 1 * 60 * 60 * 1000), moment.ISO_8601) && this.examId === '') {
            this.$notify.warning({
              title: '提示',
              message: '当前日期不得创建考试，请核查考试时间（考试前一个小时不得创建试卷）'
            })
            return
          }
          if (this.form.form.participate === '') {
            this.$notify.error({
              title: '错误',
              message: '请选择参加考试的人员角色'
            })
            return
          }
          if (this.form.name === '') {
            this.$notify.error({
              title: '错误',
              message: '请输入考试科目名称'
            })
            return
          }
          if (moment(this.date[0], moment.ISO_860).isSameOrAfter(this.date[1], moment.ISO_860)) {
            this.$notify.warning({
              title: '警告',
              message: '请注意考试时间范围'
            })
            return
          }
          this.form.form.startTime = moment(this.date[0]).format('YYYY-MM-DD H:mm:ss')
          this.form.form.endTime = moment(this.date[1]).format('YYYY-MM-DD H:mm:ss')
          var radio = ''
          var judge = ''
          var fill = ''
          var programme = ''
          var programmeValue = ''
          this.form.radio.forEach(item => {
            radio += item.id + ';'
          })
          this.form.judge.forEach(item => {
            judge += item.id + ';'
          })
          this.form.fill.forEach(item => {
            fill += item.id + ';'
          })
          this.form.programme.forEach(item => {
            programme += item.id + ';'
            programmeValue += item.value + ';'
          })
          // console.log(this.examId)
          const requestType = this.examId === '' ? 'post' : 'put'
          const { code, msg } = await this.$axios[requestType]('exam/save', {
            id: this.examId,
            radio: radio,
            radioValue: this.form.radioValue,
            judge: judge,
            judgeValue: this.form.judgeValue,
            fill: fill,
            fillValue: this.form.fillValue,
            programme: programme,
            programmeValue: programmeValue,
            participate: this.form.form.participate,
            createUsername: this.form.form.username,
            endTime: this.form.form.endTime,
            startTime: this.form.form.startTime,
            name: this.form.name
          })
          if (code !== '200') {
            this.$notify.error({
              title: '失败',
              message: msg
            })
            return
          }
          // 创建成功之后还原
          this.form.radio = []
          this.form.radioValue = '1'
          this.form.judge = []
          this.form.judgeValue = '1'
          this.form.fill = []
          this.form.fillValue = '1'
          this.form.programme = []
          this.form.form.startTime = ''
          this.form.form.endTime = ''
          this.form.form.participate = ''
          this.active = 1
          this.Active = 'tab-1'
          this.date = ''
          if (this.examId !== '') {
            this.$notify.success({
              title: '成功',
              message: '试卷修改成功，试卷管理中进行查看'
            })
            // 这里要跳转一下，因为url带参数，再次添加试卷话，不好处理
            this.$router.push('/exams')
          } else {
            this.$notify.success({
              title: '成功',
              message: '成功创建试卷，可到试卷管理页面进行查看'
            })
          }
        }
      }
    },
    // 添加试题框显示
    addShow () {
      this.dialogTable = []
      this.query = ''
      this.dialogTableVisible = true
    },
    // 根据题干关键字查询
    async queryByTitle () {
      if (this.query === '') {
        return
      }
      this.loading = true
      var path = ''
      if (this.active === 1) {
        path = 'radio'
      } else if (this.active === 2) {
        path = 'fill'
      } else if (this.active === 3) {
        path = 'judge'
      } else if (this.active === 4) {
        path = 'programme'
      }
      const { data, msg, code } = await this.$axios.post(`${path}/queryByLike/${this.query}`)
      if (code === '200') {
        this.dialogTable = data
      } else if (code !== '401') {
        this.$message.error(msg)
      }
      this.loading = false
    },
    // 添加选中的题目
    addTitle () {
      if (this.multipleSelection.length === 0) {
        this.$notify.error({
          title: '错误',
          message: '请选择要添加的试题'
        })
      } else {
        if (this.active === 1) {
          this.form.radio = this.unique(this.form.radio.concat(this.multipleSelection))
        } else if (this.active === 2) {
          this.form.fill = this.unique(this.form.fill.concat(this.multipleSelection))
        } else if (this.active === 3) {
          this.form.judge = this.unique(this.form.judge.concat(this.multipleSelection))
        } else if (this.active === 4) {
          this.multipleSelection.forEach(item => {
            item.value = 2
          })
          this.form.programme = this.unique(this.form.programme.concat(this.multipleSelection))
        }
        this.dialogTableVisible = false
        this.$notify.success({
          title: '成功',
          message: '试题成功添加'
        })
      }
    },
    // 多选框
    handleSelectionChange (val) {
      this.multipleSelection = val
    },
    // 移除题目
    deleteClick (id) {
      if (this.active === 1) {
        this.form.radio = this.form.radio.filter((item) => item.id !== id)
      } else if (this.active === 2) {
        this.form.fill = this.form.fill.filter((item) => item.id !== id)
      } else if (this.active === 3) {
        this.form.judge = this.form.judge.filter((item) => item.id !== id)
      } else if (this.active === 4) {
        this.form.programme = this.form.programme.filter((item) => item.id !== id)
      }
    },
    // 利用Map数组去重
    unique (arr) {
      var map = new Map()
      arr.forEach((item, index) => {
        if (!map.has(item.id)) {
          map.set(item.id, item)
        }
      })
      return [...map.values()]
    },
    // 修改简单题分数
    changeValue (row) {
      this.$prompt('请输入新的分数', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消'
      }).then(({ value }) => {
        row.value = +value
        this.update = false
        this.$nextTick(() => {
          this.update = true
        })
      }).catch(() => {})
    },
    // 获取角色
    async getRoles () {
      const { code, msg, data } = await this.$axios.post('role/roles')
      if (code === '200') {
        data.forEach((item) => {
          this.roles.push({
            id: item.id,
            value: item.name,
            label: item.name
          })
        })
      } else if (code !== '401') {
        this.$message.error(msg)
      }
    }
  },
  mounted () {
    this.getRoles()
  },
  created () {
    const exp = this.$route.query
    if (JSON.stringify(exp) !== '{}') {
      this.examId = exp.id
      this.$axios.get(`exam/getExamById/${exp.id}`).then((res) => {
        if (typeof (res.data.radio) === 'undefined') {
          res.data.radio = []
        }
        if (typeof (res.data.fill) === 'undefined') {
          res.data.fill = []
        }
        if (typeof (res.data.judge) === 'undefined') {
          res.data.judge = []
        }
        if (typeof (res.data.programme) === 'undefined') {
          res.data.programme = []
        }
        for (let j = 0; j < res.data.programme.length; j++) {
          for (let i = 0; i < res.data.programmeId.length; i++) {
            if (Number(res.data.programmeId[i]) === Number(res.data.programme[j].id)) {
              res.data.programme[j].value = res.data.programmeValue[i]
            }
          }
        }
        this.form = res.data
        // 初始化考试时间范围
        this.date.push(this.form.form.startTime)
        this.date.push(this.form.form.endTime)
        this.getTotalValue()
      })
    }
  }
}
</script>

<style>
.el-tabs__item:focus.is-active.is-focus:not(:active) {
  box-shadow: none!important;
  border-radius: 0px!important;
}
</style>
