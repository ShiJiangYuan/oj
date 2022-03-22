<template>
  <!-- coming soon -->
<section class="w3l-coming-soon-page">
  <div class="coming-page-info">
    <div class="wrapper">
      <div class="logo-center">
        <a class="logo" href="index.html">
          <span></span> Online Judge
        </a>
      </div>
      <div class="coming-block">
        <h1>Coming Soon</h1>
        <h4 style="margin-top:20px">Our website is under construction now</h4>
        <p>We are working very hard to give you the best
          experience with this one.</p>
      </div>
      <!-- copyright -->
      <div class="copyright-footer">
        <div class="w3l-copy-right">
          <p ref="copyright"></p>
        </div>
      </div>
      <!-- //copyright -->
    </div>
  </div>
</section>
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
@import '../../assets/500/css/style.css';
@import '../../assets/500/css/font-awesome.css';
@import url('https://fonts.googleapis.com/css2?family=Kanit:wght@300;400;700&display=swap');
</style>
