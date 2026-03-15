package com.hotel.apartment.admin.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hotel.apartment.admin.dto.RoomDto;
import com.hotel.apartment.common.exception.BusinessException;
import com.hotel.apartment.common.result.ResultCode;
import com.hotel.apartment.dal.entity.Room;
import com.hotel.apartment.dal.mapper.RoomMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AdminRoomService {

    private final RoomMapper roomMapper;

    public IPage<Room> list(Long hotelId, int page, int size) {
        LambdaQueryWrapper<Room> wrapper = new LambdaQueryWrapper<>();
        if (hotelId != null) {
            wrapper.eq(Room::getHotelId, hotelId);
        }
        return roomMapper.selectPage(new Page<>(page, size), wrapper);
    }

    public Room create(RoomDto dto) {
        Room room = new Room();
        BeanUtils.copyProperties(dto, room);
        roomMapper.insert(room);
        return room;
    }

    public Room update(Long id, RoomDto dto) {
        Room room = roomMapper.selectById(id);
        if (room == null) {
            throw new BusinessException(ResultCode.NOT_FOUND, "房间不存在");
        }
        BeanUtils.copyProperties(dto, room);
        room.setId(id);
        roomMapper.updateById(room);
        return room;
    }

    public void delete(Long id) {
        roomMapper.deleteById(id);
    }
}
