package com.shihailiang.constant;

/**
 * 账号常量
 */
public interface AccountConstant {

    /**
     * 账号登录态键
     */
    String ACCOUNT_LOGIN_STATE = "account_login";

    /**
     * 盐值：密码加密
     */
    String SALT = "shihailiang";

    //  region 角色权限

    /**
     * 超级管理员
     */
    String ADMIN_ROLE = "管理员";

    /**
     * 顾客
     */
    String CUSTOMER_ROLE = "顾客";
    String CUSTOMER_AVATAR = "customer.png";

    /**
     * 商家
     */
    String STORE_ROLE = "商家";

    /**
     * 骑手
     */
    String RIDER_ROLE = "骑手";

    // endregion
}