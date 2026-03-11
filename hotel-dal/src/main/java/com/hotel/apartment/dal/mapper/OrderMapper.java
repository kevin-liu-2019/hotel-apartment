package com.hotel.apartment.dal.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hotel.apartment.dal.entity.Order;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface OrderMapper extends BaseMapper<Order> {

    Order selectByOrderNo(@Param("orderNo") String orderNo);
}
