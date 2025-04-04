import { createRouter, createWebHistory } from 'vue-router'
 import DefaultLayout from "@/layout/DefaultLayout.vue";

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/',
      name: 'Layout',
      component:  DefaultLayout,
      redirect: '/dashboard',
      hidden:true,
      children: [
        {
          path: 'dashboard',
          name: 'dashboard',
          component: () => import('@/views/dashboard.vue'),
        },]
    },
  ],
})

export default router
