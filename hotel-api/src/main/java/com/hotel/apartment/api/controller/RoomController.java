package com.hotel.apartment.api.controller;

import com.hotel.apartment.common.result.R;
import com.hotel.apartment.dal.entity.Room;
import com.hotel.apartment.service.service.RoomService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@Tag(name = "房间接口")
@RestController
@RequestMapping("/api/rooms")
@RequiredArgsConstructor
public class RoomController {

    private final RoomService roomService;

    @Operation(summary = "获取房间详情")
    @GetMapping("/{id}")
    public R<Room> detail(@PathVariable Long id) {
        return R.ok(roomService.getRoomDetail(id));
    }
}
