package com.shihailiang.rtmsbackend.service;

import com.shihailiang.response.CommonResponse;
import com.shihailiang.rtmsbackend.pojo.entity.Complaint;
import jakarta.servlet.http.HttpServletRequest;

public interface ComplaintService {
    // 创建投诉
    CommonResponse createComplaint(Complaint complaint, HttpServletRequest request);
    
    // 查询用户的投诉列表
    CommonResponse selectComplaintsByAccountId(Long accountId, HttpServletRequest request);
    
    // 查询所有投诉(管理员)
    CommonResponse selectAllComplaints(HttpServletRequest request);
    
    // 处理投诉(管理员)
    CommonResponse handleComplaint(Long id, String result, String reply, HttpServletRequest request);
}
