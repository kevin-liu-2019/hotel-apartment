package com.hotel.apartment.service.service.impl;

import com.hotel.apartment.common.exception.BusinessException;
import com.hotel.apartment.common.result.ResultCode;
import com.hotel.apartment.dal.entity.Room;
import com.hotel.apartment.dal.mapper.RoomMapper;
import com.hotel.apartment.service.service.RoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RoomServiceImpl implements RoomService {

    private final RoomMapper roomMapper;

    @Override
    public Room getRoomDetail(Long id) {
        Room room = roomMapper.selectById(id);
        if (room == null) {
            throw new BusinessException(ResultCode.NOT_FOUND, "房间不存在");
        }
        return room;
    }
}
