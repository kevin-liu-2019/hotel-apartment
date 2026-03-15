package com.hotel.apartment.dal.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hotel.apartment.dal.entity.Hotel;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface HotelMapper extends BaseMapper<Hotel> {
}
