package com.hotel.apartment.service.strategy;

import java.math.BigDecimal;
import java.time.LocalDate;

public interface PriceStrategy {

    BigDecimal calculate(BigDecimal monthlyPrice, LocalDate startDate, LocalDate endDate);
}
