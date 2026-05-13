package com.shihailiang.rtmsbackend.controller;

import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.shihailiang.response.CommonResponse;
import com.shihailiang.rtmsbackend.pojo.entity.Order;
import com.shihailiang.rtmsbackend.service.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@Tag(name = "Order", description = "Order相关接口")
public class OrderController {

    @Resource
    OrderService orderService;

    @PostMapping("/api/create_order")
    @Operation(summary = "新增订单接口")
    @ApiOperationSupport(author = "石海良")
    public CommonResponse createOrder(@RequestBody Order order, HttpServletRequest request) {
        log.info("CreateOrder API is requested");
        return orderService.createOrder(order, request);
    }

    @GetMapping("/api/select_order_by_id/{id}")
    @Operation(summary = "根据id查询订单接口")
    @ApiOperationSupport(author = "石海良")
    public CommonResponse selectOrderById(@PathVariable Long id, HttpServletRequest request) {
        log.info("SelectOrderById API is requested");
        return orderService.selectOrderById(id, request);
    }

    @GetMapping("/api/select_orders_by_store_id/{storeId}")
    @Operation(summary = "根据店铺id查询订单列表接口")
    @ApiOperationSupport(author = "石海良")
    public CommonResponse selectOrdersByStoreId(@PathVariable Long storeId, HttpServletRequest request) {
        log.info("SelectOrdersByStoreId API is requested");
        return orderService.selectOrdersByStoreId(storeId, request);
    }

    @GetMapping("/api/select_orders_by_account_id/{accountId}")
    @Operation(summary = "根据账号id查询订单列表接口")
    @ApiOperationSupport(author = "石海良")
    public CommonResponse selectOrdersByAccountId(@PathVariable Long accountId, HttpServletRequest request) {
        log.info("SelectOrdersByAccountId API is requested");
        return orderService.selectOrdersByAccountId(accountId, request);
    }

    @GetMapping("/api/select_pending_payment_orders_by_account_id/{accountId}")
    @Operation(summary = "根据账号id查询待支付订单列表接口")
    @ApiOperationSupport(author = "石海良")
    public CommonResponse selectPendingPaymentOrdersByAccountId(@PathVariable Long accountId, HttpServletRequest request) {
        log.info("SelectPendingPaymentOrdersByAccountId API is requested");
        return orderService.selectPendingPaymentOrdersByAccountId(accountId, request);
    }

    @GetMapping("/api/select_delivering_orders_by_account_id/{accountId}")
    @Operation(summary = "根据账号id查询配送中订单列表接口")
    @ApiOperationSupport(author = "石海良")
    public CommonResponse selectDeliveringOrdersByAccountId(@PathVariable Long accountId, HttpServletRequest request) {
        log.info("SelectDeliveringOrdersByAccountId API is requested");
        return orderService.selectDeliveringOrdersByAccountId(accountId, request);
    }

    @GetMapping("/api/select_completed_orders_by_account_id/{accountId}")
    @Operation(summary = "根据账号id查询已完成订单列表接口")
    @ApiOperationSupport(author = "石海良")
    public CommonResponse selectCompletedOrdersByAccountId(@PathVariable Long accountId, HttpServletRequest request) {
        log.info("SelectCompletedOrdersByAccountId API is requested");
        return orderService.selectCompletedOrdersByAccountId(accountId, request);
    }

    @PostMapping("/api/cancel_order_by_id/{id}")
    @Operation(summary = "取消订单接口")
    @ApiOperationSupport(author = "石海良")
    public CommonResponse cancelOrderById(@PathVariable Long id, HttpServletRequest request) {
        log.info("CancelOrderById API is requested");
        return orderService.cancelOrderById(id, request);
    }

    @PostMapping("/api/cancel_order_by_id_and_money_back/{id}")
    @Operation(summary = "取消订单并退款接口")
    @ApiOperationSupport(author = "石海良")
    public CommonResponse cancelOrderByIdAndMoneyBack(@PathVariable Long id, HttpServletRequest request) {
        log.info("CancelOrderByIdAndMoneyBack API is requested");
        return orderService.cancelOrderByIdAndMoneyBack(id, request);
    }


    @PostMapping("/api/customer_delete_order_by_id/{id}")
    @Operation(summary = "顾客删除订单接口")
    @ApiOperationSupport(author = "石海良")
    public CommonResponse customerDeleteOrderById(@PathVariable Long id, HttpServletRequest request) {
        log.info("CustomerDeleteOrderById API is requested");
        return orderService.customerDeleteOrderById(id, request);
    }

