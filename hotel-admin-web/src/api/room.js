import request from './request'

export const getRooms = (params) => request.get('/rooms', { params })
export const createRoom = (data) => request.post('/rooms', data)
export const updateRoom = (id, data) => request.put(`/rooms/${id}`, data)
export const deleteRoom = (id) => request.delete(`/rooms/${id}`)
