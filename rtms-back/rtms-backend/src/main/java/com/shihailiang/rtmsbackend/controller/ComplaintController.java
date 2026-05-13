package com.shihailiang.rtmsbackend.controller;

import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.shihailiang.response.CommonResponse;
import com.shihailiang.rtmsbackend.pojo.entity.Complaint;
import com.shihailiang.rtmsbackend.service.ComplaintService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Slf4j
@RestController
@Tag(name = "Complaint", description = "投诉相关接口")
public class ComplaintController {

    @Resource
    private ComplaintService complaintService;

    @PostMapping("/api/create_complaint")
    @Operation(summary = "创建投诉")
    @ApiOperationSupport(author = "石海良")
    public CommonResponse createComplaint(@RequestBody Complaint complaint, HttpServletRequest request) {
        log.info("CreateComplaint API is requested");
        return complaintService.createComplaint(complaint, request);
    }

    @GetMapping("/api/select_complaints_by_account_id/{accountId}")
    @Operation(summary = "根据用户id查询投诉列表")
    @ApiOperationSupport(author = "石海良")
    public CommonResponse selectComplaintsByAccountId(@PathVariable Long accountId, HttpServletRequest request) {
        log.info("SelectComplaintsByAccountId API is requested");
        return complaintService.selectComplaintsByAccountId(accountId, request);
    }

    @GetMapping("/api/select_all_complaints")
    @Operation(summary = "查询所有投诉(管理员)")
    @ApiOperationSupport(author = "石海良")
    public CommonResponse selectAllComplaints(HttpServletRequest request) {
        log.info("SelectAllComplaints API is requested");
        return complaintService.selectAllComplaints(request);
    }

    @PostMapping("/api/handle_complaint")
    @Operation(summary = "处理投诉(管理员)")
    @ApiOperationSupport(author = "石海良")
    public CommonResponse handleComplaint(@RequestBody Map<String, Object> params, HttpServletRequest request) {
        log.info("HandleComplaint API is requested");
        Long id = Long.valueOf(params.get("id").toString());
        String result = params.get("result").toString();
        String reply = params.get("reply").toString();
        return complaintService.handleComplaint(id, result, reply, request);
    }
}
