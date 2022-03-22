<template>
  <div>
    <el-alert show-icon center title="进行中的考试，点击对应的标题，即可进入考试；结束的考试，点击对应的标题，即可查看考试结果" type="info"></el-alert>
    <el-table :data="tableData" border style="width: 100%;margin-top:10px" :loading="loading">
      <el-table-column prop="id" label="编号" width="100" align="center">
      </el-table-column>
      <el-table-column  label="标题" align="center">
        <template slot-scope="scope">
          <el-button type="primary" class="title" :show-overflow-tooltip='true' @click="details(scope.row)">
            {{ scope.row.name }}
          </el-button>
        </template>
      </el-table-column>
      <el-table-column label="状态" width="300" align="center">
        <template slot-scope="scope">
          <div :style="stateColor(scope.row.date)">
            {{ scope.row.date }}
          </div>
        </template>
      </el-table-column>
      <template slot="empty">
        <p>暂无考试</p>
      </template>
    </el-table>
  </div>
</template>

<script>
import moment from 'moment'
export default {
  props: {
    user: Object
  },
  data () {
    return {
      tableData: [],
      loading: true
    }
  },
  methods: {
    // 获取考试列表
    async getList () {
      const { data, code, msg } = await this.$axios.post(`exam/getByRole/${this.user.role}`)
      if (code === '200') {
        this.tableData = data
        const nowDate = moment(new Date()).format('YYYY-MM-DD H:mm:ss')
        this.tableData.forEach(item => {
          if (moment(nowDate).isBetween(item.startTime, item.endTime)) {
            item.date = '进行中 至 ' + item.endTime
          } else if (moment(nowDate).isSameOrAfter(item.endTime)) {
            item.date = '已结束 @ ' + item.endTime
          } else {
            item.date = '未开始 @ ' + item.startTime
          }
        })
      } else {
        this.$message.error(msg)
      }
      this.loading = false
    },
    // 考试进行中的话，点击可以进入考试页面
    details (row) {
      if (row.date.slice(0, 3) === '进行中') {
        const p = this.$router.resolve({ path: `/running/${row.id}` })
        window.open(p.href, '_blank')
      } else if (row.date.slice(0, 3) === '已结束') {
        const p = this.$router.resolve({ path: `/finished/${row.id}/${this.user.username}` })
        window.open(p.href, '_blank')
      } else {
        this.$message.warning('考试还未开始')
      }
    }
  },
  mounted () {
    this.getList()
  },
  computed: {
    stateColor (state) {
      return function (state) {
        if (state.slice(0, 3) === '未开始') {
          return { color: 'blue' }
        } else if (state.slice(0, 3) === '进行中') {
          return { color: 'green' }
        } else {
          return { color: 'orangered' }
        }
      }
    }
  }
}
</script>

<style lang="less" scoped>
.title {
  cursor: pointer;
  color: #1890ff;
  background: transparent;
  border-color: transparent;
  width: 100%;
  height: 100%;
}
</style>
