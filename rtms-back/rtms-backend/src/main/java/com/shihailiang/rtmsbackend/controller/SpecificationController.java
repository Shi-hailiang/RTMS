package com.shihailiang.rtmsbackend.controller;

import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.shihailiang.response.CommonResponse;
import com.shihailiang.rtmsbackend.pojo.entity.Specification;
import com.shihailiang.rtmsbackend.service.SpecificationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@Tag(name = "Specification", description = "Specification相关接口")
public class SpecificationController {

    @Resource
    SpecificationService specificationService;

    @PostMapping("/api/create_specification")
    @Operation(summary = "新增规格接口")
    @ApiOperationSupport(author = "石海良")
    public CommonResponse createSpecification(@RequestBody Specification specification, HttpServletRequest request) {
        log.info("CreateSpecification API is requested");
        return specificationService.createSpecification(specification, request);
    }

    @GetMapping("/api/select_specification_by_product_id/{productId}")
    @Operation(summary = "根据产品id查询规格接口")
    @ApiOperationSupport(author = "石海良")
    public CommonResponse selectSpecificationsByProductId(@PathVariable Long productId, HttpServletRequest request) {
        log.info("SelectSpecificationsByProductId API is requested");
        return specificationService.selectSpecificationsByProductId(productId, request);
    }

    @PostMapping("/api/update_specification")
    @Operation(summary = "更新规格接口")
    @ApiOperationSupport(author = "石海良")
    public CommonResponse updateSpecification(@RequestBody Specification specification, HttpServletRequest request) {
        log.info("UpdateSpecification API is requested");
        return specificationService.updateSpecification(specification, request);
    }

    @DeleteMapping("/api/delete_specification/{id}")
    @Operation(summary = "删除规格接口")
    @ApiOperationSupport(author = "石海良")
    public CommonResponse deleteSpecification(@PathVariable Long id, HttpServletRequest request) {
        log.info("DeleteSpecification API is requested");
        return specificationService.deleteSpecification(id, request);
    }
}
