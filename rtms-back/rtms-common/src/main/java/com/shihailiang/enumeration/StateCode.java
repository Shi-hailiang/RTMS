package com.shihailiang.enumeration;

import lombok.Getter;

/**
 * 全局状态码
 *
 * @author 石海良
 */
@Getter
public enum StateCode {

    /**
     * 正确相关状态码
     */
    SUCCESS(0,"ok",""),

    /**
     * 错误相关状态码
     */
    PARAMS_ERROR(400,"请求参数错误",""),
    NULL_ERROR(400,"请求数据为空"," "),
    USERNAME_EMPTY(400,"账号不能为空"," "),
    PASSWORD_EMPTY(400,"密码不能为空"," "),
    CHECK_PASSWORD_EMPTY(400,"确认密码不能为空"," "),
    PASSWORD_NOT_EQUAL(400,"两次密码不相同"," "),
    PHONE_EMPTY(400,"电话号码不能为空"," "),
    NICKNAME_EMPTY(400,"昵称不能为空"," "),
    ACCOUNT_ID_EMPTY(400,"账号id不能为空"," "),
    STORE_ID_EMPTY(400,"店铺id不能为空"," "),

    ADDRESS_EMPTY(400,"地址不能为空"," "),

    CATEGORY_ID_EMPTY(400,"分类id不能为空"," "),
    CATEGORY_NAME_EMPTY(400,"分类名称不能为空"," "),
    CATEGORY_NAME_EXIST(400,"分类名已存在"," "),
    CATEGORY_NOT_EXIST(400,"分类不存在"," "),

    SPECIFICATION_ID_EMPTY(400,"规格id不能为空"," "),
    SPECIFICATION_TYPE_EMPTY(400,"规格类型不能为空"," "),
    SPECIFICATION_NAME_EMPTY(400,"规格名称不能为空"," "),

    PRODUCT_ID_EMPTY(400,"产品id不能为空"," "),
    PRODUCT_NAME_EMPTY(400,"产品名称不能为空"," "),
    PRODUCT_EXIST(400,"产品已存在"," "),
    PRODUCT_NOT_EXIST(400,"产品不存在"," "),

    ORDER_ID_EMPTY(400,"订单id不能为空"," "),
    TOTAL_PRICE_EMPTY(400, "总价格不能为空"," "),
    RIDER_ID_EMPTY(400, "骑手id不能为空", " "),
    LOCATION_EMPTY(400, "经纬度不能为空", " "),

    STORE_EXIST(400,"该账号已申请开店"," "),

    USERNAME_EXIST(400,"账号已存在"," "),
    LOGIN_FAILED(400,"账号或密码错误"," "),
    NOT_LOGIN_ERROR(401,"未登录",""),
    NO_AUTH_ERROR(401,"无权限",""),
    SYSTEM_ERROR(50000,"系统内部异常","");

    /**
     * 状态码
     */
    private final int code;

    /**
     * 状态信息
     */
    private final String message;

    /**
     * 状态描述
     */
    private final String description;

    StateCode(int code, String message, String description) {
        this.code = code;
        this.message = message;
        this.description = description;
    }

}
