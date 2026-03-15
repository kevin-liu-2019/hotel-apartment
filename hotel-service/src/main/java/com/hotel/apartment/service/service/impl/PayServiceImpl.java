package com.hotel.apartment.service.service.impl;

import com.hotel.apartment.common.enums.OrderStatus;
import com.hotel.apartment.common.exception.BusinessException;
import com.hotel.apartment.common.result.ResultCode;
import com.hotel.apartment.dal.entity.Order;
import com.hotel.apartment.dal.mapper.OrderMapper;
import com.hotel.apartment.service.service.PayService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Slf4j
@Service
@RequiredArgsConstructor
public class PayServiceImpl implements PayService {

    private final OrderMapper orderMapper;

    @Override
    public String createPrepayOrder(String orderNo) {
        Order order = orderMapper.selectByOrderNo(orderNo);
        if (order == null) {
            throw new BusinessException(ResultCode.NOT_FOUND, "订单不存在");
        }
        if (order.getStatus() != OrderStatus.PENDING_PAY.getCode()) {
            throw new BusinessException(ResultCode.ORDER_STATUS_ERROR, "订单状态不正确");
        }
        // In a real scenario, call WeChat Pay API to get prepay_id
        // Here we return a placeholder
        log.info("Creating prepay order for orderNo: {}", orderNo);
        return "prepay_id_placeholder_" + orderNo;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void handlePayNotify(String notifyData) {
        // Parse notify data and update order
        log.info("Received pay notify: {}", notifyData);
        // In real scenario, parse WeChat Pay notification XML/JSON
        // and update order status
    }

    @Transactional(rollbackFor = Exception.class)
    public void markOrderPaid(String orderNo, String transactionId) {
        Order order = orderMapper.selectByOrderNo(orderNo);
        if (order == null) {
            throw new BusinessException(ResultCode.NOT_FOUND, "订单不存在");
        }
        order.setStatus(OrderStatus.PAID.getCode());
        order.setTransactionId(transactionId);
        order.setPayTime(LocalDateTime.now());
        orderMapper.updateById(order);
    }
}
