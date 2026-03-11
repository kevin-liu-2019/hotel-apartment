package com.hotel.apartment.admin.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.hotel.apartment.admin.dto.AdminLoginDto;
import com.hotel.apartment.admin.entity.Admin;
import com.hotel.apartment.admin.mapper.AdminMapper;
import com.hotel.apartment.common.exception.BusinessException;
import com.hotel.apartment.common.result.ResultCode;
import com.hotel.apartment.common.utils.JwtUtil;
import com.hotel.apartment.service.vo.LoginVo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AdminAuthService {

    private final AdminMapper adminMapper;
    private final JwtUtil jwtUtil;
    private final BCryptPasswordEncoder passwordEncoder;

    public LoginVo login(AdminLoginDto dto) {
        Admin admin = adminMapper.selectOne(
                new LambdaQueryWrapper<Admin>()
                        .eq(Admin::getUsername, dto.getUsername())
                        .eq(Admin::getStatus, 1)
        );
        if (admin == null || !passwordEncoder.matches(dto.getPassword(), admin.getPassword())) {
            throw new BusinessException(ResultCode.UNAUTHORIZED, "用户名或密码错误");
        }
        String token = jwtUtil.generateToken(admin.getId());
        return LoginVo.builder()
                .token(token)
                .userId(admin.getId())
                .nickname(admin.getNickname())
                .build();
    }
}
