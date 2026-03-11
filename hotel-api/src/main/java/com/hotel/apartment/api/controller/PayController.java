package com.hotel.apartment.api.controller;

import com.hotel.apartment.common.result.R;
import com.hotel.apartment.service.service.PayService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@Tag(name = "支付接口")
@RestController
@RequestMapping("/api/pay")
@RequiredArgsConstructor
public class PayController {

    private final PayService payService;

    @Operation(summary = "创建预支付订单")
    @PostMapping("/prepay/{orderNo}")
    public R<String> prepay(@PathVariable String orderNo) {
        return R.ok(payService.createPrepayOrder(orderNo));
    }

    @Operation(summary = "微信支付回调")
    @PostMapping("/notify")
    public String notify(@RequestBody String notifyData) {
        payService.handlePayNotify(notifyData);
        return "SUCCESS";
    }
}
