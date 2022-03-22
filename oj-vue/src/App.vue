<template>
  <div class="app">
    <!-- template用于指定组件的结构，一个组件必须要有一个根组件 -->
    <router-view></router-view>
  </div>
</template>

<script>
// 组件的配置，所有的配置都可以，丹斯不要写template
export default {
  metaInfo: {
    title: 'Online Judge'
  }
}
</script>

<style>
*{
  margin: 0;
  padding: 0;
  border: 0;
}
html,
body,
.app {
  width: 100%;
  height: 100%;
  text-decoration: none;
}
/**修改全局的滚动条*/
/**滚动条的宽度*/
::-webkit-scrollbar {
  width: 8px;
}
/* 滚动条样式 */
::-webkit-scrollbar-thumb {
  background-color: #eaecf1;
  border-radius: 3px;
}
/* 修改表格的滚动条 */
.el-table__body-wrapper::-webkit-scrollbar {
  width: 10px;
  height: 10px;
}
/* 滚动条的滑块 */
.el-table__body-wrapper::-webkit-scrollbar-thumb {
  background-color: #a1a3a9;
  border-radius: 3px;
}
</style>
