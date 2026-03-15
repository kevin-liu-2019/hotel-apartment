package com.hotel.apartment.admin.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.hotel.apartment.admin.dto.RoomDto;
import com.hotel.apartment.admin.service.AdminRoomService;
import com.hotel.apartment.common.result.R;
import com.hotel.apartment.dal.entity.Room;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@Tag(name = "房间管理")
@RestController
@RequestMapping("/admin/rooms")
@RequiredArgsConstructor
public class AdminRoomController {

    private final AdminRoomService adminRoomService;

    @Operation(summary = "获取房间列表")
    @GetMapping
    public R<IPage<Room>> list(
            @RequestParam(required = false) Long hotelId,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size) {
        return R.ok(adminRoomService.list(hotelId, page, size));
    }

    @Operation(summary = "创建房间")
    @PostMapping
    public R<Room> create(@Valid @RequestBody RoomDto dto) {
        return R.ok(adminRoomService.create(dto));
    }

    @Operation(summary = "更新房间")
    @PutMapping("/{id}")
    public R<Room> update(@PathVariable Long id, @Valid @RequestBody RoomDto dto) {
        return R.ok(adminRoomService.update(id, dto));
    }

    @Operation(summary = "删除房间")
    @DeleteMapping("/{id}")
    public R<Void> delete(@PathVariable Long id) {
        adminRoomService.delete(id);
        return R.ok();
    }
}
