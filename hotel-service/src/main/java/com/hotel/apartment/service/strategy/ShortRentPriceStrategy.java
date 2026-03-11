package com.hotel.apartment.service.strategy;

import com.hotel.apartment.common.exception.BusinessException;
import com.hotel.apartment.common.result.ResultCode;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

@Component("SHORT_RENT")
public class ShortRentPriceStrategy implements PriceStrategy {

    @Override
    public BigDecimal calculate(BigDecimal monthlyPrice, LocalDate startDate, LocalDate endDate) {
        if (startDate == null || endDate == null || !endDate.isAfter(startDate)) {
            throw new BusinessException(ResultCode.PARAM_ERROR, "日期参数无效");
        }
        long days = ChronoUnit.DAYS.between(startDate, endDate);
        // Daily price: monthly price / 30, minimum 1 month charge
        BigDecimal dailyPrice = monthlyPrice.divide(BigDecimal.valueOf(30), 2, java.math.RoundingMode.HALF_UP);
        BigDecimal totalPrice = dailyPrice.multiply(BigDecimal.valueOf(days));
        // If less than 1 month, charge at least 1 month
        if (totalPrice.compareTo(monthlyPrice) < 0) {
            return monthlyPrice;
        }
        return totalPrice;
    }
}
