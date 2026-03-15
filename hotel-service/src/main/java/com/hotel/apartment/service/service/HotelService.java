package com.hotel.apartment.service.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.hotel.apartment.dal.entity.Hotel;
import com.hotel.apartment.dal.entity.Room;

import java.util.List;

public interface HotelService {

    IPage<Hotel> getHotelList(int page, int size);

    Hotel getHotelDetail(Long id);

    List<Room> getHotelRooms(Long hotelId);
}
