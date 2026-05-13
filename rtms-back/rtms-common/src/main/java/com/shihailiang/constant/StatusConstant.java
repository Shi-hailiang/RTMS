package com.shihailiang.constant;

/**
 * 状态常量
 */
public interface StatusConstant {

    /**
     * 基本状态
     */
    public static final String NORMAL = "正常";
    public static final String DELETED = "已删除";

    /**
     * 账号状态
     */
    public static final String BANNED = "已封禁";

    /**
     * 店铺、骑手状态
     */
    public static final String  IN_REVIEW = "审核中";
    public static final String  CLOSING = "打烊中";

    /**
     * 订单状态
     */
    public static final String CUSTOMER_DELETED = "顾客已删除";
    public static final String STORE_DELETED = "商家已删除";
    public static final String RIDER_DELETED = "骑手已删除";
    public static final String CUSTOMER_STORE_DELETED = "顾客商家已删除";
    public static final String CUSTOMER_RIDER_DELETED = "顾客骑手已删除";
    public static final String STORE_RIDER_DELETED = "商家骑手已删除";

}
