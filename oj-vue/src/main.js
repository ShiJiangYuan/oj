import Vue from 'vue'
import App from './App.vue'
import ElementUI from 'element-ui'
import 'element-ui/lib/theme-chalk/index.css'
import router from './router'
import './assets/icon/iconfont.css'
import axios from 'axios'
import store from './store'
// import { serverIp } from '../public/ipConfig'
import Meta from 'vue-meta'
import echarts from 'echarts'

Vue.use(Meta)
// 使用插件
Vue.use(ElementUI)

// 关闭控制台生产提示信息
Vue.config.productionTip = false

// 使用Vue原型绑定实例
Vue.prototype.$axios = axios
Vue.prototype.$echarts = echarts
// 配置axios的默认base_url
axios.defaults.baseURL = 'http://localhost:9090'
// axios.defaults.baseURL = 'http://101.43.208.178:9090'
// 配置请求拦截器
axios.interceptors.request.use(function (config) {
  // 配置请求头，获取token
  // config.headers.Authorization = localStorage.getItem('token')
  const user = localStorage.getItem('userInfo') ? JSON.parse(localStorage.getItem('userInfo')) : {}
  if (user) {
    config.headers.token = user.token
  }
  return config
}, function (error) {
  // 对请求错误做些什么
  return Promise.reject(error)
})
// 配置响应拦截器
axios.interceptors.response.use(function (response) {
  const res = response.data
  // 返回的是文件
  if (response.config.responseType === 'blob') {
    return res
  }
  if (res.code === '401') {
    ElementUI.Message.error(res.msg)
  }
  return res
}, function (error) {
  return Promise.reject(error)
})

new Vue({
  router,
  store,
  render: h => h(App)
}).$mount('#app')
