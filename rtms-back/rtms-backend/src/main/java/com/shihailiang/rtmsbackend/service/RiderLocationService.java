package com.shihailiang.rtmsbackend.service;

import com.shihailiang.response.CommonResponse;
import com.shihailiang.rtmsbackend.pojo.entity.RiderLocation;
import jakarta.servlet.http.HttpServletRequest;

public interface RiderLocationService {
    CommonResponse reportLocation(RiderLocation riderLocation, HttpServletRequest request);
    CommonResponse getLatestLocationByOrderId(Long orderId, HttpServletRequest request);
}
