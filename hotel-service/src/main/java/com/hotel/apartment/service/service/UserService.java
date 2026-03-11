package com.hotel.apartment.service.service;

import com.hotel.apartment.dal.entity.User;
import com.hotel.apartment.service.dto.UpdateUserDto;
import com.hotel.apartment.service.dto.WxLoginDto;
import com.hotel.apartment.service.vo.LoginVo;

public interface UserService {

    LoginVo wxLogin(WxLoginDto dto);

    User getUserInfo(Long userId);

    void updateUserInfo(Long userId, UpdateUserDto dto);
}
