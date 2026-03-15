import { useState, useEffect } from 'react';
import Taro from '@tarojs/taro';
import { View, Text, Image, Button } from '@tarojs/components';
import { User } from '../../types';
import { userApi } from '../../api/user';
import { storage } from '../../utils/storage';
import './index.scss';

export default function MyPage() {
  const [user, setUser] = useState<User | null>(null);
  const [loading, setLoading] = useState(false);

  useEffect(() => {
    if (storage.isLoggedIn()) {
      loadUserInfo();
    }
  }, []);

  const loadUserInfo = async () => {
    try {
      const info = await userApi.getInfo();
      setUser(info);
    } catch (e) {
      console.error('Failed to load user info', e);
    }
  };

  const handleLogin = () => {
    setLoading(true);
    Taro.login({
      success: async ({ code }) => {
        try {
          const result = await userApi.login(code);
          storage.setToken(result.token);
          storage.setUserId(result.userId);
          await loadUserInfo();
          Taro.showToast({ title: '登录成功', icon: 'success' });
        } catch (e) {
          console.error('Login failed', e);
        } finally {
          setLoading(false);
        }
      },
      fail: () => {
        setLoading(false);
        Taro.showToast({ title: '登录失败', icon: 'none' });
      },
    });
  };

  const handleLogout = async () => {
    const res = await Taro.showModal({ title: '提示', content: '确定要退出登录吗？' });
    if (res.confirm) {
      storage.removeToken();
      setUser(null);
      Taro.showToast({ title: '已退出', icon: 'success' });
    }
  };

  const goToOrders = () => {
    Taro.switchTab({ url: '/pages/order-list/index' });
  };

  return (
    <View className="my-page">
      {/* User Info */}
      <View className="user-section">
        {user ? (
          <View className="user-info">
            <Image
              className="avatar"
              src={user.avatar || 'https://via.placeholder.com/80'}
              mode="aspectFill"
            />
            <View className="user-detail">
              <Text className="nickname">{user.nickname || '用户'}</Text>
              <Text className="phone">{user.phone || '未绑定手机号'}</Text>
            </View>
          </View>
        ) : (
          <View className="login-area">
            <Text className="login-hint">登录后享受更多服务</Text>
            <Button
              className="login-btn"
              loading={loading}
              onClick={handleLogin}
            >
              微信一键登录
            </Button>
          </View>
        )}
      </View>

      {/* Menu Items */}
      <View className="menu-section">
        <View className="menu-item" onClick={goToOrders}>
          <Text className="menu-icon">📋</Text>
          <Text className="menu-label">我的订单</Text>
          <Text className="menu-arrow">›</Text>
        </View>
        <View className="menu-item">
          <Text className="menu-icon">📄</Text>
          <Text className="menu-label">我的合同</Text>
          <Text className="menu-arrow">›</Text>
        </View>
        <View className="menu-item">
          <Text className="menu-icon">💰</Text>
          <Text className="menu-label">分销佣金</Text>
          <Text className="menu-arrow">›</Text>
        </View>
        <View className="menu-item">
          <Text className="menu-icon">❓</Text>
          <Text className="menu-label">帮助中心</Text>
          <Text className="menu-arrow">›</Text>
        </View>
        <View className="menu-item">
          <Text className="menu-icon">📞</Text>
          <Text className="menu-label">联系客服</Text>
          <Text className="menu-arrow">›</Text>
        </View>
      </View>

      {/* Logout */}
      {user && (
        <View className="logout-section" onClick={handleLogout}>
          <Text className="logout-text">退出登录</Text>
        </View>
      )}
    </View>
  );
}
