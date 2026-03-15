package com.hotel.apartment.service.strategy;

import com.hotel.apartment.common.exception.BusinessException;
import com.hotel.apartment.common.result.ResultCode;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

@Component("LONG_RENT")
public class LongRentPriceStrategy implements PriceStrategy {

    private static final long MIN_MONTHS = 6;

    @Override
    public BigDecimal calculate(BigDecimal monthlyPrice, LocalDate startDate, LocalDate endDate) {
        if (startDate == null || endDate == null || !endDate.isAfter(startDate)) {
            throw new BusinessException(ResultCode.PARAM_ERROR, "日期参数无效");
        }
        long months = ChronoUnit.MONTHS.between(startDate, endDate);
        if (months < MIN_MONTHS) {
            throw new BusinessException(ResultCode.PARAM_ERROR, "长租最少租赁6个月");
        }
        return monthlyPrice.multiply(BigDecimal.valueOf(months));
    }
}
