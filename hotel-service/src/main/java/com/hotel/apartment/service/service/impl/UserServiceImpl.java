package com.hotel.apartment.service.service.impl;

import cn.binarywang.wx.miniapp.api.WxMaService;
import cn.binarywang.wx.miniapp.bean.WxMaJscode2SessionResult;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.hotel.apartment.common.exception.BusinessException;
import com.hotel.apartment.common.result.ResultCode;
import com.hotel.apartment.common.utils.JwtUtil;
import com.hotel.apartment.dal.entity.User;
import com.hotel.apartment.dal.mapper.UserMapper;
import com.hotel.apartment.service.dto.UpdateUserDto;
import com.hotel.apartment.service.dto.WxLoginDto;
import com.hotel.apartment.service.service.UserService;
import com.hotel.apartment.service.vo.LoginVo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.error.WxErrorException;
import org.springframework.stereotype.Service;

import java.util.concurrent.ThreadLocalRandom;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final WxMaService wxMaService;
    private final UserMapper userMapper;
    private final JwtUtil jwtUtil;

    @Override
    public LoginVo wxLogin(WxLoginDto dto) {
        WxMaJscode2SessionResult sessionResult;
        try {
            sessionResult = wxMaService.getUserService().getSessionInfo(dto.getCode());
        } catch (WxErrorException e) {
            log.error("WeChat login failed", e);
            throw new BusinessException(ResultCode.SYSTEM_ERROR, "微信登录失败: " + e.getError().getErrorMsg());
        }

        String openid = sessionResult.getOpenid();
        User user = userMapper.selectOne(
                new LambdaQueryWrapper<User>().eq(User::getOpenid, openid)
        );

        if (user == null) {
            user = new User();
            user.setOpenid(openid);
            user.setUnionid(sessionResult.getUnionid());
            user.setStatus(1);
            user.setNickname("用户" + (100000 + ThreadLocalRandom.current().nextInt(900000)));
            userMapper.insert(user);
        }

        String token = jwtUtil.generateToken(user.getId());

        return LoginVo.builder()
                .token(token)
                .userId(user.getId())
                .nickname(user.getNickname())
                .avatar(user.getAvatar())
                .build();
    }

    @Override
    public User getUserInfo(Long userId) {
        User user = userMapper.selectById(userId);
        if (user == null) {
            throw new BusinessException(ResultCode.NOT_FOUND, "用户不存在");
        }
        return user;
    }

    @Override
    public void updateUserInfo(Long userId, UpdateUserDto dto) {
        User user = userMapper.selectById(userId);
        if (user == null) {
            throw new BusinessException(ResultCode.NOT_FOUND, "用户不存在");
        }
        if (dto.getNickname() != null) {
            user.setNickname(dto.getNickname());
        }
        if (dto.getAvatar() != null) {
            user.setAvatar(dto.getAvatar());
        }
        if (dto.getPhone() != null) {
            user.setPhone(dto.getPhone());
        }
        userMapper.updateById(user);
    }
}
