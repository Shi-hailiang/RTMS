package com.shihailiang.rtmsbackend.controller;

import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.shihailiang.request.LoginRequest;
import com.shihailiang.request.RegisterRequest;
import com.shihailiang.response.CommonResponse;
import com.shihailiang.rtmsbackend.pojo.entity.Account;
import com.shihailiang.rtmsbackend.service.AccountService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@Tag(name = "Account", description = "Account相关接口")
public class AccountController {

    @Resource
    AccountService accountService;

    @PostMapping("/api/register")
    @Operation(summary = "注册接口")
    @ApiOperationSupport(author = "石海良")
    public CommonResponse register(@RequestBody RegisterRequest registerRequest, HttpServletRequest request) {
        log.info("Register API is requested");
        return accountService.register(registerRequest, request);
    }

    @PostMapping("/api/login")
    @Operation(summary = "登录接口")
    @ApiOperationSupport(author = "石海良")
    public CommonResponse login(@RequestBody LoginRequest loginRequest, HttpServletRequest request) {
        log.info("Login API is requested");
        return accountService.login(loginRequest, request);
    }

    @GetMapping("/api/logout")
    @Operation(summary = "退出接口")
    @ApiOperationSupport(author = "石海良")
    public CommonResponse logout(HttpServletRequest request) {
        log.info("Logout API is requested");
        return accountService.logout(request);
    }

    @GetMapping("/api/select_accounts")
    @Operation(summary = "获取账号列表接口")
    @ApiOperationSupport(author = "石海良")
    public CommonResponse selectAccounts(HttpServletRequest request) {
        log.info("SelectAccounts API is requested");
        return accountService.selectAccounts(request);
    }

    @GetMapping("/api/select_account_by_account_id/{accountId}")
    @Operation(summary = "根据账号id获取账号接口")
    @ApiOperationSupport(author = "石海良")
    public CommonResponse selectAccountByAccountId(@PathVariable Long accountId, HttpServletRequest request) {
        log.info("SelectAccountByAccountId API is requested");
        return accountService.selectAccountByAccountId(accountId, request);
    }

    @PostMapping("/api/update_account_avatar")
    @Operation(summary = "更新账号头像接口")
    @ApiOperationSupport(author = "石海良")
    public CommonResponse updateAccountAvatar(@RequestBody Account account, HttpServletRequest request) {
        log.info("UpdateAccountAvatar API is requested");
        return accountService.updateAccountAvatar(account, request);
    }

    @PostMapping("/api/update_account_role")
    @Operation(summary = "更新账号角色接口")
    @ApiOperationSupport(author = "石海良")
    public CommonResponse updateAccountRole(@RequestBody Account account, HttpServletRequest request) {
        log.info("UpdateAccountRole API is requested");
        return accountService.updateAccountRole(account, request);
    }

    @PostMapping("/api/add_money")
    @Operation(summary = "充值接口")
    @ApiOperationSupport(author = "石海良")
    public CommonResponse addMoney(@RequestBody Account account, HttpServletRequest request) {
        log.info("AddMoney API is requested");
        return accountService.addMoney(account, request);
    }

    @PostMapping("/api/update_account_money")
    @Operation(summary = "更新账号余额接口")
    @ApiOperationSupport(author = "石海良")
    public CommonResponse updateAccountMoney(@RequestBody Account account, HttpServletRequest request) {
        log.info("UpdateAccountMoney API is requested");
        return accountService.updateAccountMoney(account, request);
    }

    @PostMapping("/api/blocked_account/{id}")
    @Operation(summary = "根据id封禁账号接口")
    @ApiOperationSupport(author = "石海良")
    public CommonResponse blockedAccountById(@PathVariable Long id, HttpServletRequest request) {
        log.info("BlockedAccountById API is requested");
        return accountService.blockedAccountById(id, request);
    }

    @PostMapping("/api/un_blocked_account/{id}")
    @Operation(summary = "根据id解封账号接口")
    @ApiOperationSupport(author = "石海良")
    public CommonResponse unBlockedAccountById(@PathVariable Long id, HttpServletRequest request) {
        log.info("UnBlockedAccountById API is requested");
        return accountService.unBlockedAccountById(id, request);
    }

    @PostMapping("/api/update_account_money_after_customer_completed/{orderId}")
    @Operation(summary = "顾客完成订单后更新商家账号余额")
    @ApiOperationSupport(author = "石海良")
    public CommonResponse updateAccountMoneyAfterCustomerCompleted(@PathVariable Long orderId, HttpServletRequest request) {
        log.info("UpdateAccountMoneyAfterCustomerCompleted API is requested");
        return accountService.updateAccountMoneyAfterCustomerCompleted(orderId, request);
    }

}
