package com.shihailiang.rtmsbackend.service;

import com.shihailiang.request.store.OpenStoreRequest;
import com.shihailiang.response.CommonResponse;
import com.shihailiang.rtmsbackend.pojo.entity.Store;
import jakarta.servlet.http.HttpServletRequest;

public interface StoreService {
    /**
     * 申请开店
     */
    CommonResponse openStore(OpenStoreRequest openStoreRequest, HttpServletRequest request);

    /**
     * 查询店铺列表
     */
    CommonResponse selectStores(HttpServletRequest request);

    /**
     * 根据店铺类型查询店铺列表
     */
    CommonResponse selectStoresByType(String type, HttpServletRequest request);

    /**
     * 管理员查询店铺列表
     */
    CommonResponse adminSelectStores(HttpServletRequest request);

    /**
     * 根据账号id查询店铺
     */
    CommonResponse selectStoreByAccountId(Long accountId, HttpServletRequest request);

    /**
     * 根据店铺id查询店铺
     */
    CommonResponse selectStoreByStoreId(Long storeId, HttpServletRequest request);

    /**
     * 修改店铺
     */
    CommonResponse updateStore(Store store , HttpServletRequest request);

    /**
     * 修改店铺状态
     */
    CommonResponse updateStoreStatus(Store store , HttpServletRequest request);

    /**
     * 根据店铺id获取店铺Home页数据
     */
    CommonResponse getHomeDataByStoreId(Long storeId, HttpServletRequest request);

}
