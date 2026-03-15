package com.hotel.apartment.admin.controller;

import com.hotel.apartment.admin.dto.AdminLoginDto;
import com.hotel.apartment.admin.service.AdminAuthService;
import com.hotel.apartment.common.result.R;
import com.hotel.apartment.service.vo.LoginVo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@Tag(name = "管理员认证")
@RestController
@RequestMapping("/admin/auth")
@RequiredArgsConstructor
public class AdminAuthController {

    private final AdminAuthService adminAuthService;

    @Operation(summary = "管理员登录")
    @PostMapping("/login")
    public R<LoginVo> login(@Valid @RequestBody AdminLoginDto dto) {
        return R.ok(adminAuthService.login(dto));
    }
}
