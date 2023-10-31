<template>
  <div>
    <el-container>
      <!--侧边栏-->
      <el-aside :width="asideWidth" style=" min-height: 100vh; background-color:#001529">
        <div style="height: 60px;color: white; display: flex; align-items: center; justify-content: center">
          <img src="@/assets/logo1.png" alt="" style="width: 40px; height: 40px">
          <span class="logo-title" v-show="!isCollapse">你好管理员</span>
        </div>

        <el-menu :collapse="isCollapse" :collapse-transition="false"  router background-color="#001529" text-color="rgba(255,255,255,0.65" active-text-color="#fff" style="border: none" :default-active="$route.path">
          <el-menu-item index="/manager/home">
              <i class="el-icon-s-home"></i>
              <span slot="title" >首页</span>
          </el-menu-item>
          <el-submenu index="/info">
            <template slot="title">
              <i class="el-icon-menu"></i>
              <span>信息管理</span>
            </template>
            <el-menu-item index="/manager/user">用户管理</el-menu-item>
            <el-menu-item index="/manager/business">商家管理</el-menu-item>
          </el-submenu>

        </el-menu>
      </el-aside>

      <el-container>
        <!--          头部区域-->
        <el-header>

          <i :class="collapseIcon" style="font-size: 26px" @click="handleCollapse"></i>
          <el-breadcrumb separator-class="el-icon-arrow-right" style="margin-left: 20px">
            <el-breadcrumb-item :to="{ path: '/manager' }">首页</el-breadcrumb-item>
            <el-breadcrumb-item :to="{ path: '/manager/user' }">用户管理</el-breadcrumb-item>
            <el-breadcrumb-item :to="{ path: '/manager/business' }">商家管理</el-breadcrumb-item>
          </el-breadcrumb>

          <div style="flex: 1; width: 0; display: flex; align-items: center; justify-content: flex-end">
            <i class="el-icon-quanping" style="font-size: 26px" @click="handleFull"></i>
            <el-dropdown placement="bottom">
              <div style="display: flex; align-items: center; cursor: default">
                <img src="@/assets/logo1.png" alt="" style="width: 40px; height: 40px; margin: 0 5px">
                <span>管理员</span>
              </div>
              <el-dropdown-menu slot="dropdown">
                <el-dropdown-item>个人信息</el-dropdown-item>
                <el-dropdown-item>修改密码</el-dropdown-item>
                <el-dropdown-item @click.native="$router.push('/Login')">退出登录</el-dropdown-item>
              </el-dropdown-menu>
            </el-dropdown>
          </div>


        </el-header>

        <!--          主体区域-->
        <el-main>
          <router-view />
        </el-main>

      </el-container>

    </el-container>
  </div>
</template>

<script>
import request from "@/Utils/request";
import axios from "axios";

export default {
  name: 'Manager',
  data(){
    return{
      isCollapse: false,
      asideWidth: '200px',
      collapseIcon: 'el-icon-s-fold',
      users:[]
    }
  },
  mounted() {
    axios.get('http://172.18.39.191:9090/manager/selectConsumerByPagination',{
      params: {
        page: '',
        pageSize: '',
        mobile: '',
        gender: ''
      }
    }).then(res => {
      console.log(res.data)
      this.users = res.data.data.rows;
    })
  },
  methods:{
    handleCollapse(){
      this.isCollapse = !this.isCollapse
      this.asideWidth = this.isCollapse ? '64px' : '200px'
      this.collapseIcon = this.isCollapse ? 'el-icon-s-unfold' : 'el-icon-s-fold'
    },
    handleFull(){
      document.documentElement.requestFullscreen()
    }


  }

}
</script>

<style>
.el-menu--inline {
  background-color: #000c17 !important;
}
.el-menu--inline .el-menu-item {
  background-color: #000c17 !important;
  padding-left: 49px !important;
}
.el-menu-item:hover, .el-submenu__title:hover {
  color: #fff !important;
}
.el-submenu__title:hover i {
  color: #fff !important;
}
.el-menu-item:hover i {
  color: #fff !important;
}
.el-menu-item.is-active {
  background-color: #1890ff !important;
  border-radius: 5px !important;
  width: calc(100% - 8px);
  margin-left: 4px;
}
.el-menu-item.is-active i, .el-menu-item.is-active .el-tooltip{
  margin-left: -4px;
}
.el-menu-item {
  height: 40px !important;
  line-height: 40px !important;
}
.el-submenu__title {
  height: 40px !important;
  line-height: 40px !important;
}
.el-submenu .el-menu-item {
  min-width: 0 !important;
}
.el-menu--inline .el-menu-item.is-active {
  padding-left: 45px !important;
}
/*.el-submenu__icon-arrow {*/
/*  margin-top: -5px;*/
/*}*/

.el-aside {
  transition: width .3s;
  box-shadow: 2px 0 6px rgba(0,21,41,.35);
}
.logo-title {
  margin-left: 5px;
  font-size: 20px;
  transition: all .3s;   /* 0.3s */
}
.el-header {
  box-shadow: 2px 0 6px rgba(0,21,41,.35);
  display: flex;
  align-items: center;
}
</style>
