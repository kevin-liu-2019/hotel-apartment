package com.hotel.apartment.dal.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hotel.apartment.dal.entity.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper extends BaseMapper<User> {
}
