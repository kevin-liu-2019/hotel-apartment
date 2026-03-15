package com.hotel.apartment.api.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.hotel.apartment.common.result.R;
import com.hotel.apartment.dal.entity.Order;
import com.hotel.apartment.service.dto.CreateOrderDto;
import com.hotel.apartment.service.service.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@Tag(name = "订单接口")
@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @Operation(summary = "创建订单")
    @PostMapping
    public R<Order> create(Authentication authentication, @Valid @RequestBody CreateOrderDto dto) {
        Long userId = (Long) authentication.getPrincipal();
        dto.setUserId(userId);
        return R.ok(orderService.createOrder(dto));
    }

    @Operation(summary = "获取订单详情")
    @GetMapping("/{orderNo}")
    public R<Order> detail(@PathVariable String orderNo) {
        return R.ok(orderService.getOrderDetail(orderNo));
    }

    @Operation(summary = "获取用户订单列表")
    @GetMapping
    public R<IPage<Order>> list(
            Authentication authentication,
            @RequestParam(required = false) Integer status,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size) {
        Long userId = (Long) authentication.getPrincipal();
        return R.ok(orderService.getUserOrders(userId, status, page, size));
    }

    @Operation(summary = "取消订单")
    @PostMapping("/{orderNo}/cancel")
    public R<Void> cancel(@PathVariable String orderNo) {
        orderService.cancelOrder(orderNo);
        return R.ok();
    }
}
