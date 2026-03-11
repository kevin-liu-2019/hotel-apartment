package com.hotel.apartment.service.strategy;

import com.hotel.apartment.common.exception.BusinessException;
import com.hotel.apartment.common.result.ResultCode;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

@Component("SHORT_RENT")
public class ShortRentPriceStrategy implements PriceStrategy {

    private static final int DAYS_PER_MONTH = 30;

    @Override
    public BigDecimal calculate(BigDecimal monthlyPrice, LocalDate startDate, LocalDate endDate) {
        if (startDate == null || endDate == null || !endDate.isAfter(startDate)) {
            throw new BusinessException(ResultCode.PARAM_ERROR, "日期参数无效");
        }
        long days = ChronoUnit.DAYS.between(startDate, endDate);
        BigDecimal dailyPrice = monthlyPrice.divide(BigDecimal.valueOf(DAYS_PER_MONTH), 2, java.math.RoundingMode.HALF_UP);
        BigDecimal totalPrice = dailyPrice.multiply(BigDecimal.valueOf(days));
        // Enforce minimum charge of one full month when calculated daily total is less than monthly price
        return totalPrice.compareTo(monthlyPrice) < 0 ? monthlyPrice : totalPrice;
    }
}
