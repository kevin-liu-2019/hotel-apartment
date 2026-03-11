package com.hotel.apartment.api.controller;

import com.hotel.apartment.common.result.R;
import com.hotel.apartment.dal.entity.Contract;
import com.hotel.apartment.service.service.ContractService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@Tag(name = "合同接口")
@RestController
@RequestMapping("/api/contracts")
@RequiredArgsConstructor
public class ContractController {

    private final ContractService contractService;

    @Operation(summary = "获取订单合同")
    @GetMapping("/order/{orderId}")
    public R<Contract> getByOrder(@PathVariable Long orderId) {
        return R.ok(contractService.getContractByOrderId(orderId));
    }
}
