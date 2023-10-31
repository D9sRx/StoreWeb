import Vue from 'vue'
import VueRouter from 'vue-router'

Vue.use(VueRouter)

const routes = [
  {
    path: '/',
    name: 'blank',
    redirect: '/login'
  },
  {
    path: '/manager',
    name: 'Manager',
    component: () => import('../views/Manager.vue'),
    redirect:'/manager/home',
    children:[
      {path: 'home', name:'Home',component:() => import('../views/manager/Home.vue')},
      {path: 'user', name:'User',component:() => import('../views/manager/User.vue')},
      {path: 'business', name:'Business',component:() => import('../views/manager/Business.vue')},
    ]
  },
  {
    path: '/business_manager',
    name: 'BusinessManager',
    component: () => import('../views/BusinessManager.vue'),
    redirect:'/business_manager/business_home',
    children:[
      {path: 'business_home', name:'BusinessHome',component:() => import('../views/business/BusinessHome.vue')},
      {path: 'order', name:'Order',component:() => import('../views/business/Order.vue')},
      {path: 'store', name:'Store',component:() => import('../views/business/Store.vue')}
    ]
  },
  {
    path: '/login',
    name: 'Login',
    component: () => import('../views/Login.vue')
  },
  {
    path: '/register',
    name: 'Register',
    component: () => import('../views/Register.vue')
  },
  {
    path: '/shopping',
    name: 'ShoppingHome',
    component: () => import('../views/ShoppingHome.vue'),
    children: [
      { path: '/', name: 'Products', component: () => import('../views/shopping/Products.vue') },
      { path: 'user_order', name: 'UserOrder', component: () => import('../views/shopping/UserOrder.vue') }
    ]
  },
  {
    path: '/product/:id',
    name: 'ProductDetails',
    component: () => import('../views/ProductDetails.vue')
  }
]

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})

export default router
