<template>
  <div>
    <!-- 添加用户 -->
    <el-button type="success" plain icon="el-icon-add" @click="addRole">添加角色<i class="el-icon-circle-plus-outline el-icon--right"></i></el-button>
    <!-- 表格 -->
    <el-table :data="tableData" stripe style="width: 100%" v-loading="loading">
      <el-table-column type="expand">
        <template v-slot:default="{ row }">
          <span v-if="row.children.length === 0">当前角色没有任何的权限信息</span>
          <el-row class="l1" v-for="l1 in row.children" :key="l1.id">
            <el-col :span="4">
              <el-tag @close="delRight(row,l1.id)" closable>{{ l1.name }}</el-tag>&nbsp;
              <span class="el-icon-arrow-right"></span>
            </el-col>
            <el-col :span="20">
              <el-row class="l2" v-for="l2 in l1.children" :key="l2.id">
                <el-col :span="4">
                  <el-tag @close="delRight(row,l2.id)"  closable type="success">{{ l2.name }}</el-tag>&nbsp;
                  <span class="el-icon-arrow-right"></span>
                </el-col>
                <el-col :span="20">
                  <el-tag @close="delRight(row,l3.id)"  class="l3" closable type="warning" v-for="l3 in l2.children" :key="l3.id">{{ l3.name }}</el-tag>
                </el-col>
              </el-row>
            </el-col>
          </el-row>
        </template>
      </el-table-column>
      <el-table-column prop="name" label="角色名称">
      </el-table-column>
      <el-table-column prop="description" label="角色描述" >
      </el-table-column>
      <el-table-column label="操作">
        <template v-slot:default="{ row }">
          <el-button size="mini" plain @click="handleRole(row)">编辑<i class="el-icon-edit el-icon--right"></i>
          </el-button>
          <el-button size="mini" v-if="row.name !== '超级管理员'" type="danger" plain @click="handleDelete(row.id,$event)">删除<i class="el-icon-delete el-icon--right"></i>
          </el-button>
          <el-button size="mini" v-if="row.name !== '超级管理员'" type="success" plain  @click="showAssignDialog(row)">分配权限&nbsp; <i class="el-icon-check"></i>
          </el-button>
        </template>
      </el-table-column>
    </el-table>
    <!-- 树状权限 -->
    <el-dialog title="分配权限" :visible.sync="assignVisible" width="35%" :close-on-click-modal="false">
      <el-tree ref="tree" :data="treeData" show-checkbox :props="defaultProps"  node-key="id">
      </el-tree>
      <span slot="footer" class="dialog-footer">
        <el-button @click="assignVisible = false">取 消</el-button>
        <el-button type="primary" @click="assignRight">分 配</el-button>
      </span>
    </el-dialog>
    <!-- 编辑 -->
    <el-dialog :title="save" :visible.sync="saveVisible" width="35%" :close-on-click-modal="false">
      <el-form ref="form"  status-icon :model="form" :rules="rules">
        <el-form-item label-position="right" label="ID" label-width="100px" disabled v-show="showId" >
          <el-input style="width:150px" disabled v-model="form.roleId" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label-position="right" label="角色名称" label-width="100px" prop="roleName" >
          <el-input v-model="form.roleName" autocomplete="off" :disabled="!editName"></el-input>
        </el-form-item>
        <el-form-item label-position="right" label="角色描述" label-width="100px" prop="roleDesc">
          <el-input v-model="form.roleDesc" autocomplete="off"></el-input>
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button @click="saveVisible = false">取 消</el-button>
        <el-button type="primary" @click="saveRight">确 定</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
