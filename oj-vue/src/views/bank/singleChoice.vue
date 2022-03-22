<template>
  <div>
    <!-- 搜索框 -->
    <el-input v-model="query" clearable placeholder="请输入要查询的题干" class="input-with-select" style="width:300px;margin-right:20px">
      <el-button slot="append" icon="el-icon-search" @click="queryByTitle"></el-button>
    </el-input>
    <!-- 添加用户 -->
    <el-button type="success" plain icon="el-icon-add" @click="add">添加题目<i class="el-icon-circle-plus-outline el-icon--right"></i></el-button>
    <!-- 批量导入 -->
    <el-upload :action="urlImport" :show-file-list="false" accept="xlsx" :on-success="handleExcelImportSuccess" :on-error="handleExcelImportError" style="display: inline-block;margin: 0 20px">
      <el-button type="primary" @click="lend">批量导入题目<i class="el-icon-upload2 el-icon--right"></i></el-button>
    </el-upload>
    <el-table :data="tableData" stripe style="width: 100%;margin-top:10px" v-loading="loading" border>
      <el-table-column prop="title"  label="题干" max-width="300px">
      </el-table-column>
      <el-table-column prop="planA"  label="A选项">
      </el-table-column>
      <el-table-column prop="planB"  label="B选项">
      </el-table-column>
      <el-table-column prop="planC"  label="C选项">
      </el-table-column>
      <el-table-column prop="planD"  label="D选项">
      </el-table-column>
      <el-table-column prop="sure"  label="正确答案" width="80px">
      </el-table-column>
      <el-table-column prop="source"  label="来源" width="80px">
      </el-table-column>
      <el-table-column prop="createTime"  label="创建时间" width="160px">
      </el-table-column>
      <el-table-column fixed="right" label="操作"  width="90px">
        <template slot-scope="scope">
          <el-button @click="handleClick(scope.row)" type="text" size="small">编辑</el-button>
          <el-button type="text" size="small" @click="deleteClick(scope.row.id)">删除</el-button>
        </template>
      </el-table-column>
    </el-table>
  <!-- 分页 -->
    <div class="block" style="margin: 20px 0">
      <el-pagination :current-page="pageNum" :page-sizes="[10, 15, 20, 30]" :page-size="pageSize"
        layout="total, sizes, prev, pager, next, jumper" :total="pageTotal" @size-change="handleSizeChange"
@current-change="handleCurrentChange">
      </el-pagination>
    </div>
    <!-- 添加的dialog -->
    <el-dialog :close-on-click-modal="false" :title="save" :visible.sync="dialogAddVisible" width="500px">
      <el-form ref="form" status-icon :model="form" :rules="rules">
        <el-form-item label-position="right" label="题目" label-width="80px" prop="title">
          <el-input type="textarea" :autosize="{ minRows: 2, maxRows: 4}"  v-model="form.title"></el-input>
        </el-form-item>
        <el-form-item label-position="right" label="A选项" label-width="80px" prop="planA">
          <el-input v-model="form.planA" :autosize="{ minRows: 2, maxRows: 4}" type="textarea" ></el-input>
        </el-form-item>
        <el-form-item label-position="right" label="B选项" label-width="80px" prop="planB">
          <el-input v-model="form.planB"  :autosize="{ minRows: 2, maxRows: 4}" type="textarea"></el-input>
        </el-form-item>
         <el-form-item label-position="right" label="C选项" label-width="80px" prop="planC">
          <el-input v-model="form.planC" :autosize="{ minRows: 2, maxRows: 4}" type="textarea"></el-input>
        </el-form-item>
         <el-form-item label-position="right" label="D选项" label-width="80px" prop="planD">
          <el-input v-model="form.planD" :autosize="{ minRows: 2, maxRows: 4}" type="textarea"></el-input>
        </el-form-item>
        <el-form-item label-position="right" label="正确选项" label-width="80px" prop="sure">
            <el-select v-model="form.sure" placeholder="请选择">
              <el-option v-for="item in options" :key="item.value" :label="item.label" :value="item.value">
              </el-option>
            </el-select>
        </el-form-item>
        <el-form-item label-position="right" label="来源" label-width="80px" >
          <el-input v-model="form.source" :autosize="{ minRows: 2, maxRows: 4}" type="textarea"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogAddVisible = false">取 消</el-button>
        <el-button type="primary" @click="addTitle">确 定</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>

