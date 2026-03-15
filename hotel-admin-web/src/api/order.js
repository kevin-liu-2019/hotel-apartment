import request from './request'

export const getOrders = (params) => request.get('/orders', { params })
