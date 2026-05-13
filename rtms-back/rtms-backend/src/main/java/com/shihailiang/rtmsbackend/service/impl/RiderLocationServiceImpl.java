package com.shihailiang.rtmsbackend.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.shihailiang.enumeration.StateCode;
import com.shihailiang.response.CommonResponse;
import com.shihailiang.response.ResponseUtils;
import com.shihailiang.rtmsbackend.mapper.OrderMapper;
import com.shihailiang.rtmsbackend.mapper.RiderLocationMapper;
import com.shihailiang.rtmsbackend.mapper.StoreMapper;
import com.shihailiang.rtmsbackend.pojo.entity.Order;
import com.shihailiang.rtmsbackend.pojo.entity.RiderLocation;
import com.shihailiang.rtmsbackend.pojo.entity.Store;
import com.shihailiang.rtmsbackend.service.RiderLocationService;
import com.shihailiang.util.CoordinateUtil;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
public class RiderLocationServiceImpl implements RiderLocationService {

    @Resource
    RiderLocationMapper riderLocationMapper;

    @Resource
    OrderMapper orderMapper;

    @Resource
    StoreMapper storeMapper;

    @Override
    public CommonResponse reportLocation(RiderLocation riderLocation, HttpServletRequest request) {
        if (riderLocation.getOrderId() == null) {
            return ResponseUtils.error(StateCode.ORDER_ID_EMPTY);
        }
        if (riderLocation.getRiderId() == null) {
            return ResponseUtils.error(StateCode.RIDER_ID_EMPTY);
        }
        if (riderLocation.getLatitude() == null || riderLocation.getLongitude() == null) {
            return ResponseUtils.error(StateCode.LOCATION_EMPTY);
        }
        riderLocationMapper.insert(riderLocation);
        return ResponseUtils.success();
    }

    @Override
    public CommonResponse getLatestLocationByOrderId(Long orderId, HttpServletRequest request) {
        // 查询订单
        QueryWrapper<Order> orderQw = new QueryWrapper<>();
        orderQw.eq("id", orderId);
        Order order = orderMapper.selectOne(orderQw);
        if (order == null) {
            return ResponseUtils.error(StateCode.ORDER_ID_EMPTY);
        }

        // 查询商家
        QueryWrapper<Store> storeQw = new QueryWrapper<>();
        storeQw.eq("id", order.getStoreId());
        Store store = storeMapper.selectOne(storeQw);

        // 查询骑手最新位置
        QueryWrapper<RiderLocation> locationQw = new QueryWrapper<>();
        locationQw.eq("order_id", orderId);
        locationQw.orderByDesc("create_time");
        locationQw.last("LIMIT 1");
        RiderLocation location = riderLocationMapper.selectOne(locationQw);

        // 组装返回数据
        Map<String, Object> result = new HashMap<>();

        // 商家坐标
        if (store != null && store.getAddress() != null) {
            result.put("storeLatitude", CoordinateUtil.getLatitude(store.getAddress()));
            result.put("storeLongitude", CoordinateUtil.getLongitude(store.getAddress()));
            result.put("storeName", store.getName());
        } else {
            result.put("storeLatitude", BigDecimal.valueOf(23.132));
            result.put("storeLongitude", BigDecimal.valueOf(113.322));
            result.put("storeName", "商家");
        }

        // 收货地址坐标
        if (order.getAddress() != null) {
            result.put("deliveryLatitude", CoordinateUtil.getLatitude(order.getAddress()));
            result.put("deliveryLongitude", CoordinateUtil.getLongitude(order.getAddress()));
            result.put("deliveryAddress", order.getAddress());
        }

        // 骑手位置坐标
        if (location != null) {
            result.put("riderLatitude", location.getLatitude());
            result.put("riderLongitude", location.getLongitude());
        }

        return ResponseUtils.success(result);
    }
}
