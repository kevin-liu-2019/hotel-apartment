import Taro from '@tarojs/taro';
import { ApiResponse } from '../types';

const BASE_URL = 'https://api.example.com';

const getToken = (): string => {
  return Taro.getStorageSync('token') || '';
};

export async function request<T = any>(
  url: string,
  method: 'GET' | 'POST' | 'PUT' | 'DELETE' = 'GET',
  data?: any
): Promise<T> {
  const token = getToken();
  const headers: Record<string, string> = {
    'Content-Type': 'application/json',
  };
  if (token) {
    headers['Authorization'] = `Bearer ${token}`;
  }

  return new Promise((resolve, reject) => {
    Taro.request({
      url: `${BASE_URL}${url}`,
      method,
      data,
      header: headers,
      success: (res) => {
        const response = res.data as ApiResponse<T>;
        if (response.code === 200) {
          resolve(response.data);
        } else if (response.code === 401) {
          Taro.removeStorageSync('token');
          Taro.navigateTo({ url: '/pages/my/index' });
          reject(new Error(response.message));
        } else {
          Taro.showToast({ title: response.message || '请求失败', icon: 'none' });
          reject(new Error(response.message));
        }
      },
      fail: (err) => {
        Taro.showToast({ title: '网络错误，请稍后重试', icon: 'none' });
        reject(err);
      },
    });
  });
}

export const get = <T>(url: string, params?: Record<string, any>) => {
  const queryString = params
    ? '?' + Object.entries(params)
        .filter(([, v]) => v !== undefined && v !== null)
        .map(([k, v]) => `${k}=${encodeURIComponent(v)}`)
        .join('&')
    : '';
  return request<T>(`${url}${queryString}`, 'GET');
};

export const post = <T>(url: string, data?: any) => request<T>(url, 'POST', data);
export const put = <T>(url: string, data?: any) => request<T>(url, 'PUT', data);
export const del = <T>(url: string) => request<T>(url, 'DELETE');