    @PostMapping("/api/customer_payment_order_by_id/{id}")
    @Operation(summary = "顾客支付订单接口")
    @ApiOperationSupport(author = "石海良")
    public CommonResponse customerPaymentOrderById(@PathVariable Long id, HttpServletRequest request) {
        log.info("CustomerPaymentOrderById API is requested");
        return orderService.customerPaymentOrderById(id, request);
    }

    @PostMapping("/api/store_accept_order_by_id/{id}")
    @Operation(summary = "商家接受订单接口")
    @ApiOperationSupport(author = "石海良")
    public CommonResponse storeAcceptOrderById(@PathVariable Long id, HttpServletRequest request) {
        log.info("StoreAcceptOrderById API is requested");
        return orderService.storeAcceptOrderById(id, request);
    }

    @PostMapping("/api/store_out_order_by_id/{id}")
    @Operation(summary = "商家出餐接口")
    @ApiOperationSupport(author = "石海良")
    public CommonResponse storeOutOrderById(@PathVariable Long id, HttpServletRequest request) {
        log.info("StoreOutOrderById API is requested");
        return orderService.storeOutOrderById(id, request);
    }

    @PostMapping("/api/rider_accept_order_by_id/{id}/{riderId}")
    @Operation(summary = "骑手接受订单接口")
    @ApiOperationSupport(author = "石海良")
    public CommonResponse riderAcceptOrderById(@PathVariable Long id, @PathVariable Long riderId, HttpServletRequest request) {
        log.info("RiderAcceptOrderById API is requested");
        return orderService.riderAcceptOrderById(id, riderId, request);
    }

    @PostMapping("/api/rider_delivered_order_by_id/{id}")
    @Operation(summary = "骑手送达订单接口")
    @ApiOperationSupport(author = "石海良")
    public CommonResponse riderDeliveredOrderById(@PathVariable Long id, HttpServletRequest request) {
        log.info("RiderDeliveredOrderById API is requested");
        return orderService.riderDeliveredOrderById(id, request);
    }

    @PostMapping("/api/customer_completed_order_by_id/{id}")
    @Operation(summary = "顾客完成订单接口")
    @ApiOperationSupport(author = "石海良")
    public CommonResponse customerCompletedOrderById(@PathVariable Long id, HttpServletRequest request) {
        log.info("CustomerCompletedOrderById API is requested");
        return orderService.customerCompletedOrderById(id, request);
    }

    @GetMapping("/api/select_to_be_delivered_orders")
    @Operation(summary = "查询待配送订单列表接口")
    @ApiOperationSupport(author = "石海良")
    public CommonResponse selectToBeDeliveredOrders(HttpServletRequest request) {
        log.info("SelectToBeDeliveredOrders API is requested");
        return orderService.selectToBeDeliveredOrders(request);
    }

    @GetMapping("/api/select_orders_by_rider_id_and_progress/{riderId}/{progress}")
    @Operation(summary = "根据骑手id和订单进度查询订单列表接口")
    @ApiOperationSupport(author = "石海良")
    public CommonResponse selectOrdersByRiderIdAndProgress(@PathVariable Long riderId, @PathVariable String progress, HttpServletRequest request) {
        log.info("SelectOrdersByRiderIdAndProgress API is requested");
        return orderService.selectOrdersByRiderIdAndProgress(riderId, progress, request);
    }

    @GetMapping("/api/select_order_complete_number_by_rider_id/{riderId}")
    @Operation(summary = "根据骑手id查询今日和本月已完成订单接口")
    @ApiOperationSupport(author = "石海良")
    public CommonResponse selectOrderCompleteNumberByRiderId(@PathVariable Long riderId, HttpServletRequest request) {
        log.info("SelectOrderCompleteNumberByRiderId API is requested");
        return orderService.selectOrderCompleteNumberByRiderId(riderId, request);
    }

    @GetMapping("/api/select_completed_orders")
    @Operation(summary = "查询已完成订单接口")
    @ApiOperationSupport(author = "石海良")
    public CommonResponse selectCompletedOrders(HttpServletRequest request) {
        log.info("SelectCompletedOrders API is requested");
        return orderService.selectCompletedOrders(request);
    }
    
}
