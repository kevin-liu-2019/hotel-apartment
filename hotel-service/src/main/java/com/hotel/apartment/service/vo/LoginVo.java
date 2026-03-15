package com.hotel.apartment.service.vo;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LoginVo {

    private String token;

    private Long userId;

    private String nickname;

    private String avatar;
}
