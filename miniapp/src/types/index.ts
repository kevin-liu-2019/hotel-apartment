export interface Hotel {
  id: number;
  name: string;
  address: string;
  phone: string;
  intro: string;
  coverImage: string;
  facilities: string[];
  latitude: string;
  longitude: string;
  minPrice: number;
  status: number;
  sortOrder: number;
  createdAt: string;
  updatedAt: string;
}

export interface Room {
  id: number;
  hotelId: number;
  name: string;
  price: number;
  area: number;
  floor: number;
  orientation: string;
  images: string[];
  stock: number;
  description: string;
  status: number;
}

export interface Order {
  id: number;
  orderNo: string;
  userId: number;
  hotelId: number;
  roomId: number;
  rentalType: number;
  startDate: string;
  endDate: string;
  totalPrice: number;
  status: number;
  payTime: string | null;
  transactionId: string | null;
  contractId: number | null;
  remark: string | null;
  createdAt: string;
  updatedAt: string;
}

export interface User {
  id: number;
  openid: string;
  phone: string;
  nickname: string;
  avatar: string;
  status: number;
}

export interface PageResult<T> {
  records: T[];
  total: number;
  size: number;
  current: number;
  pages: number;
}

export interface ApiResponse<T = any> {
  code: number;
  message: string;
  data: T;
  timestamp: number;
}

export enum RentalType {
  SHORT_RENT = 0,
  LONG_RENT = 1,
}

export enum OrderStatus {
  PENDING_SIGN = 0,
  PENDING_PAY = 1,
  PAID = 2,
  SIGNED = 3,
  IN_PROGRESS = 4,
  COMPLETED = 5,
  CANCELLED = 6,
}

export const OrderStatusText: Record<OrderStatus, string> = {
  [OrderStatus.PENDING_SIGN]: '待签约',
  [OrderStatus.PENDING_PAY]: '待支付',
  [OrderStatus.PAID]: '已支付',
  [OrderStatus.SIGNED]: '已签约',
  [OrderStatus.IN_PROGRESS]: '进行中',
  [OrderStatus.COMPLETED]: '已完成',
  [OrderStatus.CANCELLED]: '已取消',
};

export const RentalTypeText: Record<RentalType, string> = {
  [RentalType.SHORT_RENT]: '短租',
  [RentalType.LONG_RENT]: '长租',
};
