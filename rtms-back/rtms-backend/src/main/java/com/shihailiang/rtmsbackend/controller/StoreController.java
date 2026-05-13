package com.shihailiang.rtmsbackend.controller;

import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.shihailiang.request.store.OpenStoreRequest;
import com.shihailiang.response.CommonResponse;
import com.shihailiang.rtmsbackend.pojo.entity.Store;
import com.shihailiang.rtmsbackend.service.StoreService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@Tag(name = "Store", description = "Store相关接口")
public class StoreController {

    @Resource
    StoreService storeService;

    @PostMapping("/api/open_store")
    @Operation(summary = "申请开店接口")
    @ApiOperationSupport(author = "石海良")
    public CommonResponse openStore(@RequestBody OpenStoreRequest openStoreRequest, HttpServletRequest request) {
        log.info("OpenStore API is requested");
        return storeService.openStore(openStoreRequest, request);
    }

    @GetMapping("/api/select_stores")
    @Operation(summary = "查询店铺列表接口")
    @ApiOperationSupport(author = "石海良")
    public CommonResponse selectStores(HttpServletRequest request) {
        log.info("SelectStores API is requested");
        return storeService.selectStores(request);
    }

    @GetMapping("/api/select_stores_by_type/{type}")
    @Operation(summary = "根据店铺类型查询店铺列表接口")
    @ApiOperationSupport(author = "石海良")
    public CommonResponse selectStoresByType(@PathVariable String type, HttpServletRequest request) {
        log.info("SelectStoresByType API is requested");
        return storeService.selectStoresByType(type, request);
    }

    @GetMapping("/api/admin_select_stores")
    @Operation(summary = "管理员查询店铺列表接口")
    @ApiOperationSupport(author = "石海良")
    public CommonResponse adminSelectStores(HttpServletRequest request) {
        log.info("AdminSelectStores API is requested");
        return storeService.adminSelectStores(request);
    }

    @GetMapping("/api/select_store_by_account_id/{accountId}")
    @Operation(summary = "根据账号id查询店铺接口")
    @ApiOperationSupport(author = "石海良")
    public CommonResponse selectStoreByAccountId(@PathVariable Long accountId, HttpServletRequest request) {
        log.info("SelectStoreByAccountId API is requested");
        return storeService.selectStoreByAccountId(accountId, request);
    }

    @GetMapping("/api/select_store_by_store_id/{storeId}")
    @Operation(summary = "根据店铺id查询店铺接口")
    @ApiOperationSupport(author = "石海良")
    public CommonResponse selectStoreByStoreId(@PathVariable Long storeId, HttpServletRequest request) {
        log.info("SelectStoreByStoreId API is requested");
        return storeService.selectStoreByStoreId(storeId, request);
    }

    @PostMapping("/api/update_store")
    @Operation(summary = "更新店铺接口")
    @ApiOperationSupport(author = "石海良")
    public CommonResponse updateStore(@RequestBody Store store, HttpServletRequest request) {
        log.info("UpdateStore API is requested");
        return storeService.updateStore(store, request);
    }

    @PostMapping("/api/update_store_status")
    @Operation(summary = "更新店铺状态接口")
    @ApiOperationSupport(author = "石海良")
    public CommonResponse updateStoreStatus(@RequestBody Store store, HttpServletRequest request) {
        log.info("UpdateStoreStatus API is requested");
        return storeService.updateStoreStatus(store, request);
    }

    @GetMapping("/api/get_home_data_by_store_id/{storeId}")
    @Operation(summary = "根据店铺id查询店铺Home页data接口")
    @ApiOperationSupport(author = "石海良")
    public CommonResponse getHomeDataByStoreId(@PathVariable Long storeId, HttpServletRequest request) {
        log.info("GetHomeDataByStoreId API is requested");
        return storeService.getHomeDataByStoreId(storeId, request);
    }
}
