<template>
  <div class="error-block">
    <h1><a href="#logo" class="brand-logo">Fixed Error</a></h1>
    <div class="grids-2">
      <div class="right-grid">
        <h2>404</h2>
        <h3>page not found</h3>
        <a href="/index" class="home btn"> Back to home </a>
      </div>
    </div>
    <!-- copyright -->
    <div class="copyright text-center">
      <div id="bottom">
        <p ref="copyright"></p>
        <a href="https://beian.miit.gov.cn/" target="_blank">蒙ICP备2020004885号-1</a>
      </div>
    </div>
    <!-- //copyright -->
  </div>
</template>

<script>
export default {
  mounted () {
    // 自动更新版权的时间
    this.$refs.copyright.innerText = `COPYRIGHT © 2022-${(new Date()).getFullYear()} ShiJiangYuan. ALL RIGHTS RESERVED.`
  }
}
</script>

<style>
@import '../../assets/404/css/style.css';
@import '../../assets/404/css/fontAwesome.css';
@import url('https://fonts.googleapis.com/css?family=Poppins:400,700,900&display=swap');
#bottom {
  width: 520px;
  color: white;
  text-align: center;
  font-size: 12px;
  max-width: 100%;
  position: absolute;
  left: 50%;
  transform: translateX(-50%);
  bottom: 20px;
}
</style>
