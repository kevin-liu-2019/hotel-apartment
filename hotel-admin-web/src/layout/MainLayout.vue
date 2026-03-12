<template>
  <el-container class="layout-wrapper">
    <el-aside width="200px" class="sidebar">
      <div class="logo">🏨 酒店公寓管理</div>
      <el-menu
        :default-active="activeMenu"
        background-color="#001529"
        text-color="#c0c4cc"
        active-text-color="#409eff"
        router
      >
        <el-menu-item index="/hotels">
          <el-icon><OfficeBuilding /></el-icon>
          <span>酒店管理</span>
        </el-menu-item>
        <el-menu-item index="/rooms">
          <el-icon><House /></el-icon>
          <span>房间管理</span>
        </el-menu-item>
        <el-menu-item index="/orders">
          <el-icon><List /></el-icon>
          <span>订单管理</span>
        </el-menu-item>
      </el-menu>
    </el-aside>

    <el-container>
      <el-header class="header">
        <div class="header-right">
          <el-button type="danger" size="small" @click="handleLogout">退出登录</el-button>
        </div>
      </el-header>
      <el-main>
        <router-view />
      </el-main>
    </el-container>
  </el-container>
</template>

<script setup>
import { computed } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import { OfficeBuilding, House, List } from '@element-plus/icons-vue'
import { useAuthStore } from '@/stores/auth'

const route = useRoute()
const router = useRouter()
const authStore = useAuthStore()

const activeMenu = computed(() => route.path)

const handleLogout = () => {
  authStore.logout()
  router.push('/login')
}
</script>

<style scoped>
.layout-wrapper {
  height: 100vh;
}
.sidebar {
  background-color: #001529;
  overflow: hidden;
}
.logo {
  height: 60px;
  line-height: 60px;
  text-align: center;
  color: #fff;
  font-size: 16px;
  font-weight: 600;
  border-bottom: 1px solid #0d2137;
}
.header {
  background: #fff;
  border-bottom: 1px solid #ebeef5;
  display: flex;
  align-items: center;
  justify-content: flex-end;
  padding: 0 20px;
}
.header-right {
  display: flex;
  align-items: center;
  gap: 12px;
}
</style>
