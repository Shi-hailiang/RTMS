package com.shihailiang.rtmsbackend.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.shihailiang.response.CommonResponse;
import com.shihailiang.response.ResponseUtils;
import com.shihailiang.rtmsbackend.mapper.SystemMapper;
import com.shihailiang.rtmsbackend.pojo.entity.System;
import com.shihailiang.rtmsbackend.pojo.vo.SystemVO;
import com.shihailiang.rtmsbackend.service.SystemService;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class SystemServiceImpl implements SystemService {

    @Resource
    SystemMapper systemMapper;
    String tableKey;
    String tableValue;
    SystemVO systemVO;

    @Override
    public CommonResponse selectSystem(HttpServletRequest request) {
        // 将数据库账号表内容取出
        QueryWrapper<System> queryWrapper = new QueryWrapper<>();
        List<System> systemKeyValues = systemMapper.selectList(queryWrapper);
        // 在循环之前实例化 systemVO 对象
        systemVO = new SystemVO();
        for (int i = 0; i < systemKeyValues.size(); i++) {
            log.info(systemKeyValues.get(i).toString());
            tableKey = systemKeyValues.get(i).getTableKey();
            tableValue = systemKeyValues.get(i).getTableValue();
            if ("name".equals(tableKey)) {
                systemVO.setName(tableValue);
            }
            if ("announcement".equals(tableKey)) {
                systemVO.setAnnouncement(tableValue);
            }
            if ("banner_1".equals(tableKey)) {
                systemVO.setBanner1(tableValue);
            }
            if ("banner_2".equals(tableKey)) {
                systemVO.setBanner2(tableValue);
            }
            if ("banner_3".equals(tableKey)) {
                systemVO.setBanner3(tableValue);
            }
            if ("delivery_price".equals(tableKey)) {
                systemVO.setDeliveryPrice(Long.valueOf(tableValue));
            }
        }
        return ResponseUtils.success(systemVO);
    }

    @Override
    public CommonResponse updateSystem(SystemVO systemVO, HttpServletRequest request) {
        String name = systemVO.getName();
        String announcement = systemVO.getAnnouncement();
        String deliveryPrice = String.valueOf(systemVO.getDeliveryPrice());
        String banner1 = systemVO.getBanner1();
        String banner2 = systemVO.getBanner2();
        String banner3 = systemVO.getBanner3();
        UpdateWrapper<System> updateWrapper1 = new UpdateWrapper<>();
        UpdateWrapper<System> updateWrapper2 = new UpdateWrapper<>();
        UpdateWrapper<System> updateWrapper3 = new UpdateWrapper<>();
        UpdateWrapper<System> updateWrapper4 = new UpdateWrapper<>();
        UpdateWrapper<System> updateWrapper5 = new UpdateWrapper<>();
        UpdateWrapper<System> updateWrapper6 = new UpdateWrapper<>();
        updateWrapper1.set("table_value", name);
        updateWrapper1.eq("table_key", "name");
        systemMapper.update(null, updateWrapper1);
        updateWrapper2.set("table_value", announcement);
        updateWrapper2.eq("table_key", "announcement");
        systemMapper.update(null, updateWrapper2);
        updateWrapper3.set("table_value", deliveryPrice);
        updateWrapper3.eq("table_key", "delivery_price");
        systemMapper.update(null, updateWrapper3);
        updateWrapper4.set("table_value", banner1);
        updateWrapper4.eq("table_key", "banner_1");
        systemMapper.update(null, updateWrapper4);
        updateWrapper5.set("table_value", banner2);
        updateWrapper5.eq("table_key", "banner_2");
        systemMapper.update(null, updateWrapper5);
        updateWrapper6.set("table_value", banner3);
        updateWrapper6.eq("table_key", "banner_3");
        systemMapper.update(null, updateWrapper6);
        return ResponseUtils.success();
    }
}
