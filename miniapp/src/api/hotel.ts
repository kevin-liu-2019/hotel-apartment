import { get } from '../utils/request';
import { Hotel, Room, PageResult } from '../types';

export const hotelApi = {
  getList: (page = 1, size = 10) =>
    get<PageResult<Hotel>>('/api/hotels', { page, size }),

  getDetail: (id: number) =>
    get<Hotel>(`/api/hotels/${id}`),

  getRooms: (hotelId: number) =>
    get<Room[]>(`/api/hotels/${hotelId}/rooms`),
};
