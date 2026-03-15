package com.hotel.apartment.common.enums;

import lombok.Getter;

@Getter
public enum OrderStatus {

    PENDING_SIGN(0, "待签约"),
    PENDING_PAY(1, "待支付"),
    PAID(2, "已支付"),
    SIGNED(3, "已签约"),
    IN_PROGRESS(4, "进行中"),
    COMPLETED(5, "已完成"),
    CANCELLED(6, "已取消");

    private final int code;
    private final String description;

    OrderStatus(int code, String description) {
        this.code = code;
        this.description = description;
    }

    public static OrderStatus of(int code) {
        for (OrderStatus status : values()) {
            if (status.code == code) {
                return status;
            }
        }
        throw new IllegalArgumentException("Unknown order status code: " + code);
    }
}
