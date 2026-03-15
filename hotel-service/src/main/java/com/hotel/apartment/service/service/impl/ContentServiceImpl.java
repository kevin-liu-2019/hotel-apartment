package com.hotel.apartment.service.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.hotel.apartment.dal.entity.PageConfig;
import com.hotel.apartment.dal.entity.PopupConfig;
import com.hotel.apartment.dal.mapper.PageConfigMapper;
import com.hotel.apartment.dal.mapper.PopupConfigMapper;
import com.hotel.apartment.service.service.ContentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ContentServiceImpl implements ContentService {

    private final PageConfigMapper pageConfigMapper;
    private final PopupConfigMapper popupConfigMapper;

    @Override
    public PageConfig getPageConfig(Long hotelId, String pageType) {
        return pageConfigMapper.selectOne(
                new LambdaQueryWrapper<PageConfig>()
                        .eq(PageConfig::getHotelId, hotelId)
                        .eq(PageConfig::getPageType, pageType)
                        .eq(PageConfig::getStatus, 1)
                        .last("LIMIT 1")
        );
    }

    @Override
    public List<PopupConfig> getPopupConfig(Long hotelId) {
        LocalDateTime now = LocalDateTime.now();
        return popupConfigMapper.selectList(
                new LambdaQueryWrapper<PopupConfig>()
                        .eq(PopupConfig::getHotelId, hotelId)
                        .eq(PopupConfig::getStatus, 1)
                        .le(PopupConfig::getStartTime, now)
                        .ge(PopupConfig::getEndTime, now)
                        .orderByDesc(PopupConfig::getCreatedAt)
        );
    }
}
