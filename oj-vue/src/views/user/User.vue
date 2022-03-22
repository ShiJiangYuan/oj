<template>
  <div>
    <!-- 搜索框 -->
    <el-input v-model="query" clearable placeholder="请输入内容" class="input-with-select" style="width:300px;margin-right:20px">
      <el-button slot="append" icon="el-icon-search" @click="queryByUsername"></el-button>
    </el-input>
    <!-- 添加用户 -->
    <el-button type="success" plain icon="el-icon-add" @click="dialogAddVisible = true">添加用户<i class="el-icon-circle-plus-outline el-icon--right"></i></el-button>
    <!-- 批量操作 -->
    <div style="margin:10px 10px 10px 0px">
      <el-button type="danger" @click="batchDelete">批量删除<i class="el-icon-remove-outline el-icon--right"></i></el-button>
      <el-upload :action="urlImport" :show-file-list="false" accept="xlsx" :on-success="handleExcelImportSuccess" :on-error="handleExcelImportError" style="display: inline-block;margin: 0 20px">
        <el-button type="primary" @click="lend">导入<i class="el-icon-upload2 el-icon--right"></i></el-button>
      </el-upload>
      <el-button type="primary" @click="exp">导出<i class="el-icon-download el-icon--right"></i></el-button>
    </div>
    <!-- 表格 -->
    <el-table :data="tableData" stripe style="width: 100%" v-loading="loading" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55">
      </el-table-column>
      <el-table-column prop="username" label="用户名" width="200">
      </el-table-column>
      <el-table-column v-if="user.role === '超级管理员'" prop="password" label="密码">
      </el-table-column>
      <el-table-column prop="nickname" label="昵称" >
      </el-table-column>
      <el-table-column prop="email" label="邮箱" width="220px">
      </el-table-column>
      <el-table-column prop="role" label="角色" width="130">
      </el-table-column>
      <el-table-column label="用户状态" width="100">
        <template slot-scope="scope">
          <el-switch @click.native="handleState(scope.row)" v-model="scope.row.state" active-color="#13ce66" inactive-color="#ff4949">
          </el-switch>
        </template>
      </el-table-column>
      <el-table-column label="操作"  width="310">
        <template slot-scope="scope">
          <el-button size="mini" @click="handleEdit(scope.row)"  v-if="scope.row.role !== '超级管理员' || user.role === '超级管理员'">编辑<i class="el-icon-edit el-icon--right"></i></el-button>
          <el-button size="mini" type="danger" plain  v-if="scope.row.role !== '超级管理员'"
            @click="handleDelete(scope.row.username)">删除<i class="el-icon-delete el-icon--right"></i></el-button>
          <el-button size="mini" type="success" plain v-if="scope.row.role !== '超级管理员'"
            @click="handleAllocation(scope.row)">分配角色 <i class="el-icon-finished"></i></el-button>
        </template>
      </el-table-column>
    </el-table>
    <!-- 分页 -->
    <div class="block" style="margin: 20px 0">
      <el-pagination :current-page="pageNum" :page-sizes="[6, 15, 30, 50]" :page-size="pageSize"
        layout="total, sizes, prev, pager, next, jumper" :total="pageTotal" @size-change="handleSizeChange"
@current-change="handleCurrentChange">
      </el-pagination>
    </div>
    <!-- 添加的dialog -->
    <el-dialog :close-on-click-modal="false" title="增加用户" :visible.sync="dialogAddVisible" width="500px">
      <el-form ref="form" status-icon :model="form" :rules="rules">
        <el-form-item label-position="right" label="用户名" label-width="100px" prop="username">
          <el-input v-model="form.username" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label-position="right" label="密码" label-width="100px" prop="password">
          <el-input v-model="form.password" type="password" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label-position="right"  label="确认密码" label-width="100px" prop="passwordAgain">
          <el-input v-model="form.passwordAgain" type="password" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label-position="right" label="昵称" label-width="100px" prop="nickname">
          <el-input v-model="form.nickname" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label-position="right" label="邮箱" label-width="100px" prop="email">
          <el-input v-model="form.email" autocomplete="off"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogAddVisible = false">取 消</el-button>
        <el-button type="primary" @click="addUser">确 定</el-button>
      </div>
    </el-dialog>
      <!-- 修改的dialog  -->
    <el-dialog :close-on-click-modal="false" title="更新用户信息" :visible.sync="dialogUpdateVisible" width="500px">
      <el-form ref="updateform"  status-icon :model="updateform" :rules="rules">
        <el-form-item label-position="right" label="用户名" label-width="100px" prop="username">
          <el-input disabled v-model="updateform.username" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label-position="right" label="昵称" label-width="100px" prop="nickname">
          <el-input v-model="updateform.nickname" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label-position="right" label="邮箱" label-width="100px" prop="email">
          <el-input v-model="updateform.email" autocomplete="off"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogUpdateVisible = false">取 消</el-button>
        <el-button type="primary" @click="updateUser">确 定</el-button>
      </div>
    </el-dialog>
    <el-dialog title="分配角色" :close-on-click-modal="false" :visible.sync="allocationVisible" width="400px">
      <el-select v-model="currentRole" placeholder="请选择">
        <el-option v-for="item in roles" :key="item.value" :label="item.label" :value="item.value">
        </el-option>
      </el-select>
      <span slot="footer" class="dialog-footer">
        <el-button @click="allocationVisible = false">取 消</el-button>
        <el-button type="primary" @click="allocation">确 定</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