export default {
  name: 'singleChoic',
  data () {
    return {
      tableData: [],
      // 查询框的文本信息
      query: '',
      // 分页的属性，当前页数，页面显示的数据条数，总页数
      pageNum: 1,
      pageSize: 10,
      pageTotal: 1,
      // 表格数据加载效果
      loading: true,
      // 添加用户的dialog
      dialogAddVisible: false,
      form: {
        title: '',
        planA: '',
        planB: '',
        planC: '',
        planD: '',
        sure: '',
        source: '',
        id: ''
      },
      // 配置验证规则
      rules: {
        title: [
          { required: true, message: '必填项', trigger: ['blur', 'change'] }
        ],
        planA: [
          { required: true, message: '必填项', trigger: ['blur', 'change'] }
        ],
        planB: [
          { required: true, message: '必填项', trigger: ['blur', 'change'] }
        ],
        planC: [
          { required: true, message: '必填项', trigger: ['blur', 'change'] }
        ],
        planD: [
          { required: true, message: '必填项', trigger: ['blur', 'change'] }
        ],
        sure: [
          { required: true, message: '必填项', trigger: ['blur', 'change'] }
        ]
      },
      // 选项
      options: [{
        value: 'A',
        label: 'A'
      }, {
        value: 'B',
        label: 'B'
      }, {
        value: 'C',
        label: 'C'
      }, {
        value: 'D',
        label: 'D'
      }],
      // url
      urlImport: this.$axios.defaults.baseURL + '/radio/import'
    }
  },
  methods: {
    // 导入数据提示
    lend () {
      this.$message('导入格式（带标题）：题目|A选项|B选项|C选项|D选项|正确选项(A、B、C、D)|备注(可不填)，只允许上传xlsx文件')
    },
    // 导入数据失败
    handleExcelImportError () {
      this.$message.error('请勿上传非xlsx文件/上传格式有误')
    },
    // 导入数据成功
    handleExcelImportSuccess (data) {
      if (data.code === '500') {
        this.$message.error(data.msg)
      } else if (data.code === '200') {
        this.$message.success('导入成功！')
        this.getTitleList()
      } else {
        this.$message.error('系统出现未知错误！')
      }
    },
    // 删除题目
    async deleteClick (id) {
      try {
        await this.$confirm('此操作将永久删除该题目, 是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        })
        const { code, msg } = await this.$axios.delete(`radio/deleteById/${id}`)
        if (code === '200') {
          this.$message.success('删除成功')
          if (this.tableData.length === 1 && this.pageNum > 1) {
            this.pageNum--
          }
          this.getTitleList()
        } else {
          this.$message.error(msg)
        }
      } catch (e) {}
    },
    // 显示添加框
    async add () {
      this.dialogAddVisible = true
      this.form.title = ''
      this.form.planA = ''
      this.form.planB = ''
      this.form.planC = ''
      this.form.planD = ''
      this.form.sure = ''
      this.form.source = ''
      this.form.id = ''
      await this.$nextTick(() => {
        this.$refs.form.clearValidate()
      })
    },
    // 编辑题目信息显示
    handleClick (row) {
      this.form.id = row.id
      this.form.title = row.title
      this.form.planA = row.planA
      this.form.planB = row.planB
      this.form.planC = row.planC
      this.form.planD = row.planD
      this.form.sure = row.sure
      this.form.source = row.source
      this.dialogAddVisible = true
    },
    // 获取用户列表
    getTitleList () {
      this.$axios.get('radio/page', {
        // get请求参数
        params: {
          pageNum: this.pageNum,
          pageSize: this.pageSize,
          title: this.query
        }
      }).then(res => {
        this.pageNum = res.current
        this.pageTotal = res.total
        this.tableData = res.records
        this.loading = false
      }).catch(res => {
        this.$message.error('请求超时', {
          duration: 2000
        })
        this.loading = false
      })
    },
    // 每页显示多少条数据
    handleSizeChange (val) {
      this.pageSize = val
      this.getTitleList()
    },
    // 当前页数
    handleCurrentChange (val) {
      this.pageNum = val
      this.getTitleList()
    },
    // 添加题目
    async addTitle () {
      try {
        await this.$refs.form.validate()
        const apiType = this.form.id === '' ? 'post' : 'put'
        const res = await this.$axios[apiType]('radio/add', this.form)
        if (res.code === '200') {
          this.$message.success(res.msg)
          this.dialogAddVisible = false
          this.getTitleList()
        }
      } catch (e) {}
    },
    // 根据题目模糊查询
    queryByTitle () {
      if (this.query === '') {
        return
      }
      this.pageNum = 1
      this.getTitleList()
    }
  },
  mounted () {
    this.getTitleList()
  },
  computed: {
    save: function () {
      return this.form.title === '' ? '添加题目' : '修改题目'
    }
  }
}
</script>
