import { get, post } from '../utils/request';
import { Order, PageResult } from '../types';

export interface CreateOrderParams {
  hotelId: number;
  roomId: number;
  rentalType: number;
  startDate: string;
  endDate: string;
  remark?: string;
}

export const orderApi = {
  create: (data: CreateOrderParams) =>
    post<Order>('/api/orders', data),

  getDetail: (orderNo: string) =>
    get<Order>(`/api/orders/${orderNo}`),

  getList: (status?: number, page = 1, size = 10) =>
    get<PageResult<Order>>('/api/orders', { status, page, size }),

  cancel: (orderNo: string) =>
    post<void>(`/api/orders/${orderNo}/cancel`),

  prepay: (orderNo: string) =>
    post<string>(`/api/pay/prepay/${orderNo}`),
};
