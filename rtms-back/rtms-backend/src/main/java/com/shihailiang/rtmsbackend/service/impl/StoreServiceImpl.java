package com.shihailiang.rtmsbackend.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.shihailiang.constant.StatusConstant;
import com.shihailiang.enumeration.StateCode;
import com.shihailiang.request.store.OpenStoreRequest;
import com.shihailiang.response.CommonResponse;
import com.shihailiang.response.ResponseUtils;
import com.shihailiang.rtmsbackend.mapper.StoreHomeVOMapper;
import com.shihailiang.rtmsbackend.mapper.StoreMapper;
import com.shihailiang.rtmsbackend.pojo.entity.Store;
import com.shihailiang.rtmsbackend.pojo.vo.StoreHomeVO;
import com.shihailiang.rtmsbackend.service.StoreService;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.shihailiang.constant.StatusConstant.DELETED;
import static com.shihailiang.constant.StatusConstant.NORMAL;

@Slf4j
@Service
public class StoreServiceImpl implements StoreService {

    @Resource
    StoreMapper storeMapper;

    @Resource
    StoreHomeVOMapper storeHomeVOMapper;

    @Override
    public CommonResponse openStore(OpenStoreRequest openStoreRequest, HttpServletRequest request) {
        //1. 查询店铺是否存在
        QueryWrapper<Store> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("account_id",openStoreRequest.getAccountId());
        Store store = storeMapper.selectOne(queryWrapper);
        // 重复申请
        if (store != null){
            log.info("this store can not be opened");
            return ResponseUtils.error(StateCode.STORE_EXIST);
        }
        log.info("this store can be opened");
        // 将表单信息传入实体类
        store = new Store();
        store.setAccountId(openStoreRequest.getAccountId());
        store.setName(openStoreRequest.getName());
        store.setLogo(openStoreRequest.getLogo());
        store.setDescription(openStoreRequest.getDescription());
        store.setPhone(openStoreRequest.getPhone());
        store.setAddress(openStoreRequest.getAddress());
        store.setBusinessHour(openStoreRequest.getBusinessHour());
        store.setBusinessLicense(openStoreRequest.getBusinessLicense());
        store.setPackagePrice(openStoreRequest.getPackagePrice());
        store.setStatus(StatusConstant.IN_REVIEW);
        store.setType(openStoreRequest.getType());
        storeMapper.insert(store);
        return ResponseUtils.success();
    }

    @Override
    public CommonResponse selectStores(HttpServletRequest request) {
        // 将数据库店铺表内容取出
        QueryWrapper<Store> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("status", NORMAL);
        List<Store> stores = storeMapper.selectList(queryWrapper);
        return ResponseUtils.success(stores);
    }

    @Override
    public CommonResponse selectStoresByType(String type, HttpServletRequest request) {
        // 将数据库店铺表内容取出
        QueryWrapper<Store> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("type", type);
        queryWrapper.eq("status", NORMAL);
        List<Store> stores = storeMapper.selectList(queryWrapper);
        return ResponseUtils.success(stores);
    }

    @Override
    public CommonResponse adminSelectStores(HttpServletRequest request) {
        // 将数据库店铺表内容取出
        QueryWrapper<Store> queryWrapper = new QueryWrapper<>();
        queryWrapper.ne("status", DELETED);
        List<Store> stores = storeMapper.selectList(queryWrapper);
        return ResponseUtils.success(stores);
    }

    @Override
    public CommonResponse selectStoreByAccountId(Long accountId, HttpServletRequest request) {
        // 将数据库店铺表内容取出
        QueryWrapper<Store> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("account_id",accountId);
        Store store = storeMapper.selectOne(queryWrapper);
        return ResponseUtils.success(store);
    }

    @Override
    public CommonResponse selectStoreByStoreId(Long storeId, HttpServletRequest request) {
        // 将数据库店铺表内容取出
        QueryWrapper<Store> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id",storeId);
        Store store = storeMapper.selectOne(queryWrapper);
        return ResponseUtils.success(store);
    }

    @Override
    public CommonResponse updateStore(Store store, HttpServletRequest request) {
        UpdateWrapper<Store> updateWrapper = new UpdateWrapper<>();
        updateWrapper.set("name", store.getName());// 设置要更新的字段及值
        updateWrapper.set("logo", store.getLogo());
        updateWrapper.set("phone", store.getPhone());
        updateWrapper.set("address", store.getAddress());
        updateWrapper.set("business_hour", store.getBusinessHour());
        updateWrapper.set("business_license", store.getBusinessLicense());
        updateWrapper.set("package_price", store.getPackagePrice());
        updateWrapper.set("description", store.getDescription());
        updateWrapper.set("type", store.getType());
        updateWrapper.eq("id", store.getId());  // 设置更新条件，这里假设使用 ID 作为更新条件
        storeMapper.update(null, updateWrapper);
        return ResponseUtils.success();
    }

    @Override
    public CommonResponse updateStoreStatus(Store store, HttpServletRequest request) {
        UpdateWrapper<Store> updateWrapper = new UpdateWrapper<>();
        updateWrapper.set("status", store.getStatus());
        updateWrapper.eq("id", store.getId());  // 设置更新条件，这里假设使用 ID 作为更新条件
        storeMapper.update(null, updateWrapper);
        return ResponseUtils.success();
    }

    @Override
    public CommonResponse getHomeDataByStoreId(Long storeId, HttpServletRequest request) {
        // 将数据库账号表内容取出
        QueryWrapper<StoreHomeVO> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("store_id",storeId);
        StoreHomeVO storeHomeVO = storeHomeVOMapper.selectOne(queryWrapper);
        return ResponseUtils.success(storeHomeVO);
    }
}




