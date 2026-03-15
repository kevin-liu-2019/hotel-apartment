package com.hotel.apartment.admin.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.hotel.apartment.admin.service.AdminOrderService;
import com.hotel.apartment.common.result.R;
import com.hotel.apartment.dal.entity.Order;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@Tag(name = "订单管理")
@RestController
@RequestMapping("/admin/orders")
@RequiredArgsConstructor
public class AdminOrderController {

    private final AdminOrderService adminOrderService;

    @Operation(summary = "获取订单列表")
    @GetMapping
    public R<IPage<Order>> list(
            @RequestParam(required = false) Long hotelId,
            @RequestParam(required = false) Integer status,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size) {
        return R.ok(adminOrderService.list(hotelId, status, page, size));
    }
}
