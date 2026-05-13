package com.shihailiang.rtmsbackend.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.shihailiang.enumeration.StateCode;
import com.shihailiang.response.CommonResponse;
import com.shihailiang.response.ResponseUtils;
import com.shihailiang.rtmsbackend.mapper.SpecificationMapper;
import com.shihailiang.rtmsbackend.pojo.entity.Specification;
import com.shihailiang.rtmsbackend.service.SpecificationService;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.shihailiang.constant.StatusConstant.DELETED;
import static com.shihailiang.constant.StatusConstant.NORMAL;

@Slf4j
@Service
public class SpecificationServiceImpl implements SpecificationService {

    @Resource
    SpecificationMapper specificationMapper;

    @Override
    public CommonResponse createSpecification(Specification specification, HttpServletRequest request) {
        // 1. 校验数据是否合法
        String productId = String.valueOf(specification.getProductId());
        String type = specification.getType();
        String name = specification.getName();
        // 产品id为空
        if (productId.isEmpty()) {
            return ResponseUtils.error(StateCode.PRODUCT_ID_EMPTY);
        }
        // 规格类型为空
        if (type.isEmpty()) {
            return ResponseUtils.error(StateCode.SPECIFICATION_TYPE_EMPTY);
        }
        // 规格名称为空
        if (name.isEmpty()) {
            return ResponseUtils.error(StateCode.SPECIFICATION_NAME_EMPTY);
        }
        // 将表单信息传入实体类
        specification.setStatus(NORMAL);
        specificationMapper.insert(specification);
        return ResponseUtils.success();
    }

    @Override
    public CommonResponse selectSpecifications(HttpServletRequest request) {
        return null;
    }

    @Override
    public CommonResponse selectSpecificationsByProductId(Long productId, HttpServletRequest request) {
        // 将数据库规格表内容取出
        QueryWrapper<Specification> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("product_id",productId);
        queryWrapper.eq("status",NORMAL);
        List<Specification> specifications = specificationMapper.selectList(queryWrapper);
        return ResponseUtils.success(specifications);
    }

    @Override
    public CommonResponse updateSpecification(Specification specification, HttpServletRequest request) {
        UpdateWrapper<Specification> updateWrapper = new UpdateWrapper<>();
        updateWrapper.set("type", specification.getType());  // 设置要更新的字段及值
        updateWrapper.set("name", specification.getName());
        updateWrapper.eq("id", specification.getId());  // 设置更新条件，这里假设使用 ID 作为更新条件
        specificationMapper.update(null, updateWrapper);
        return ResponseUtils.success();
    }

    @Override
    public CommonResponse deleteSpecification(Long id, HttpServletRequest request) {
        UpdateWrapper<Specification> updateWrapper = new UpdateWrapper<>();
        updateWrapper.set("status", DELETED);  // 设置要更新的字段及值
        updateWrapper.eq("id", id);  // 设置更新条件，这里假设使用 ID 作为更新条件
        specificationMapper.update(null, updateWrapper);
        return ResponseUtils.success();
    }
}
