package com.shihailiang.rtmsbackend.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.shihailiang.enumeration.StateCode;
import com.shihailiang.response.CommonResponse;
import com.shihailiang.response.ResponseUtils;
import com.shihailiang.rtmsbackend.mapper.OrderProductMapper;
import com.shihailiang.rtmsbackend.pojo.entity.OrderProduct;
import com.shihailiang.rtmsbackend.service.OrderProductService;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.shihailiang.constant.StatusConstant.NORMAL;


@Slf4j
@Service
public class OrderProductServiceImpl implements OrderProductService {

    @Resource
    OrderProductMapper orderProductMapper;

    @Override
    public CommonResponse createOrderProduct(OrderProduct orderProduct, HttpServletRequest request) {
        // 1. 校验数据是否合法
        String orderId = String.valueOf(orderProduct.getOrderId());
        String productId = String.valueOf(orderProduct.getProductId());
        String price = String.valueOf(orderProduct.getPrice());
        String number = String.valueOf(orderProduct.getNumber());
        String specification = orderProduct.getSpecification();
        String totalPrice = String.valueOf(orderProduct.getTotalPrice());

        // 订单id为空
        if (orderId.isEmpty()) {
            return ResponseUtils.error(StateCode.ORDER_ID_EMPTY);
        }

        // 产品id为空
        if (productId.isEmpty()) {
            return ResponseUtils.error(StateCode.PRODUCT_ID_EMPTY);
        }

        // 总价为空
        if (totalPrice.isEmpty()) {
            return ResponseUtils.error(StateCode.TOTAL_PRICE_EMPTY);
        }

        // 将表单信息传入实体类
        orderProduct.setStatus(NORMAL);
        orderProductMapper.insert(orderProduct);
        return ResponseUtils.success();
    }

    @Override
    public CommonResponse selectOrderProductsByOrderId(Long orderId, HttpServletRequest request) {
        // 将数据库订单表内容取出
        QueryWrapper<OrderProduct> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("order_id",orderId);
        queryWrapper.eq("status",NORMAL);
        List<OrderProduct> orderProducts = orderProductMapper.selectList(queryWrapper);
        return ResponseUtils.success(orderProducts);
    }
}
