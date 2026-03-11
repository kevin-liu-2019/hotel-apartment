package com.hotel.apartment.service.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hotel.apartment.common.enums.OrderStatus;
import com.hotel.apartment.common.enums.RentalType;
import com.hotel.apartment.common.exception.BusinessException;
import com.hotel.apartment.common.result.ResultCode;
import com.hotel.apartment.dal.entity.Order;
import com.hotel.apartment.dal.entity.Room;
import com.hotel.apartment.dal.mapper.OrderMapper;
import com.hotel.apartment.dal.mapper.RoomMapper;
import com.hotel.apartment.service.dto.CreateOrderDto;
import com.hotel.apartment.service.service.OrderService;
import com.hotel.apartment.service.strategy.PriceStrategy;
import com.hotel.apartment.service.strategy.PriceStrategyFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.ThreadLocalRandom;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderMapper orderMapper;
    private final RoomMapper roomMapper;
    private final PriceStrategyFactory priceStrategyFactory;
    private final RabbitTemplate rabbitTemplate;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Order createOrder(CreateOrderDto dto) {
        // 1. Validate room availability
        Room room = roomMapper.selectById(dto.getRoomId());
        if (room == null || room.getStatus() != 1) {
            throw new BusinessException(ResultCode.ROOM_NOT_AVAILABLE);
        }
        if (room.getStock() == null || room.getStock() <= 0) {
            throw new BusinessException(ResultCode.ROOM_NOT_AVAILABLE, "房间库存不足");
        }

        // 2. Calculate price
        RentalType rentalType = RentalType.of(dto.getRentalType());
        PriceStrategy strategy = priceStrategyFactory.getStrategy(rentalType.name());
        BigDecimal totalPrice = strategy.calculate(room.getPrice(), dto.getStartDate(), dto.getEndDate());

        // 3. Create order
        Order order = new Order();
        order.setOrderNo(generateOrderNo());
        order.setUserId(dto.getUserId());
        order.setHotelId(dto.getHotelId());
        order.setRoomId(dto.getRoomId());
        order.setRentalType(dto.getRentalType());
        order.setStartDate(dto.getStartDate());
        order.setEndDate(dto.getEndDate());
        order.setTotalPrice(totalPrice);
        order.setStatus(OrderStatus.PENDING_SIGN.getCode());
        order.setRemark(dto.getRemark());
        orderMapper.insert(order);

        // 4. Decrement stock
        int updated = roomMapper.decrementStock(dto.getRoomId());
        if (updated == 0) {
            throw new BusinessException(ResultCode.ROOM_NOT_AVAILABLE, "库存扣减失败，请重试");
        }

        // 5. Send delayed message for order timeout
        try {
            rabbitTemplate.convertAndSend(
                    "order.exchange",
                    "order.timeout",
                    order.getOrderNo()
            );
        } catch (Exception e) {
            log.warn("Failed to send order timeout message for order {}: {}", order.getOrderNo(), e.getMessage());
        }

        return order;
    }

    @Override
    public Order getOrderDetail(String orderNo) {
        Order order = orderMapper.selectByOrderNo(orderNo);
        if (order == null) {
            throw new BusinessException(ResultCode.NOT_FOUND, "订单不存在");
        }
        return order;
    }

    @Override
    public IPage<Order> getUserOrders(Long userId, Integer status, int page, int size) {
        Page<Order> pageParam = new Page<>(page, size);
        LambdaQueryWrapper<Order> wrapper = new LambdaQueryWrapper<Order>()
                .eq(Order::getUserId, userId)
                .eq(status != null, Order::getStatus, status)
                .orderByDesc(Order::getCreatedAt);
        return orderMapper.selectPage(pageParam, wrapper);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void cancelOrder(String orderNo) {
        Order order = orderMapper.selectByOrderNo(orderNo);
        if (order == null) {
            throw new BusinessException(ResultCode.NOT_FOUND, "订单不存在");
        }
        int currentStatus = order.getStatus();
        if (currentStatus != OrderStatus.PENDING_SIGN.getCode()
                && currentStatus != OrderStatus.PENDING_PAY.getCode()) {
            throw new BusinessException(ResultCode.ORDER_STATUS_ERROR, "当前订单状态不允许取消");
        }
        order.setStatus(OrderStatus.CANCELLED.getCode());
        orderMapper.updateById(order);

        // Restore stock
        Room room = roomMapper.selectById(order.getRoomId());
        if (room != null) {
            room.setStock(room.getStock() + 1);
            roomMapper.updateById(room);
        }
    }

    private String generateOrderNo() {
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
        int random = ThreadLocalRandom.current().nextInt(1000, 9999);
        return timestamp + random;
    }
}
