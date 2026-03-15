import { get, post, put } from '../utils/request';
import { User } from '../types';

export interface LoginVo {
  token: string;
  userId: number;
  nickname: string;
  avatar: string;
}

export const userApi = {
  login: (code: string) =>
    post<LoginVo>('/api/user/login', { code }),

  getInfo: () =>
    get<User>('/api/user/info'),

  updateInfo: (data: Partial<User>) =>
    put<void>('/api/user/info', data),
};
