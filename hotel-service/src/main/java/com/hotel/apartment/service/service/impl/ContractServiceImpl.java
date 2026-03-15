package com.hotel.apartment.service.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.hotel.apartment.common.exception.BusinessException;
import com.hotel.apartment.common.result.ResultCode;
import com.hotel.apartment.dal.entity.Contract;
import com.hotel.apartment.dal.mapper.ContractMapper;
import com.hotel.apartment.service.service.ContractService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.ThreadLocalRandom;

@Slf4j
@Service
@RequiredArgsConstructor
public class ContractServiceImpl implements ContractService {

    private final ContractMapper contractMapper;

    @Override
    public Contract createContract(Long orderId) {
        Contract contract = new Contract();
        contract.setOrderId(orderId);
        contract.setContractNo(generateContractNo());
        contract.setSignStatus(0);
        contractMapper.insert(contract);
        log.info("Created contract {} for order {}", contract.getContractNo(), orderId);
        return contract;
    }

    @Override
    public Contract getContractByOrderId(Long orderId) {
        Contract contract = contractMapper.selectOne(
                new LambdaQueryWrapper<Contract>()
                        .eq(Contract::getOrderId, orderId)
                        .last("LIMIT 1")
        );
        if (contract == null) {
            throw new BusinessException(ResultCode.NOT_FOUND, "合同不存在");
        }
        return contract;
    }

    private String generateContractNo() {
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
        int random = ThreadLocalRandom.current().nextInt(1000, 9999);
        return "CT" + timestamp + random;
    }
}
