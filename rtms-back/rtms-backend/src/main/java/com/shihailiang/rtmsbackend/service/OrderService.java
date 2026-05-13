package com.shihailiang.rtmsbackend.service;

import com.shihailiang.response.CommonResponse;
import com.shihailiang.rtmsbackend.pojo.entity.Order;
import jakarta.servlet.http.HttpServletRequest;

public interface OrderService {
    /**
     * 新增订单
     */
    CommonResponse createOrder(Order order, HttpServletRequest request);

    /**
     * 删除订单
     */
    CommonResponse deleteOrder(Long id, HttpServletRequest request);

    /**
     * 修改订单
     */
    CommonResponse updateOrder(Order order, HttpServletRequest request);

    /**
     * 根据id查询订单
     */
    CommonResponse selectOrderById(Long id, HttpServletRequest request);

    /**
     * 根据店铺id查询订单
     */
    CommonResponse selectOrdersByStoreId(Long storeId, HttpServletRequest request);

    /**
     * 根据账号id查询订单
     */
    CommonResponse selectOrdersByAccountId(Long accountId, HttpServletRequest request);

    /**
     * 根据账号id查询待支付订单
     */
    CommonResponse selectPendingPaymentOrdersByAccountId(Long accountId, HttpServletRequest request);

    /**
     * 根据账号id查询配送中订单
     */
    CommonResponse selectDeliveringOrdersByAccountId(Long accountId, HttpServletRequest request);

    /**
     * 根据账号id查询已完成订单
     */
    CommonResponse selectCompletedOrdersByAccountId(Long accountId, HttpServletRequest request);

    /**
     * 根据id取消订单
     */
    CommonResponse cancelOrderById(Long id, HttpServletRequest request);

    /**
     * 根据id取消订单并退款
     */
    CommonResponse cancelOrderByIdAndMoneyBack(Long id, HttpServletRequest request);

    /**
     * 顾客根据id删除订单
     */
    CommonResponse customerDeleteOrderById(Long id, HttpServletRequest request);

    /**
     * 顾客根据id支付订单
     */
    CommonResponse customerPaymentOrderById(Long id, HttpServletRequest request);

    /**
     * 商家根据id接受订单
     */
    CommonResponse storeAcceptOrderById(Long id, HttpServletRequest request);

    /**
     * 商家根据id出餐订单
     */
    CommonResponse storeOutOrderById(Long id, HttpServletRequest request);

    /**
     * 骑手根据id接受订单
     */
    CommonResponse riderAcceptOrderById(Long id, Long riderId, HttpServletRequest request);

    /**
     * 骑手根据id送达订单
     */
    CommonResponse riderDeliveredOrderById(Long id, HttpServletRequest request);

    /**
     * 顾客根据id确认订单
     */
    CommonResponse customerCompletedOrderById(Long id, HttpServletRequest request);

    /**
     * 查询待配送订单
     */
    CommonResponse selectToBeDeliveredOrders(HttpServletRequest request);

    /**
     * 根据骑手id和订单进度查询订单列表接口
     */
    CommonResponse selectOrdersByRiderIdAndProgress(Long riderId, String progress, HttpServletRequest request);

    /**
     * 根据骑手id查询今日和本月完成订单数量接口
     */
    CommonResponse selectOrderCompleteNumberByRiderId(Long riderId, HttpServletRequest request);

    /**
     * 查询已完成订单
     */
    CommonResponse selectCompletedOrders(HttpServletRequest request);
}
