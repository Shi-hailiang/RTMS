package com.shihailiang.rtmsbackend.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.shihailiang.response.CommonResponse;
import com.shihailiang.response.ResponseUtils;
import com.shihailiang.rtmsbackend.mapper.AccountMapper;
import com.shihailiang.rtmsbackend.mapper.ReviewMapper;
import com.shihailiang.rtmsbackend.pojo.entity.Account;
import com.shihailiang.rtmsbackend.pojo.entity.Review;
import com.shihailiang.rtmsbackend.pojo.vo.ReviewVO;
import com.shihailiang.rtmsbackend.service.ReviewService;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static com.shihailiang.constant.StatusConstant.DELETED;
import static com.shihailiang.constant.StatusConstant.NORMAL;

@Slf4j
@Service
public class ReviewServiceImpl implements ReviewService {

    @Resource
    private ReviewMapper reviewMapper;

    @Resource
    private AccountMapper accountMapper;

    @Override
    public CommonResponse createReview(Review review, HttpServletRequest request) {
        // 检查是否已评价
        QueryWrapper<Review> checkWrapper = new QueryWrapper<>();
        checkWrapper.eq("order_id", review.getOrderId());
        checkWrapper.ne("status", DELETED);
        if (reviewMapper.selectOne(checkWrapper) != null) {
            return ResponseUtils.error(500, "该订单已评价");
        }
        review.setStatus(NORMAL);
        reviewMapper.insert(review);
        return ResponseUtils.success();
    }

    @Override
    public CommonResponse selectReviewsByStoreId(Long storeId, HttpServletRequest request) {
        QueryWrapper<Review> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("store_id", storeId);
        queryWrapper.eq("status", NORMAL);
        queryWrapper.orderByDesc("create_time");
        List<Review> reviews = reviewMapper.selectList(queryWrapper);
        
        log.info("Found {} reviews for store {}", reviews.size(), storeId);
        
        // 转换为VO，添加用户信息
        List<ReviewVO> reviewVOs = new ArrayList<>();
        for (Review review : reviews) {
            ReviewVO vo = new ReviewVO();
            BeanUtils.copyProperties(review, vo);
            log.info("Review id: {}, accountId: {}, content: {}, rating: {}", 
                review.getId(), review.getAccountId(), review.getContent(), review.getRating());
            // 查询用户信息
            if (review.getAccountId() != null) {
                Account account = accountMapper.selectById(review.getAccountId());
                if (account != null) {
                    vo.setNickname(account.getNickname() != null ? account.getNickname() : "用户" + account.getId());
                    vo.setAvatar(account.getAvatar());
                    log.info("Account found: nickname={}, avatar={}", account.getNickname(), account.getAvatar());
                } else {
                    vo.setNickname("匿名用户");
                    log.info("Account not found for id: {}", review.getAccountId());
                }
            } else {
                vo.setNickname("匿名用户");
                log.info("AccountId is null for review: {}", review.getId());
            }
            reviewVOs.add(vo);
        }
        return ResponseUtils.success(reviewVOs);
    }

    @Override
    public CommonResponse selectReviewByOrderId(Long orderId, HttpServletRequest request) {
        QueryWrapper<Review> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("order_id", orderId);
        queryWrapper.ne("status", DELETED);
        Review review = reviewMapper.selectOne(queryWrapper);
        return ResponseUtils.success(review);
    }

    @Override
    public CommonResponse deleteReview(Long id, HttpServletRequest request) {
        UpdateWrapper<Review> updateWrapper = new UpdateWrapper<>();
        updateWrapper.set("status", DELETED);
        updateWrapper.eq("id", id);
        reviewMapper.update(null, updateWrapper);
        return ResponseUtils.success();
    }
}
