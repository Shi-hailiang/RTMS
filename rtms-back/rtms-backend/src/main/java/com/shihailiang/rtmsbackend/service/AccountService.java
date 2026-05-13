package com.shihailiang.rtmsbackend.service;

import com.shihailiang.request.LoginRequest;
import com.shihailiang.request.RegisterRequest;
import com.shihailiang.response.CommonResponse;
import com.shihailiang.rtmsbackend.pojo.entity.Account;
import jakarta.servlet.http.HttpServletRequest;

public interface AccountService {

    /**
     * 注册
     */
    CommonResponse register(RegisterRequest registerRequest, HttpServletRequest request);

    /**
     * 登录
     */
    CommonResponse login(LoginRequest loginRequest, HttpServletRequest request);

    /**
     * 退出登录
     */
    CommonResponse logout(HttpServletRequest request);

    /**
     * 查询账号列表
     */
    CommonResponse selectAccounts(HttpServletRequest request);

    /**
     * 根据账号id查询账号
     */
    CommonResponse selectAccountByAccountId(Long accountId, HttpServletRequest request);

    /**
     * 更新账号头像
     */
    CommonResponse updateAccountAvatar(Account account, HttpServletRequest request);

    /**
     * 更新账号角色
     */
    CommonResponse updateAccountRole(Account account, HttpServletRequest request);

    /**
     * 充值
     */
    CommonResponse addMoney(Account account, HttpServletRequest request);

    /**
     * 更新账号余额
     */
    CommonResponse updateAccountMoney(Account account, HttpServletRequest request);

    /**
     * 封禁账号
     */
    CommonResponse blockedAccountById(Long id, HttpServletRequest request);

    /**
     * 解封账号
     */
    CommonResponse unBlockedAccountById(Long id, HttpServletRequest request);

    /**
     * 根据账单id计算打包费、计算商品总价值，根据商家id获取对应账号余额，更新余额
     */
    CommonResponse updateAccountMoneyAfterCustomerCompleted(Long orderId, HttpServletRequest request);
}
