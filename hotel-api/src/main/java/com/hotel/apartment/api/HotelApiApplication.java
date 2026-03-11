package com.hotel.apartment.api;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.hotel.apartment")
@MapperScan("com.hotel.apartment.dal.mapper")
public class HotelApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(HotelApiApplication.class, args);
    }
}
