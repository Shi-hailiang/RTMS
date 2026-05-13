package com.shihailiang.rtmsbackend.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.shihailiang.enumeration.StateCode;
import com.shihailiang.request.LoginRequest;
import com.shihailiang.request.RegisterRequest;
import com.shihailiang.response.CommonResponse;
import com.shihailiang.response.ResponseUtils;
import com.shihailiang.rtmsbackend.mapper.AccountMapper;
import com.shihailiang.rtmsbackend.mapper.OrderMapper;
import com.shihailiang.rtmsbackend.mapper.OrderProductMapper;
import com.shihailiang.rtmsbackend.mapper.StoreMapper;
import com.shihailiang.rtmsbackend.pojo.dto.AccountHandledDTO;
import com.shihailiang.rtmsbackend.pojo.entity.Account;
import com.shihailiang.rtmsbackend.pojo.entity.Order;
import com.shihailiang.rtmsbackend.pojo.entity.OrderProduct;
import com.shihailiang.rtmsbackend.pojo.entity.Store;
import com.shihailiang.rtmsbackend.service.AccountService;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.shihailiang.constant.AccountConstant.*;
import static com.shihailiang.constant.StatusConstant.BANNED;
import static com.shihailiang.constant.StatusConstant.NORMAL;

@Slf4j
@Service
public class AccountServiceImpl implements AccountService {

    @Resource
    AccountMapper accountMapper;
    @Resource
    OrderMapper orderMapper;
    @Resource
    OrderProductMapper orderProductMapper;
    @Resource
    StoreMapper storeMapper;

    @Override
    public CommonResponse register(RegisterRequest registerRequest, HttpServletRequest request) {
        // 1. 校验数据是否合法
        String username = registerRequest.getUsername();
        String password = registerRequest.getPassword();
        String checkPassword = registerRequest.getCheckPassword();
        String role = registerRequest.getRole();
        String phone = registerRequest.getPhone();
        String nickname = registerRequest.getNickname();
        // 账号为空
        if (username.isEmpty()) {
            return ResponseUtils.error(StateCode.USERNAME_EMPTY);
        }
        // 密码为空
        if (password.isEmpty()) {
            return ResponseUtils.error(StateCode.PASSWORD_EMPTY);
        }
        // 确认密码为空
        if (checkPassword.isEmpty()) {
            return ResponseUtils.error(StateCode.CHECK_PASSWORD_EMPTY);
        }
        // 角色为空，默认为顾客
        if (role.isEmpty()) {
            role = CUSTOMER_ROLE;
        }
        // 电话号码为空
        if (phone.isEmpty()) {
            return ResponseUtils.error(StateCode.PHONE_EMPTY);
        }
        // 昵称为空
        if (nickname.isEmpty()) {
            return ResponseUtils.error(StateCode.NICKNAME_EMPTY);
        }
        // 两次输入的密码不相同
        if (!password.equals(checkPassword)) {
            return ResponseUtils.error(StateCode.PASSWORD_NOT_EQUAL);
        }
        // 账号不能包含特殊字符
        String validPattern = "[\\u4e00-\\u9fa5`~!@#$%^&*()+=|{}:;\\\\[\\\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？']";
        Matcher matcher = Pattern.compile(validPattern).matcher(username);
        if (matcher.find()){
            return ResponseUtils.error(StateCode.PARAMS_ERROR);
        }
        // 账号不能包含空格
        if (username.contains(" ")){
            return ResponseUtils.error(StateCode.PARAMS_ERROR);
        }
        //2. 加密
        String encryptPassword = DigestUtils.md5DigestAsHex((SALT + password).getBytes());
        System.out.println(encryptPassword);
        //3. 查询用户是否存在
        QueryWrapper<Account> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username",username);
        Account account = accountMapper.selectOne(queryWrapper);
        // 账号已存在
        if (account != null){
            log.info("this username can not be registered");
            return ResponseUtils.error(StateCode.USERNAME_EXIST);
        }
        log.info("this username can be registered");
        // 将表单信息传入实体类
        account = new Account();
        account.setUsername(username);
        account.setPassword(encryptPassword);
        account.setRole(role);
        account.setPhone(phone);
        account.setNickname(nickname);
        account.setAvatar(CUSTOMER_AVATAR);
        log.info(account.toString());
        accountMapper.insert(account);
        return ResponseUtils.success();
    }

