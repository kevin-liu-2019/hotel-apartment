package com.hotel.apartment.service.strategy;

import com.hotel.apartment.common.exception.BusinessException;
import com.hotel.apartment.common.result.ResultCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@RequiredArgsConstructor
public class PriceStrategyFactory {

    private final Map<String, PriceStrategy> strategyMap;

    public PriceStrategy getStrategy(String rentalType) {
        PriceStrategy strategy = strategyMap.get(rentalType);
        if (strategy == null) {
            throw new BusinessException(ResultCode.PARAM_ERROR, "不支持的租赁类型: " + rentalType);
        }
        return strategy;
    }
}
