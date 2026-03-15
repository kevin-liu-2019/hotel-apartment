package com.hotel.apartment.common.result;

import lombok.Getter;

@Getter
public enum ResultCode {

    SUCCESS(200, "操作成功"),
    PARAM_ERROR(400, "参数错误"),
    UNAUTHORIZED(401, "未授权"),
    FORBIDDEN(403, "禁止访问"),
    NOT_FOUND(404, "资源不存在"),
    ROOM_NOT_AVAILABLE(4001, "房间不可用或库存不足"),
    ORDER_STATUS_ERROR(4002, "订单状态错误"),
    PAY_ERROR(4003, "支付失败"),
    SYSTEM_ERROR(500, "系统内部错误");

    private final int code;
    private final String message;

    ResultCode(int code, String message) {
        this.code = code;
        this.message = message;
    }
}
