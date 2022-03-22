<template>
<div>
  <el-table :data="tableData" style="width: 100%;margin-bottom: 20px;" row-key="id"
  :tree-props="{children: 'children'}" v-loading="loading">
    <el-table-column prop="id"  label="ID">
    </el-table-column>
    <el-table-column  prop="name"  label="名称">
    </el-table-column>
    <el-table-column prop="icon" label="ICON">
      <template v-slot:default="{ row }">
        <i :class="row.icon"></i>
      </template>
    </el-table-column>
    <el-table-column  prop="url"  label="url">
    </el-table-column>
    <el-table-column  prop="method"  label="请求方式">
    </el-table-column>
    <el-table-column  label="操作">
      <template v-slot:default="{ row }">
        <el-button v-show="row.children !== null" size="mini" type="success" plain  @click="editIcon(row)">更改图标&nbsp; <i class="el-icon-check"></i>
        </el-button>
        <el-button v-show="row.children === null" size="mini" plain  @click="editShow(row)">编辑&nbsp; <i class="el-icon-edit"></i>
        </el-button>
      </template>
    </el-table-column>
  </el-table>
  <el-dialog title="更新信息" :visible.sync="saveVisible" width="35%" :close-on-click-modal="false">
    <el-form ref="form"  status-icon :model="form">
      <el-form-item label-position="right" label="名称" label-width="100px">
        <el-input v-model="form.name" autocomplete="off"></el-input>
      </el-form-item>
      <el-form-item label-position="right" label="url" label-width="100px">
        <el-input v-model="form.url" autocomplete="off"></el-input>
      </el-form-item>
      <el-form-item label-position="right" label="请求方式" label-width="100px">
        <el-input v-model="form.method" autocomplete="off"></el-input>
      </el-form-item>
    </el-form>
    <span slot="footer" class="dialog-footer">
      <el-button @click="saveVisible = false">取 消</el-button>
      <el-button type="primary" @click="saveRight" :loading="loading">确 定</el-button>
    </span>
  </el-dialog>
</div>
</template>
<script>
export default {
  data () {
    return {
      tableData: [],
      loading: true,
      saveVisible: false,
      form: {
        url: '',
        method: '',
        id: '',
        name: ''
      }
    }
  },
  methods: {
    // 修改icon
    async editIcon (row) {
      try {
        const res = await this.$prompt('请输入新的icon', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消'
        })
        const { msg, code } = await this.$axios.patch(`right/icon/${row.id}`, {
          data: res.value
        })
        if (code === '200') {
          this.$message.success('icon更改成功')
          row.icon = res.value
        } else {
          this.$message.error(msg)
        }
      } catch (e) {}
    },
    // 显示编辑框
    editShow (row) {
      this.form.url = row.url
      this.form.method = row.method
      this.form.id = row.id
      this.form.name = row.name
      this.saveVisible = true
    },
    // 修改url和method
    async saveRight () {
      if (this.form.url === '' || this.form.method === '' || this.form.name === '') {
        this.$message.error('输入不能为空')
        return
      }
      this.form.method = this.form.method.toUpperCase()
      const { code, msg } = await this.$axios.patch(`right/updateUM/${this.form.id}/${this.form.method}/${this.form.name}`, {
        url: this.form.url
      })
      if (code === '200') {
        this.$message.success('修改成功')
        this.saveVisible = false
        for (let i = 0; i < this.tableData.length; i++) {
          for (let j = 0; j < this.tableData[i].children.length; j++) {
            for (let k = 0; k < this.tableData[i].children[j].children.length; k++) {
              if (this.tableData[i].children[j].children[k].id === this.form.id) {
                this.tableData[i].children[j].children[k].name = this.form.name
                this.tableData[i].children[j].children[k].url = this.form.url
                this.tableData[i].children[j].children[k].method = this.form.method.toUpperCase()
                break
              }
            }
          }
        }
      } else if (code !== '401') {
        this.saveVisible = false
        this.$message.error(msg)
      }
      var t = JSON.stringify(this.tableData)
      this.tableData = JSON.parse(t)
    }
  },
  async mounted () {
    const { msg, code, data } = await this.$axios.post('right/rights')
    this.loading = false
    if (code === '200') {
      this.tableData = data
    } else if (code !== '401') {
      this.$message.error(msg)
    }
  }
}
</script>
