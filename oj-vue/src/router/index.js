import VueRouter from 'vue-router'
import Vue from 'vue'
import store from '@/store'

Vue.use(VueRouter)

const routes = [{
  path: '/login',
  component: () => import('../views/login/Login.vue'),
  name: '登录'
},
{
  path: '/register',
  component: () => import('../views/login/Register.vue'),
  name: '注册'
},
{
  path: '/forgot',
  component: () => import('../views/login/ForgotPassword.vue'),
  name: '忘记密码'
},
{
  path: '/activation',
  component: () => import('../views/login/ActivationAccount.vue'),
  name: '激活'
},
{
  path: '/running/:id',
  name: '正在进行的考试',
  component: () => import('../views/contest/runningContest.vue')
},
{
  path: '/finished/:id/:username',
  name: '已经结束的考试',
  component: () => import('../views/contest/finishedContest.vue')
},
{
  path: '/analyse/:id',
  name: '成绩分析',
  component: () => import('../views/exam/analyse.vue')
},
{
  path: '/',
  component: () => import('../views/Manage.vue'),
  name: 'Manage',
  redirect: '/index',
  children: [
    { path: 'index', name: '首页', component: () => import('../views/index/Index.vue') },
    { path: 'person', name: '个人信息', component: () => import('../views/user/PersonInfo.vue') },
    { path: 'user', name: '系统管理/用户管理', component: () => import('../views/user/User.vue') },
    { path: 'roles', name: '权限管理/角色列表', component: () => import('../views/right/Role.vue') },
    { path: 'rights', name: '权限管理/权限列表', component: () => import('../views/right/Right.vue') },
    { path: 'radio', name: '题库管理/单选题题库', component: () => import('../views/bank/singleChoice.vue') },
    { path: 'judge', name: '题库管理/判断题题库', component: () => import('../views/bank/judge.vue') },
    { path: 'fill', name: '题库管理/填空题题库', component: () => import('../views/bank/fill.vue') },
    { path: 'programme', name: '题库管理/简答题题库', component: () => import('../views/bank/programme.vue') },
    { path: 'exam', name: '考试管理/创建试卷', component: () => import('../views/exam/exam.vue') },
    { path: 'exams', name: '考试管理/试卷管理', component: () => import('../views/exam/exams.vue') },
    { path: 'statistics', name: '考试管理 / 成绩统计', component: () => import('../views/exam/statistics.vue') },
    { path: 'contest', name: '考试中心 / 我的考试', component: () => import('../views/contest/contest.vue') }
  ]
},
{
  path: '/404',
  component: () => import('../views/error/404.vue'),
  name: '404'
},
{
  path: '/500',
  component: () => import('../views/error/500.vue'),
  name: '500'
}
]

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes: routes
})

router.beforeEach((to, from, next) => {
  const token = localStorage.getItem('userInfo') ? JSON.parse(localStorage.getItem('userInfo')) : {}
  // 设置当前路由名称，为了在Header组件中去使用
  localStorage.setItem('currentPathName', to.name)
  // 触发数据更新
  store.commit('setPath')
  if ((token && token.token) || to.path === '/login' || to.path === '/register' || to.path === '/forgot' || to.path === '/activation' || to.path === '/500') {
    // exams跳转到exam会带参数，防止点击创建试卷再进行跳转
    if (to.path !== from.path) {
      // 判断此跳转路由的来源路由是否存在，存在的情况跳转到来源路由，否则跳转到404页面
      if (to.matched.length === 0) {
        if (to.path.slice(0, 8) === '/running' && to.path.length !== 8) {
          next()
        } else {
          next('/404')
        }
      } else {
        next()
      }
    }
  } else {
    next({ name: '登录' })
  }
})

export default router
