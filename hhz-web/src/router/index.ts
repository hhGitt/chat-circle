import { createRouter, createWebHashHistory } from 'vue-router'
import { UserStore } from '@/stores'

const router = createRouter({
  history: createWebHashHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/login',
      name: 'login',
      component: () => import('@/views/Login.vue')
    },
    {
      path: '/register',
      name: 'register',
      component: () => import('@/views/Register.vue')
    },
    {
      path: '/',
      name: '首页',
      component: () => import('@/layouts/index.vue'),
      redirect: '/home',
      children: [
        {
          path: '/home',
          name: 'home',
          component: () => import('@/views/Home.vue')
        },
        {
          path: '/chat',
          name: 'chat',
          component: () => import('@/views/chat_room/Main.vue'),
          // redirect: '/chat/chatList/0',
          // children: [
          //   {
          //     path: 'chatList/:chatType',
          //     name: 'chatList',
          //     component: () => import('@/views/chat_room/chatList.vue')
          //   },
          //   {
          //     path: 'chatPage/:chatType/:chatId',
          //     name: 'chatPage',
          //     component: () => import('@/views/chat_room/chatPage.vue')
          //   },
          // ]
        },
        {
          path: '/personalCenter',
          name: 'personalCenter',
          redirect: '/info',
          component: () => import('@/views/personal_center/Main.vue'),
          children: [
            {
              path: '/info',
              name: 'p_info',
              component: () => import('@/views/personal_center/info.vue')
            },
            {
              path: '/account',
              name: 'p_account',
              component: () => import('@/views/personal_center/account.vue')
            },
          ]
        }
      ]
    },

    {
      path: '/:pathMatch(.*)*',
      name: 'notFound',
      component: () => import('@/views/notFound.vue')
    }
  ]
})
router.beforeEach(async (to, from, next) => {

  // 1.如果访问登录页,直接过
  if (to.path == '/login' || to.path == '/register') return next();

  // 2.如果没有token,重定向到login
  if (!UserStore.token) {
    return next({ path: '/login', replace: true })
  }

  // 3.如果没有菜单列表，就重新请求菜单列表并添加动态路由
  // 4.正常访问页面
  next();
})
export default router


