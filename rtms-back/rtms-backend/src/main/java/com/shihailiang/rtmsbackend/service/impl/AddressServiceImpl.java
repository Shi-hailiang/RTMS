package com.shihailiang.rtmsbackend.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.shihailiang.enumeration.StateCode;
import com.shihailiang.response.CommonResponse;
import com.shihailiang.response.ResponseUtils;
import com.shihailiang.rtmsbackend.mapper.AddressMapper;
import com.shihailiang.rtmsbackend.pojo.entity.Address;
import com.shihailiang.rtmsbackend.service.AddressService;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.shihailiang.constant.StatusConstant.DELETED;
import static com.shihailiang.constant.StatusConstant.NORMAL;


@Slf4j
@Service
public class AddressServiceImpl implements AddressService {

    @Resource
    AddressMapper addressMapper;

    @Override
    public CommonResponse createAddress(Address address, HttpServletRequest request) {
        // 1. 校验数据是否合法
        // 地址为空
        if (address.getAddress().isEmpty()) {
            return ResponseUtils.error(StateCode.STORE_ID_EMPTY);
        }

        // 将表单信息传入实体类
        address.setStatus(NORMAL);
        addressMapper.insert(address);
        return ResponseUtils.success();
    }

    @Override
    public CommonResponse selectAddresses(HttpServletRequest request) {
        // 将数据库地址表内容取出
        QueryWrapper<Address> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("status",NORMAL);
        List<Address> addresses = addressMapper.selectList(queryWrapper);
        return ResponseUtils.success(addresses);
    }

    @Override
    public CommonResponse selectAddressesByAccountId(Long accountId, HttpServletRequest request) {
        // 根据用户id查询地址列表
        QueryWrapper<Address> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("account_id", accountId);
        queryWrapper.eq("status", NORMAL);
        List<Address> addresses = addressMapper.selectList(queryWrapper);
        return ResponseUtils.success(addresses);
    }

    @Override
    public CommonResponse updateAddress(Address address, HttpServletRequest request) {
        UpdateWrapper<Address> updateWrapper = new UpdateWrapper<>();
        updateWrapper.set("address", address.getAddress());  // 设置要更新的字段及值
        updateWrapper.eq("id", address.getId());  // 设置更新条件，这里假设使用 ID 作为更新条件
        addressMapper.update(null, updateWrapper);
        return ResponseUtils.success();
    }

    @Override
    public CommonResponse deleteAddress(Long id, HttpServletRequest request) {
        UpdateWrapper<Address> updateWrapper = new UpdateWrapper<>();
        updateWrapper.set("status", DELETED);  // 设置要更新的字段及值
        updateWrapper.eq("id", id);  // 设置更新条件，这里假设使用 ID 作为更新条件
        addressMapper.update(null, updateWrapper);
        return ResponseUtils.success();
    }
}
