package com.shihailiang.rtmsbackend.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.shihailiang.rtmsbackend.pojo.entity.Order;

public interface OrderMapper extends BaseMapper<Order> {
    int insert(Order order);
}