package com.hotel.apartment.api.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.hotel.apartment.common.result.R;
import com.hotel.apartment.dal.entity.Hotel;
import com.hotel.apartment.dal.entity.Room;
import com.hotel.apartment.service.service.HotelService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "酒店接口")
@RestController
@RequestMapping("/api/hotels")
@RequiredArgsConstructor
public class HotelController {

    private final HotelService hotelService;

    @Operation(summary = "获取酒店列表")
    @GetMapping
    public R<IPage<Hotel>> list(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size) {
        return R.ok(hotelService.getHotelList(page, size));
    }

    @Operation(summary = "获取酒店详情")
    @GetMapping("/{id}")
    public R<Hotel> detail(@PathVariable Long id) {
        return R.ok(hotelService.getHotelDetail(id));
    }

    @Operation(summary = "获取酒店房间列表")
    @GetMapping("/{hotelId}/rooms")
    public R<List<Room>> rooms(@PathVariable Long hotelId) {
        return R.ok(hotelService.getHotelRooms(hotelId));
    }
}
