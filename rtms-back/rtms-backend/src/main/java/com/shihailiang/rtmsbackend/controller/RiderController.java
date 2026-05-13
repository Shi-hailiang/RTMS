package com.shihailiang.rtmsbackend.controller;

import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.shihailiang.response.CommonResponse;
import com.shihailiang.rtmsbackend.pojo.entity.Rider;
import com.shihailiang.rtmsbackend.service.RiderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@Tag(name = "Rider", description = "Rider相关接口")
public class RiderController {

    @Resource
    RiderService riderService;

    @GetMapping("/api/select_riders")
    @Operation(summary = "查询骑手列表接口")
    @ApiOperationSupport(author = "石海良")
    public CommonResponse selectRiders(HttpServletRequest request) {
        log.info("SelectRiders API is requested");
        return riderService.selectRiders(request);
    }

    @GetMapping("/api/select_rider_by_id/{id}")
    @Operation(summary = "根据id查询骑手接口")
    @ApiOperationSupport(author = "石海良")
    public CommonResponse selectRiderById(@PathVariable Long id, HttpServletRequest request) {
        log.info("SelectRiderById API is requested");
        return riderService.selectRiderById(id, request);
    }

    @GetMapping("/api/select_rider_by_account_id/{accountId}")
    @Operation(summary = "根据账号id查询骑手接口")
    @ApiOperationSupport(author = "石海良")
    public CommonResponse selectRiderByAccountId(@PathVariable Long accountId, HttpServletRequest request) {
        log.info("SelectRiderByAccountId API is requested");
        return riderService.selectRiderByAccountId(accountId, request);
    }

    @PostMapping("/api/create_rider")
    @Operation(summary = "新增骑手接口")
    @ApiOperationSupport(author = "石海良")
    public CommonResponse createRider(@RequestBody Rider rider, HttpServletRequest request) {
        log.info("CreateRider API is requested");
        return riderService.createRider(rider, request);
    }

    @PostMapping("/api/update_rider")
    @Operation(summary = "修改骑手接口")
    @ApiOperationSupport(author = "石海良")
    public CommonResponse updateRider(@RequestBody Rider rider, HttpServletRequest request) {
        log.info("UpdateRider API is requested");
        return riderService.updateRider(rider, request);
    }

    @DeleteMapping("/api/delete_rider/{id}")
    @Operation(summary = "删除骑手接口")
    @ApiOperationSupport(author = "石海良")
    public CommonResponse deleteRider(@PathVariable Long id, HttpServletRequest request) {
        log.info("DeleteRider API is requested");
        return riderService.deleteRider(id, request);
    }
}
