package com.shihailiang.request;

import lombok.Data;

/**
 * 登录请求
 */
@Data
public class LoginRequest {
    /**
     * 账号
     */
    private String username;
    /**
     * 密码
     */
    private String password;
}
