package com.hotel.apartment.dal.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hotel.apartment.dal.entity.Room;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface RoomMapper extends BaseMapper<Room> {

    int decrementStock(@Param("roomId") Long roomId);
}