    @Override
    public CommonResponse login(LoginRequest loginRequest, HttpServletRequest request) {
        // 1. 校验数据是否合法
        String username = loginRequest.getUsername();
        String password = loginRequest.getPassword();
        // 账号为空
        if (username.isEmpty()) {
            return ResponseUtils.error(StateCode.USERNAME_EMPTY);
        }
        // 密码为空
        if (password.isEmpty()) {
            return ResponseUtils.error(StateCode.PASSWORD_EMPTY);
        }
        // 账号包含特殊字符
        String validPattern = "[\\u4e00-\\u9fa5`~!@#$%^&*()+=|{}:;\\\\[\\\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？']";
        Matcher matcher = Pattern.compile(validPattern).matcher(username);
        if (matcher.find()){
            return ResponseUtils.error(StateCode.PARAMS_ERROR);
        }
        // 账号包含空格
        if (username.contains(" ")){
            return ResponseUtils.error(StateCode.PARAMS_ERROR);
        }
        //2. 加密
        String encryptPassword = DigestUtils.md5DigestAsHex((SALT + password).getBytes());
        //3. 查询用户是否存在
        QueryWrapper<Account> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username",username);
        queryWrapper.eq("password",encryptPassword);
        Account account = accountMapper.selectOne(queryWrapper);
        // 用户不存在
        if (account == null){
            log.info("user login failed, userAccount cannot match userPassword");
            return ResponseUtils.error(StateCode.LOGIN_FAILED);
        }
        AccountHandledDTO accountHandledDTO = getAccountHandledDTO(account);
        // 获取当前会话，如果不存在则创建新的会话
        HttpSession session = request.getSession(true);
        // 将用户信息存储在会话中，可以存储更多的用户信息
        session.setAttribute(ACCOUNT_LOGIN_STATE, accountHandledDTO);
        return ResponseUtils.success(accountHandledDTO);
    }

