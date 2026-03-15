package com.hotel.apartment.service.service;

public interface PayService {

    String createPrepayOrder(String orderNo);

    void handlePayNotify(String notifyData);
}