export default {
  name: 'Role',
  data () {
    return {
      // 查询框的文本信息
      query: '',
      // 权限分配dialog
      assignVisible: false,
      // 表格数据
      tableData: [],
      // 点击分配权限的角色id
      roleId: '',
      // 表格数据加载效果
      loading: true,
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
        roleName: [{ required: true, message: '请输入角色名称', trigger: ['blur', 'change'] }],
        roleDesc: [{ required: true, message: '请输入角色描述', trigger: ['blur', 'change'] }]
      },
      // 树状数据
      treeData: [],
      defaultProps: {
        children: 'children',
        label: 'name'
      },
      // 编辑角色
      form: {
        roleId: '',
        roleName: '',
        roleDesc: ''
      },
      // 编辑页面是否显示
      saveVisible: false,
      // 判断是否可以更改名称
      editName: true
    }
  },
  methods: {
    // 添加或者修改信息
    async saveRight () {
      try {
        await this.$refs.form.validate()
        const apiType = this.form.roleId === '' ? 'post' : 'put'
        const { code, msg } = await this.$axios[apiType]('role/updateRole', {
          data: this.form
        })
        if (code === '200') {
          this.$message.success('角色添加成功')
          this.saveVisible = false
          this.getRoleList()
        } else if (code !== '401') {
          this.$message.error(msg)
        }
      } catch (e) {}
    },
    // 显示编辑框
    async addRole () {
      this.saveVisible = true
      this.form.roleId = ''
      this.form.roleName = ''
      this.form.roleDesc = ''
      this.editName = true
      await this.$nextTick(() => {
        this.$refs.form.clearValidate()
      })
    },
    // 显示编辑权限信息
    handleRole (row) {
      this.saveVisible = true
      this.form.roleId = row.id
      this.form.roleName = row.name
      this.form.roleDesc = row.description
      this.editName = false
    },
    // 删除角色
    async handleDelete (roleId, event) {
      try {
        await this.$confirm('你确定要删除该角色吗？', '温馨提示', { type: 'warning' })
        const { code, msg } = await this.$axios.delete(`role/deleteRole/${roleId}`)
        if (code === '200') {
          this.$message.success('删除成功')
          this.tableData.splice(this.tableData.findIndex(item => item.id === roleId), 1)
        } else if (code !== '401') {
          this.$message.error(msg)
        }
      } catch (e) {} finally {
        if (event.target.nodeName === 'I') {
          event.target.parentNode.parentNode.blur()
        } else if (event.target.nodeName === 'SPAN') {
          event.target.parentNode.blur()
        } else {
          event.target.blur()
        }
      }
    },
    // 删除单个权限
    async delRight (role, rightId) {
      const { msg, code, data } = await this.$axios.delete(`role/role/${role.id}/right/${rightId}`)
      if (code === '200') {
        this.$notify({
          title: '成功',
          message: '成功删除权限',
          type: 'success'
        })
        role.children = data
      } else if (code !== '401') {
        this.$message.error(msg)
      }
    },
    // 分配权限
    async assignRight () {
      var ids = this.$refs.tree.getCheckedKeys()
      var halfs = this.$refs.tree.getHalfCheckedKeys()
      var rids = [...ids, ...halfs]
      const { code, msg, data } = await this.$axios.post(`role/setRoleRight/${this.roleId}`, rids)
      if (code === '200') {
        // 更新页面中的权限信息
        for (let i = 0; i < this.tableData.length; i++) {
          if (this.roleId === this.tableData[i].id) {
            this.tableData[i].children = data.children
            break
          }
        }
        this.treeData.children = data.children
        this.$message.success('权限分配成功')
        this.assignVisible = false
      } else if (code !== '401') {
        this.$message.error(msg)
      }
    },
    // 展示树形权限
    async showAssignDialog (row) {
      this.assignVisible = true
      this.roleId = row.id
      const { data } = await this.$axios.post('right/rights')
      this.treeData = data
      var ids = []
      // 防止有的功能不够三级权限
      row.children.forEach(l1 => {
        if (l1.children.length === 0) {
        } else {
          l1.children.forEach(l2 => {
            if (l2.children.length === 0) {
            } else {
              l2.children.forEach(l3 => {
                ids.push(l3.id)
              })
            }
          })
        }
      })
      this.$refs.tree.setCheckedKeys(ids)
    },
    // 获取角色列表
    getRoleList () {
      this.$axios.get('role/roleRights').then(res => {
        this.loading = false
        const { data, code } = res
        if (code === '200') {
          this.tableData = data
        }
      }).catch(res => {
        this.$message.error('请求超时', { duration: 2000 })
        this.loading = false
      })
    }
  },
  mounted () {
    this.getRoleList()
  },
  computed: {
    save: function () {
      return this.form.roleId === '' ? '添加角色' : '修改角色'
    },
    showId () {
      return !!this.form.roleId
    }
  }
}
</script>

<style lang="less" scoped>
.l1 {
  border-bottom: 1px dotted #ccc;
  padding: 5px 0;
}
.l2 {
  margin-bottom: 5px;
  &:last-child {
    margin-bottom: 0;
  }
  .l3 {
    margin-right: 5px;
    margin-bottom: 5px;
  }
}
</style>
