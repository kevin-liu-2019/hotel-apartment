package com.hotel.apartment.common.enums;

import lombok.Getter;

@Getter
public enum RentalType {

    SHORT_RENT(0, "短租"),
    LONG_RENT(1, "长租");

    private final int code;
    private final String description;

    RentalType(int code, String description) {
        this.code = code;
        this.description = description;
    }

    public static RentalType of(int code) {
        for (RentalType type : values()) {
            if (type.code == code) {
                return type;
            }
        }
        throw new IllegalArgumentException("Unknown rental type code: " + code);
    }
}
