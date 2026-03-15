package com.hotel.apartment.api.controller;

import com.hotel.apartment.common.result.R;
import com.hotel.apartment.dal.entity.User;
import com.hotel.apartment.service.dto.UpdateUserDto;
import com.hotel.apartment.service.dto.WxLoginDto;
import com.hotel.apartment.service.service.UserService;
import com.hotel.apartment.service.vo.LoginVo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@Tag(name = "用户接口")
@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @Operation(summary = "微信登录")
    @PostMapping("/login")
    public R<LoginVo> login(@Valid @RequestBody WxLoginDto dto) {
        return R.ok(userService.wxLogin(dto));
    }

    @Operation(summary = "获取用户信息")
    @GetMapping("/info")
    public R<User> info(Authentication authentication) {
        Long userId = (Long) authentication.getPrincipal();
        return R.ok(userService.getUserInfo(userId));
    }

    @Operation(summary = "更新用户信息")
    @PutMapping("/info")
    public R<Void> updateInfo(Authentication authentication, @RequestBody UpdateUserDto dto) {
        Long userId = (Long) authentication.getPrincipal();
        userService.updateUserInfo(userId, dto);
        return R.ok();
    }
}
