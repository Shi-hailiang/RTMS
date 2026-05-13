package com.shihailiang.request.store;

import lombok.Data;

/**
 * 申请开店请求
 */
@Data
public class OpenStoreRequest {
    /**
     * 账号id
     */
    private Long accountId;
    /**
     * 店铺名称
     */
    private String name;
    /**
     * 店铺logo
     */
    private String logo;
    /**
     * 店铺简介
     */
    private String description;
    /**
     * 电话号码
     */
    private String phone;
    /**
     * 店铺地址
     */
    private String address;
    /**
     * 营业时段
     */
    private String businessHour;
    /**
     * 营业执照
     */
    private String businessLicense;
    /**
     * 打包费用
     */
    private Long packagePrice;
    /**
     * 店铺类型
     */
    private String type;
}
