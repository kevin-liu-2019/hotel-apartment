import { get } from '../utils/request';
import { Room } from '../types';

export const roomApi = {
  getDetail: (id: number) =>
    get<Room>(`/api/rooms/${id}`),
};
