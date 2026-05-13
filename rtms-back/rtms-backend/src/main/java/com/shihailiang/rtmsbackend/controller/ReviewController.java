package com.shihailiang.rtmsbackend.controller;

import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.shihailiang.response.CommonResponse;
import com.shihailiang.rtmsbackend.pojo.entity.Review;
import com.shihailiang.rtmsbackend.service.ReviewService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@Tag(name = "Review", description = "评价相关接口")
public class ReviewController {

    @Resource
    private ReviewService reviewService;

    @PostMapping("/api/create_review")
    @Operation(summary = "创建评价")
    @ApiOperationSupport(author = "石海良")
    public CommonResponse createReview(@RequestBody Review review, HttpServletRequest request) {
        log.info("CreateReview API is requested");
        return reviewService.createReview(review, request);
    }

    @GetMapping("/api/select_reviews_by_store_id/{storeId}")
    @Operation(summary = "根据店铺id查询评价列表")
    @ApiOperationSupport(author = "石海良")
    public CommonResponse selectReviewsByStoreId(@PathVariable Long storeId, HttpServletRequest request) {
        log.info("SelectReviewsByStoreId API is requested");
        return reviewService.selectReviewsByStoreId(storeId, request);
    }

    @GetMapping("/api/select_review_by_order_id/{orderId}")
    @Operation(summary = "根据订单id查询评价")
    @ApiOperationSupport(author = "石海良")
    public CommonResponse selectReviewByOrderId(@PathVariable Long orderId, HttpServletRequest request) {
        log.info("SelectReviewByOrderId API is requested");
        return reviewService.selectReviewByOrderId(orderId, request);
    }

    @DeleteMapping("/api/delete_review/{id}")
    @Operation(summary = "删除评价")
    @ApiOperationSupport(author = "石海良")
    public CommonResponse deleteReview(@PathVariable Long id, HttpServletRequest request) {
        log.info("DeleteReview API is requested");
        return reviewService.deleteReview(id, request);
    }
}
