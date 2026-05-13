package com.shihailiang.rtmsbackend.service;

import com.shihailiang.response.CommonResponse;
import com.shihailiang.rtmsbackend.pojo.entity.OrderProduct;
import jakarta.servlet.http.HttpServletRequest;

public interface OrderProductService {

    /**
     * 新增订单产品
     */
    CommonResponse createOrderProduct(OrderProduct orderProduct, HttpServletRequest request);

    /**
     * 根据订单id查询订单产品
     */
    CommonResponse selectOrderProductsByOrderId(Long orderId, HttpServletRequest request);
}
