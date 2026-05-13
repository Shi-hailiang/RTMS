package com.shihailiang.rtmsbackend.controller;

import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.shihailiang.response.CommonResponse;
import com.shihailiang.rtmsbackend.pojo.entity.OrderProduct;
import com.shihailiang.rtmsbackend.service.OrderProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@Tag(name = "OrderProduct", description = "OrderProduct相关接口")
public class OrderProductController {

    @Resource
    OrderProductService orderProductService;

    @PostMapping("/api/create_order_product")
    @Operation(summary = "新增订单产品接口")
    @ApiOperationSupport(author = "石海良")
    public CommonResponse createOrderProduct(@RequestBody OrderProduct orderProduct, HttpServletRequest request) {
        log.info("CreateOrderProduct API is requested");
        return orderProductService.createOrderProduct(orderProduct, request);
    }

    @GetMapping("/api/select_order_products_by_order_id/{orderId}")
    @Operation(summary = "根据订单id查询订单产品接口")
    @ApiOperationSupport(author = "石海良")
    public CommonResponse selectOrderProductsByOrderId(@PathVariable Long orderId, HttpServletRequest request) {
        log.info("SelectOrderProductsByOrderId API is requested");
        return orderProductService.selectOrderProductsByOrderId(orderId, request);
    }
}
