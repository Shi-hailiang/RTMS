package com.shihailiang.rtmsbackend.controller;

import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.shihailiang.response.CommonResponse;
import com.shihailiang.rtmsbackend.pojo.vo.SystemVO;
import com.shihailiang.rtmsbackend.service.SystemService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@Tag(name = "System", description = "System相关接口")
public class SystemController {

    @Resource
    SystemService systemService;

    @GetMapping("/api/select_system")
    @Operation(summary = "查询系统信息接口")
    @ApiOperationSupport(author = "石海良")
    public CommonResponse selectSystem(HttpServletRequest request) {
        log.info("SelectSystem API is requested");
        return systemService.selectSystem(request);
    }

    @PostMapping("/api/update_system")
    @Operation(summary = "修改系统信息接口")
    @ApiOperationSupport(author = "石海良")
    public CommonResponse updateSystem(@RequestBody  SystemVO systemVO, HttpServletRequest request) {
        log.info("UpdateSystem API is requested");
        return systemService.updateSystem(systemVO, request);
    }
}
