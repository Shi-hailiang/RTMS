package com.shihailiang.rtmsbackend.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.shihailiang.enumeration.StateCode;
import com.shihailiang.response.CommonResponse;
import com.shihailiang.response.ResponseUtils;
import com.shihailiang.rtmsbackend.mapper.RiderMapper;
import com.shihailiang.rtmsbackend.pojo.entity.Rider;
import com.shihailiang.rtmsbackend.service.RiderService;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.shihailiang.constant.StatusConstant.DELETED;
import static com.shihailiang.constant.StatusConstant.NORMAL;


@Slf4j
@Service
public class RiderServiceImpl implements RiderService {

    @Resource
    RiderMapper riderMapper;

    @Override
    public CommonResponse selectRiders(HttpServletRequest request) {
        // 将数据库骑手表内容取出
        QueryWrapper<Rider> queryWrapper = new QueryWrapper<>();
        queryWrapper.ne("account_id",1);
        queryWrapper.eq("status",NORMAL);
        List<Rider> riders = riderMapper.selectList(queryWrapper);
        return ResponseUtils.success(riders);
    }

    @Override
    public CommonResponse selectRiderById(Long id, HttpServletRequest request) {
        // 将数据库骑手表内容取出
        QueryWrapper<Rider> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id",id);
        queryWrapper.eq("status",NORMAL);
        Rider rider = riderMapper.selectOne(queryWrapper);
        return ResponseUtils.success(rider);
    }

    @Override
    public CommonResponse selectRiderByAccountId(Long accountId, HttpServletRequest request) {
        // 将数据库骑手表内容取出
        QueryWrapper<Rider> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("account_id",accountId);
        queryWrapper.eq("status",NORMAL);
        Rider rider = riderMapper.selectOne(queryWrapper);
        return ResponseUtils.success(rider);
    }

    @Override
    public CommonResponse createRider(Rider rider, HttpServletRequest request) {
        // 1. 校验数据是否合法
        String accountId = String.valueOf(rider.getAccountId());
        // 账号id为空
        if (accountId.isEmpty()) {
            return ResponseUtils.error(StateCode.ACCOUNT_ID_EMPTY);
        }
        // 将表单信息传入实体类
        rider.setStatus(NORMAL);
        riderMapper.insert(rider);
        return ResponseUtils.success();
    }

    @Override
    public CommonResponse updateRider(Rider rider, HttpServletRequest request) {
        UpdateWrapper<Rider> updateWrapper = new UpdateWrapper<>();
        updateWrapper.set("name", rider.getName());  // 设置要更新的字段及值
        updateWrapper.set("gender", rider.getGender());  // 设置要更新的字段及值
        updateWrapper.set("id_number", rider.getIdNumber());  // 设置要更新的字段及值
        updateWrapper.set("phone", rider.getPhone());  // 设置要更新的字段及值
        updateWrapper.eq("id", rider.getId());  // 设置更新条件，这里假设使用 ID 作为更新条件
        riderMapper.update(null, updateWrapper);
        return ResponseUtils.success();
    }

    @Override
    public CommonResponse deleteRider(Long id, HttpServletRequest request) {
        UpdateWrapper<Rider> updateWrapper = new UpdateWrapper<>();
        updateWrapper.set("status", DELETED);  // 设置要更新的字段及值
        updateWrapper.eq("id", id);  // 设置更新条件，这里假设使用 ID 作为更新条件
        riderMapper.update(null, updateWrapper);
        return ResponseUtils.success();
    }
}
