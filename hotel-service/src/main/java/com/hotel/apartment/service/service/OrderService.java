package com.hotel.apartment.service.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.hotel.apartment.dal.entity.Order;
import com.hotel.apartment.service.dto.CreateOrderDto;

public interface OrderService {

    Order createOrder(CreateOrderDto dto);

    Order getOrderDetail(String orderNo);

    IPage<Order> getUserOrders(Long userId, Integer status, int page, int size);

    void cancelOrder(String orderNo);
}
