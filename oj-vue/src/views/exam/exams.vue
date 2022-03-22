<template>
  <div>
    <!-- 搜索框 -->
    <el-input v-model="query" clearable placeholder="请输入考试名称" class="input-with-select" style="width:300px;margin-right:20px">
      <el-button slot="append" icon="el-icon-search" @click="queryByName"></el-button>
    </el-input>
     <el-table :data="tableData" stripe style="width: 100%" v-loading="loading">
       <el-table-column prop="id" label="编号" width="70">
      </el-table-column>
      <el-table-column prop="name" label="考试名称" width="180" :show-overflow-tooltip='true'>
      </el-table-column>
      <el-table-column prop="totalValue" label="卷面分值" width="90">
      </el-table-column>
      <el-table-column prop="startTime" label="开始时间">
      </el-table-column>
      <el-table-column prop="endTime" label="结束时间">
      </el-table-column>
      <el-table-column prop="createTime" label="创建时间">
      </el-table-column>
      <el-table-column v-if="user.role === '管理员' || user.role === '超级管理员'" prop="createUsername" label="创建者" width="90"  :show-overflow-tooltip='true'>
      </el-table-column>
       <el-table-column prop="participate" label="考生" width="80"  :show-overflow-tooltip='true'>
      </el-table-column>
      <el-table-column label="试卷状态" width="90">
        <template slot-scope="scope">
          <el-switch @click.native="handleState(scope.row)" v-model="scope.row.state" active-color="#13ce66" inactive-color="#ff4949">
          </el-switch>
        </template>
      </el-table-column>
      <el-table-column label="操作" width="180px">
        <template slot-scope="scope">
          <el-button size="mini" @click="handleEdit(scope.row.id)">编辑<i class="el-icon-edit el-icon--right"></i></el-button>
          <el-button size="mini" type="danger"
            @click="handleDelete(scope.row.id)">删除<i class="el-icon-delete el-icon--right"></i></el-button>
        </template>
      </el-table-column>
    </el-table>
    <!-- 分页 -->
    <div class="block" style="margin: 20px 0">
      <el-pagination :current-page="pageNum" :page-sizes="[10, 15, 30, 50]" :page-size="pageSize"
        layout="total, sizes, prev, pager, next, jumper" :total="pageTotal" @size-change="handleSizeChange"
@current-change="handleCurrentChange">
      </el-pagination>
    </div>
  </div>
</template>

<script>
export default {
  props: {
    user: Object
  },
  data () {
    return {
      tableData: [],
      loading: true,
      query: '',
      pageNum: 1,
      pageSize: 10,
      pageTotal: 1
    }
  },
  methods: {
    // 获取考试信息
    getExams () {
      var username = this.user.role === '管理员' || this.user.role === '超级管理员' ? '' : this.user.username
      this.$axios.get('exam/getExams', {
        params: {
          pageNum: this.pageNum,
          pageSize: this.pageSize,
          name: this.query,
          username: username
        }
      }).then(res => {
        this.pageNum = res.current
        this.pageTotal = res.total
        this.tableData = res.records
        for (let i = 0; i < this.tableData.length; i++) {
          this.tableData[i].totalValue = +0
          if (typeof (this.tableData[i].radio) === 'undefined') {
            this.tableData[i].radio = []
          } else {
            this.tableData[i].totalValue += +this.tableData[i].radioValue * (this.tableData[i].radio.split(';').length - 1)
          }
          if (typeof (this.tableData[i].fill) === 'undefined') {
            this.tableData[i].fill = []
          } else {
            this.tableData[i].totalValue += +this.tableData[i].fillValue * (this.tableData[i].fill.split(';').length - 1)
          }
          if (typeof (this.tableData[i].judge) === 'undefined') {
            this.tableData[i].judge = []
          } else {
            this.tableData[i].totalValue += +this.tableData[i].judgeValue * (this.tableData[i].judge.split(';').length - 1)
          }
          if (typeof (this.tableData[i].programme) === 'undefined') {
            this.tableData[i].programme = []
          } else {
            var arr = this.tableData[i].programmeValue.split(';')
            for (let j = 0; j < arr.length - 1; j++) {
              this.tableData[i].totalValue += +arr[j]
            }
          }
        }
        this.loading = false
      }).catch(res => {
        console.log(res)
        this.$message.error('请求超时', {
          duration: 2000
        })
        this.loading = false
      })
    },
    // 编辑考试信息
    async handleEdit (id) {
      const { code } = await this.$axios.post('exam/editExams')
      if (code === '200') {
        this.$router.push({
          path: 'exam',
          query: {
            id: id
          }
        })
      }
    },
    // 删除考试
    async handleDelete (id) {
      this.$confirm('此操作将永久删除本场考试, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        this.$axios.delete(`exam/deleteById/${id}`).then((res) => {
          if (this.tableData.length === 1 && this.pageNum > 1) {
            this.pageNum--
          }
          this.getExams()
          this.$message({
            type: 'success',
            message: '删除成功!'
          })
        }).catch(() => {
          this.$message({
            type: 'error',
            message: '系统错误!'
          })
        })
      })
    },
    // 通过考试名称查询
    queryByName () {
      this.getExams()
    },
    handleSizeChange (val) {
      this.pageSize = val
      this.getExams()
    },
    handleCurrentChange (val) {
      this.pageNum = val
      this.getExams()
    },
    async handleState (row) {
      try {
        const res = await this.$axios.patch(`exam/examState/${row.id}/${row.state}`)
        if (res.code !== '200') {
          row.state = !row.state
        }
      } catch (e) {}
    }
  },
  mounted () {
    this.getExams()
  }
}
</script>

<style>
/* el-table隐藏之后显示内容样式 */
.el-tooltip__popper{
  max-width:20%;
}
.el-tooltip__popper,.el-tooltip__popper.is-dark{
  background:rgb(48, 65, 86) !important;
  color: #fff !important;
  line-height: 24px;
}
</style>