export default {
  name: 'User',
  props: {
    user: Object
  },
  data () {
    // 自定义校验两次密码输入一致
    var validatePass = (rule, value, callback) => {
      if (value !== this.form.password) {
        callback(new Error('两次输入密码不一致!'))
      } else {
        callback()
      }
    }
    return {
      // url
      urlImport: this.$axios.defaults.baseURL + '/user/import',
      // 查询框的文本信息
      query: '',
      // 分页的属性，当前页数，页面显示的数据条数，总页数
      pageNum: 1,
      pageSize: 6,
      pageTotal: 1,
      // 表格数据
      tableData: [],
      // 表格数据加载效果
      loading: true,
      // 添加用户的dialog
      dialogAddVisible: false,
      form: {
        username: '',
        password: '',
        passwordAgain: '',
        nickname: '',
        email: '',
        role: '普通用户'
      },
      // 修改的属性的dialog
      dialogUpdateVisible: false,
      updateform: {
        id: '',
        nickname: '',
        email: ''
      },
      // 批量删除数据集
      multipleSelection: [],
      // 配置验证规则
      rules: {
        username: [
          { required: true, message: '请输入用户名', trigger: ['blur', 'change'] },
          { min: 3, max: 10, message: '长度在 3 到 10 个字符', trigger: ['blur', 'change'] }
        ],
        password: [
          { required: true, message: '请输入密码', trigger: ['blur', 'change'] },
          { min: 3, max: 10, message: '长度在 3 到 10 个字符', trigger: ['blur', 'change'] }
        ],
        passwordAgain: [
          { required: true, message: '请再次输入密码', trigger: ['blur', 'change'] },
          { validator: validatePass, trigger: ['blur', 'change'] }
        ],
        nickname: [
          { min: 2, max: 10, message: '长度在 2 到 10 个字符', trigger: ['blur', 'change'] }
        ],
        email: [
          { required: true, message: '请输入邮箱地址', trigger: ['blur', 'change'] },
          { type: 'email', message: '请输入正确的邮箱地址', trigger: ['blur', 'change'] }
        ],
        role: [
          { required: true, message: '请选择角色', trigger: ['blur', 'change'] }
        ]
      },
      roles: [],
      currentRole: '',
      currentUsername: '',
      allocationVisible: false
    }
  },
  methods: {
    // 导入数据提示
    lend () {
      this.$message('导入格式：用户名|密码|昵称|邮箱|角色|登录状态，只允许上传xlsx文件')
    },
    // 导入数据失败
    handleExcelImportError () {
      this.$message.error('请勿上传非xlsx文件')
    },
    // 导入数据成功
    handleExcelImportSuccess (data) {
      if (data.code === '500') {
        this.$message.error(data.msg)
      } else if (data.code === '200') {
        this.$message.success('导入成功！')
        this.getUserList()
      } else {
        this.$message.error('系统出现未知错误！')
      }
    },
    // 导出数据
    exp () {
      // url
      window.open(this.$axios.defaults.baseURL + '/user/export')
    },
    // 批量删除
    async batchDelete () {
      if (this.multipleSelection.length === 0) {
        return
      }
      try {
        await this.$confirm('此操作将永久批量删除用户, 是否继续?', '提示', { confirmButtonText: '确定', cancelButtonText: '取消', type: 'warning' })
        const usernames = this.multipleSelection.map(v => v.username)
        const res = await this.$axios.delete('user/batchDelete', {
          data: {
            usernames: usernames
          }
        })
        if (res === true) {
          this.$message.success('删除成功')
          if (this.tableData.length === this.multipleSelection.length && this.pageNum > 1) {
            this.pageNum--
          }
          this.getUserList()
        } else if (res === false) {
          this.$message.success('删除失败')
        }
      } catch (e) {}
    },
    // 批量选择
    handleSelectionChange (val) {
      this.multipleSelection = val
    },
    // 修改用户登陆状态
    async handleState (row) {
      try {
        const res = await this.$axios.patch(`user/state/${row.username}`, {
          data: {
            userState: row.state
          }
        })
        if (res !== true) {
          row.state = !row.state
        }
      } catch (e) {}
    },
    // 添加用户
    async addUser () {
      try {
        await this.$refs.form.validate()
        this.form.role = '普通用户'
        const res = await this.$axios.post('user', this.form)
        if (res === false) {
          this.$message.error('该用户名已被占用')
        } else if (res === true) {
          this.$message.success('添加成功')
          this.dialogAddVisible = false
          this.$refs.form.resetFields()
          this.getUserList()
        } else {}
      } catch (e) {}
    },
    // 每页显示多少条数据
    handleSizeChange (val) {
      this.pageSize = val
      this.getUserList()
    },
    // 当前页数
    handleCurrentChange (val) {
      this.pageNum = val
      this.getUserList()
    },
    // 获取用户列表
    getUserList () {
      this.$axios.get('user/page', {
        // get请求参数
        params: {
          pageNum: this.pageNum,
          pageSize: this.pageSize,
          username: this.query
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
    // 根据用户名模糊查询
    queryByUsername () {
      if (this.query === '') {
        return
      }
      this.pageNum = 1
      this.getUserList()
    },
    // 编辑用户信息
    handleEdit (row) {
      this.updateform.username = row.username
      this.updateform.nickname = row.nickname
      this.updateform.email = row.email
      this.dialogUpdateVisible = true
    },
    // 更新用户信息
    async updateUser () {
      try {
        await this.$refs.updateform.validate()
        const res = await this.$axios.put('user', this.updateform)
        if (res === false) {
          this.$message.error('修改失败')
        } else if ((res === true)) {
          this.$message.success('修改成功')
          this.dialogUpdateVisible = false
        } else {}
      } catch (e) {}
      for (let i = 0; i < this.tableData.length; i++) {
        if (this.tableData[i].username === this.updateform.username) {
          this.tableData[i].email = this.updateform.email
          this.tableData[i].nickname = this.updateform.nickname
          break
        }
      }
      var t = JSON.stringify(this.tableData)
      this.tableData = JSON.parse(t)
    },
    // 删除用户
    async handleDelete (username) {
      try {
        await this.$confirm('此操作将永久删除该用户, 是否继续?', '提示', { confirmButtonText: '确定', cancelButtonText: '取消', type: 'warning' })
        const res = await this.$axios.delete(`user/delete/${username}`)
        if (res === true) {
          this.$message.success('删除成功')
          if (this.tableData.length === 1 && this.pageNum > 1) {
            this.pageNum--
          }
          this.getUserList()
        } else if (res === false) {
          this.$message.success('删除失败')
        } else {}
      } catch (e) {}
    },
    // 分配角色页面显示
    handleAllocation (row) {
      this.currentRole = row.role
      this.currentUsername = row.username
      this.allocationVisible = true
    },
    async allocation () {
      this.allocationVisible = false
      const { code } = await this.$axios.patch(`user/allocationByUsername/${this.currentUsername}/${this.currentRole}`)
      if (code === '200') {
        for (let i = 0; i < this.tableData.length; i++) {
          if (this.tableData[i].username === this.currentUsername) {
            this.tableData[i].role = this.currentRole
            break
          }
        }
        this.$message.success('权限分配成功')
      }
    }
  },
  mounted () {
    this.getUserList()
  },
  async created () {
    const { code, data, msg } = await this.$axios.post('role/roles')
    if (code === '200') {
      data.forEach((item) => {
        this.roles.push({
          id: item.id,
          value: item.name,
          label: item.name
        })
      })
    } else {
      this.$message.error(msg)
    }
  }
}
</script>

<style lang="less">
.el-dialog .el-form-item {
  width: 300px;
  .el-input {
    width: 300px;
  }
}
</style>
