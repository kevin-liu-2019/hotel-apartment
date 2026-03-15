import Taro from '@tarojs/taro';

export const storage = {
  setToken: (token: string) => Taro.setStorageSync('token', token),
  getToken: (): string => Taro.getStorageSync('token') || '',
  removeToken: () => Taro.removeStorageSync('token'),
  setUserId: (id: number) => Taro.setStorageSync('userId', id),
  getUserId: (): number => Taro.getStorageSync('userId') || 0,
  isLoggedIn: (): boolean => !!Taro.getStorageSync('token'),
};
