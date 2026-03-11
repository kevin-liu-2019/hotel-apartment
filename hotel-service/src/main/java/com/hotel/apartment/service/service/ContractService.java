package com.hotel.apartment.service.service;

import com.hotel.apartment.dal.entity.Contract;

public interface ContractService {

    Contract createContract(Long orderId);

    Contract getContractByOrderId(Long orderId);
}
