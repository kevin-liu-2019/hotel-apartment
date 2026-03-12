import { defineStore } from 'pinia'
import { ref } from 'vue'
import { login as loginApi } from '@/api/auth'

export const useAuthStore = defineStore('auth', () => {
  const token = ref(localStorage.getItem('token') || '')
  const adminInfo = ref(null)

  const loginAction = async (username, password) => {
    const res = await loginApi({ username, password })
    token.value = res.data.token
    adminInfo.value = res.data
    localStorage.setItem('token', res.data.token)
  }

  const logout = () => {
    token.value = ''
    adminInfo.value = null
    localStorage.removeItem('token')
  }

  const isLoggedIn = () => !!token.value

  return { token, adminInfo, loginAction, logout, isLoggedIn }
})
