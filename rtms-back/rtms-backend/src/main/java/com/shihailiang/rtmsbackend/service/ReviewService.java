package com.shihailiang.rtmsbackend.service;

import com.shihailiang.response.CommonResponse;
import com.shihailiang.rtmsbackend.pojo.entity.Review;
import jakarta.servlet.http.HttpServletRequest;

public interface ReviewService {
    CommonResponse createReview(Review review, HttpServletRequest request);
    CommonResponse selectReviewsByStoreId(Long storeId, HttpServletRequest request);
    CommonResponse selectReviewByOrderId(Long orderId, HttpServletRequest request);
    CommonResponse deleteReview(Long id, HttpServletRequest request);
}
