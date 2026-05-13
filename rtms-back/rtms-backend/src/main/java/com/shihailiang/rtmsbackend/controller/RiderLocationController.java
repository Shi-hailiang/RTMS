package com.shihailiang.rtmsbackend.controller;

import com.shihailiang.response.CommonResponse;
import com.shihailiang.rtmsbackend.pojo.entity.RiderLocation;
import com.shihailiang.rtmsbackend.service.RiderLocationService;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@Tag(name = "RiderLocation", description = "骑手位置相关接口")
public class RiderLocationController {

    @Resource
    RiderLocationService riderLocationService;

    @PostMapping("/api/report_rider_location")
    @Operation(summary = "骑手上报位置接口")
    @ApiOperationSupport(author = "石海良")
    public CommonResponse reportRiderLocation(@RequestBody RiderLocation riderLocation, HttpServletRequest request) {
        log.info("ReportRiderLocation API is requested");
        return riderLocationService.reportLocation(riderLocation, request);
    }

    @GetMapping("/api/get_rider_location_by_order_id/{orderId}")
    @Operation(summary = "根据订单id获取骑手位置接口")
    @ApiOperationSupport(author = "石海良")
    public CommonResponse getRiderLocationByOrderId(@PathVariable Long orderId, HttpServletRequest request) {
        log.info("GetRiderLocationByOrderId API is requested");
        return riderLocationService.getLatestLocationByOrderId(orderId, request);
    }
}