    @Override
    public CommonResponse logout(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            // 移除登录态
            request.getSession().removeAttribute(ACCOUNT_LOGIN_STATE);
        }
        return ResponseUtils.success();
    }

    @Override
    public CommonResponse selectAccounts(HttpServletRequest request) {
        // 将数据库账号表内容取出
        QueryWrapper<Account> queryWrapper = new QueryWrapper<>();
        queryWrapper.ne("role", "管理员");
        List<Account> accounts = accountMapper.selectList(queryWrapper);
        // 数据脱敏后返回
        List<AccountHandledDTO> accountHandledDTOS = new ArrayList<>();
        for (int i = 0; i < accounts.size(); i++) {
            Account account = accounts.get(i);
            AccountHandledDTO accountHandledDTO = getAccountHandledDTO(account);
            accountHandledDTOS.add(accountHandledDTO);
        }
        return ResponseUtils.success(accountHandledDTOS);
    }

    @Override
    public CommonResponse selectAccountByAccountId(Long accountId, HttpServletRequest request) {
        // 将数据库账号表内容取出
        QueryWrapper<Account> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id", accountId);
        Account account = accountMapper.selectOne(queryWrapper);
        // 数据脱敏后返回
        AccountHandledDTO accountHandledDTO = getAccountHandledDTO(account);
        return ResponseUtils.success(accountHandledDTO);
    }

    @Override
    public CommonResponse updateAccountAvatar(Account account, HttpServletRequest request) {
        UpdateWrapper<Account> updateWrapper = new UpdateWrapper<>();
        updateWrapper.set("avatar", account.getAvatar());  // 设置要更新的字段及值
        updateWrapper.eq("id", account.getId());  // 设置更新条件，这里假设使用 ID 作为更新条件
        accountMapper.update(null, updateWrapper);
        return ResponseUtils.success();
    }

    @Override
    public CommonResponse updateAccountRole(Account account, HttpServletRequest request) {
        UpdateWrapper<Account> updateWrapper = new UpdateWrapper<>();
        updateWrapper.set("role", account.getRole());  // 设置要更新的字段及值
        updateWrapper.eq("id", account.getId());  // 设置更新条件，这里假设使用 ID 作为更新条件
        accountMapper.update(null, updateWrapper);
        return ResponseUtils.success();
    }

    @Override
    public CommonResponse addMoney(Account account, HttpServletRequest request) {
        UpdateWrapper<Account> updateWrapper = new UpdateWrapper<>();
        updateWrapper.set("money", account.getMoney());  // 设置要更新的字段及值
        updateWrapper.eq("id", account.getId());  // 设置更新条件，这里假设使用 ID 作为更新条件
        accountMapper.update(null, updateWrapper);
        return ResponseUtils.success();
    }

    @Override
    public CommonResponse updateAccountMoney(Account account, HttpServletRequest request) {
        UpdateWrapper<Account> updateWrapper = new UpdateWrapper<>();
        updateWrapper.set("money", account.getMoney());  // 设置要更新的字段及值
        updateWrapper.eq("id", account.getId());  // 设置更新条件，这里假设使用 ID 作为更新条件
        accountMapper.update(null, updateWrapper);
        return ResponseUtils.success();
    }

    @Override
    public CommonResponse blockedAccountById(Long id, HttpServletRequest request) {
        UpdateWrapper<Account> updateWrapper = new UpdateWrapper<>();
        updateWrapper.set("status", BANNED);  // 设置要更新的字段及值
        updateWrapper.eq("id", id);  // 设置更新条件，这里假设使用 ID 作为更新条件
        accountMapper.update(null, updateWrapper);
        return ResponseUtils.success();
    }

    @Override
    public CommonResponse unBlockedAccountById(Long id, HttpServletRequest request) {
        UpdateWrapper<Account> updateWrapper = new UpdateWrapper<>();
        updateWrapper.set("status", NORMAL);  // 设置要更新的字段及值
        updateWrapper.eq("id", id);  // 设置更新条件，这里假设使用 ID 作为更新条件
        accountMapper.update(null, updateWrapper);
        return ResponseUtils.success();
    }

    /**
     * 根据账单id计算打包费、计算商品总价值，根据商家id获取对应账号余额，更新余额
     */
    @Override
    public CommonResponse updateAccountMoneyAfterCustomerCompleted(Long orderId, HttpServletRequest request) {
        // 获取对应账单
        QueryWrapper<Order> orderQueryWrapper = new QueryWrapper<>();
        orderQueryWrapper.eq("id", orderId);
        Order order = orderMapper.selectOne(orderQueryWrapper);
        String storeId = String.valueOf(order.getStoreId());
        Long packagePrice = order.getPackagePrice();

        // 获取账单产品列表
        QueryWrapper<OrderProduct> orderProductQueryWrapper = new QueryWrapper<>();
        orderProductQueryWrapper.eq("order_id", orderId);
        List<OrderProduct> orderProducts = orderProductMapper.selectList(orderProductQueryWrapper);

        // 计算产品总价与打包费之和totalPrice
        Long orderProductsTotalPrice = 0L;
        for (int i = 0; i < orderProducts.size(); i++) {
            OrderProduct orderProduct = new OrderProduct();
            orderProduct = orderProducts.get(i);
            orderProductsTotalPrice += orderProduct.getTotalPrice();
        }
        Long totalPrice = orderProductsTotalPrice + packagePrice;

        // 获取对应商家账号
        QueryWrapper<Store> storeQueryWrapper = new QueryWrapper<>();
        storeQueryWrapper.eq("id", order.getStoreId());
        Store store = storeMapper.selectOne(storeQueryWrapper);

        QueryWrapper<Account> accountQueryWrapper = new QueryWrapper<>();
        accountQueryWrapper.eq("id", store.getAccountId());
        Account account = accountMapper.selectOne(accountQueryWrapper);
        Long money = account.getMoney();
        money = money + totalPrice;

        UpdateWrapper<Account> updateWrapper = new UpdateWrapper<>();
        updateWrapper.set("money", money);  // 设置要更新的字段及值
        updateWrapper.eq("id", account.getId());  // 设置更新条件，这里假设使用 ID 作为更新条件
        accountMapper.update(null, updateWrapper);
        return ResponseUtils.success();
    }


    /**
     * 获取脱敏账号信息
     * @param account
     * @return accountHandledDTO
     */
    private static AccountHandledDTO getAccountHandledDTO(Account account) {
        AccountHandledDTO accountHandledDTO = new AccountHandledDTO();
        accountHandledDTO.setId(account.getId());
        accountHandledDTO.setUsername(account.getUsername());
        accountHandledDTO.setRole(account.getRole());
        accountHandledDTO.setPhone(account.getPhone());
        accountHandledDTO.setNickname(account.getNickname());
        accountHandledDTO.setAvatar(account.getAvatar());
        accountHandledDTO.setMoney(account.getMoney());
        accountHandledDTO.setCreateTime(account.getCreateTime());
        accountHandledDTO.setUpdateTime(account.getUpdateTime());
        accountHandledDTO.setStatus(account.getStatus());
        return accountHandledDTO;
    }
}
