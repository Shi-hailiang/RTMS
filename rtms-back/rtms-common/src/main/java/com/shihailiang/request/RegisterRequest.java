package com.shihailiang.request;

import lombok.Data;

/**
 * 注册请求
 */
@Data
public class RegisterRequest {
    /**
     * 账号
     */
    private String username;
    /**
     * 密码
     */
    private String password;
    /**
     * 确认密码
     */
    private String checkPassword;
    /**
     * 角色
     */
    private String role;
    /**
     * 电话号码
     */
    private String phone;
    /**
     * 昵称
     */
    private String nickname;
}
