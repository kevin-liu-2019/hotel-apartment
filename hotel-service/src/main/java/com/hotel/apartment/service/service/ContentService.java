package com.hotel.apartment.service.service;

import com.hotel.apartment.dal.entity.PageConfig;
import com.hotel.apartment.dal.entity.PopupConfig;

import java.util.List;

public interface ContentService {

    PageConfig getPageConfig(Long hotelId, String pageType);

    List<PopupConfig> getPopupConfig(Long hotelId);
}
