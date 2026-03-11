package com.hotel.apartment.api.controller;

import com.hotel.apartment.common.result.R;
import com.hotel.apartment.dal.entity.PageConfig;
import com.hotel.apartment.dal.entity.PopupConfig;
import com.hotel.apartment.service.service.ContentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "内容配置接口")
@RestController
@RequestMapping("/api/content")
@RequiredArgsConstructor
public class ContentController {

    private final ContentService contentService;

    @Operation(summary = "获取页面配置")
    @GetMapping("/page")
    public R<PageConfig> pageConfig(
            @RequestParam Long hotelId,
            @RequestParam String pageType) {
        return R.ok(contentService.getPageConfig(hotelId, pageType));
    }

    @Operation(summary = "获取弹窗配置")
    @GetMapping("/popup")
    public R<List<PopupConfig>> popupConfig(@RequestParam Long hotelId) {
        return R.ok(contentService.getPopupConfig(hotelId));
    }
}
