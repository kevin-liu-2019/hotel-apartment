package com.hotel.apartment.service.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hotel.apartment.common.exception.BusinessException;
import com.hotel.apartment.common.result.ResultCode;
import com.hotel.apartment.dal.entity.Hotel;
import com.hotel.apartment.dal.entity.Room;
import com.hotel.apartment.dal.mapper.HotelMapper;
import com.hotel.apartment.dal.mapper.RoomMapper;
import com.hotel.apartment.service.service.HotelService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class HotelServiceImpl implements HotelService {

    private final HotelMapper hotelMapper;
    private final RoomMapper roomMapper;

    @Override
    public IPage<Hotel> getHotelList(int page, int size) {
        Page<Hotel> pageParam = new Page<>(page, size);
        LambdaQueryWrapper<Hotel> wrapper = new LambdaQueryWrapper<Hotel>()
                .eq(Hotel::getStatus, 1)
                .orderByAsc(Hotel::getSortOrder)
                .orderByDesc(Hotel::getCreatedAt);
        return hotelMapper.selectPage(pageParam, wrapper);
    }

    @Override
    public Hotel getHotelDetail(Long id) {
        Hotel hotel = hotelMapper.selectById(id);
        if (hotel == null) {
            throw new BusinessException(ResultCode.NOT_FOUND, "酒店不存在");
        }
        return hotel;
    }

    @Override
    public List<Room> getHotelRooms(Long hotelId) {
        LambdaQueryWrapper<Room> wrapper = new LambdaQueryWrapper<Room>()
                .eq(Room::getHotelId, hotelId)
                .eq(Room::getStatus, 1)
                .orderByAsc(Room::getFloor);
        return roomMapper.selectList(wrapper);
    }
}
