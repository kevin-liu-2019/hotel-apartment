package com.hotel.apartment.admin.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.hotel.apartment.admin.dto.HotelDto;
import com.hotel.apartment.admin.service.AdminHotelService;
import com.hotel.apartment.common.result.R;
import com.hotel.apartment.dal.entity.Hotel;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@Tag(name = "酒店管理")
@RestController
@RequestMapping("/admin/hotels")
@RequiredArgsConstructor
public class AdminHotelController {

    private final AdminHotelService adminHotelService;

    @Operation(summary = "获取酒店列表")
    @GetMapping
    public R<IPage<Hotel>> list(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size) {
        return R.ok(adminHotelService.list(page, size));
    }

    @Operation(summary = "创建酒店")
    @PostMapping
    public R<Hotel> create(@Valid @RequestBody HotelDto dto) {
        return R.ok(adminHotelService.create(dto));
    }

    @Operation(summary = "更新酒店")
    @PutMapping("/{id}")
    public R<Hotel> update(@PathVariable Long id, @Valid @RequestBody HotelDto dto) {
        return R.ok(adminHotelService.update(id, dto));
    }

    @Operation(summary = "删除酒店")
    @DeleteMapping("/{id}")
    public R<Void> delete(@PathVariable Long id) {
        adminHotelService.delete(id);
        return R.ok();
    }
}
