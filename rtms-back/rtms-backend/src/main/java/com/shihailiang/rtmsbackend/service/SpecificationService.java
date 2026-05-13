package com.shihailiang.rtmsbackend.service;

import com.shihailiang.response.CommonResponse;
import com.shihailiang.rtmsbackend.pojo.entity.Specification;
import jakarta.servlet.http.HttpServletRequest;

public interface SpecificationService {
    /**
     * 新增规格
     */
    CommonResponse createSpecification(Specification specification, HttpServletRequest request);

    /**
     * 查询规格列表
     */
    CommonResponse selectSpecifications(HttpServletRequest request);

    /**
     * 根据产品id查询规格列表
     */
    CommonResponse selectSpecificationsByProductId(Long productId, HttpServletRequest request);

    /**
     * 修改规格
     */
    CommonResponse updateSpecification(Specification specification, HttpServletRequest request);

    /**
     * 删除规格
     */
    CommonResponse deleteSpecification(Long id, HttpServletRequest request);
}
