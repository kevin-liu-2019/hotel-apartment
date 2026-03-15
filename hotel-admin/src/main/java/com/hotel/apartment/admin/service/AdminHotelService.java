package com.hotel.apartment.admin.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hotel.apartment.admin.dto.HotelDto;
import com.hotel.apartment.common.exception.BusinessException;
import com.hotel.apartment.common.result.ResultCode;
import com.hotel.apartment.dal.entity.Hotel;
import com.hotel.apartment.dal.mapper.HotelMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AdminHotelService {

    private final HotelMapper hotelMapper;

    public IPage<Hotel> list(int page, int size) {
        return hotelMapper.selectPage(new Page<>(page, size), null);
    }

    public Hotel create(HotelDto dto) {
        Hotel hotel = new Hotel();
        BeanUtils.copyProperties(dto, hotel);
        hotelMapper.insert(hotel);
        return hotel;
    }

    public Hotel update(Long id, HotelDto dto) {
        Hotel hotel = hotelMapper.selectById(id);
        if (hotel == null) {
            throw new BusinessException(ResultCode.NOT_FOUND, "酒店不存在");
        }
        BeanUtils.copyProperties(dto, hotel);
        hotel.setId(id);
        hotelMapper.updateById(hotel);
        return hotel;
    }

    public void delete(Long id) {
        hotelMapper.deleteById(id);
    }
}
