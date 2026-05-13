package com.shihailiang.rtmsbackend.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.shihailiang.enumeration.StateCode;
import com.shihailiang.response.CommonResponse;
import com.shihailiang.response.ResponseUtils;
import com.shihailiang.rtmsbackend.mapper.CategoryMapper;
import com.shihailiang.rtmsbackend.pojo.entity.Category;
import com.shihailiang.rtmsbackend.service.CategoryService;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.shihailiang.constant.AccountConstant.*;
import static com.shihailiang.constant.StatusConstant.DELETED;
import static com.shihailiang.constant.StatusConstant.NORMAL;

@Slf4j
@Service
public class CategoryServiceImpl implements CategoryService {

    @Resource
    CategoryMapper categoryMapper;

    @Override
    public CommonResponse createCategory(Category category, HttpServletRequest request) {
        // 1. 校验数据是否合法
        String storeId = String.valueOf(category.getStoreId());
        String name = category.getName();
        // 店铺id为空
        if (storeId.isEmpty()) {
            return ResponseUtils.error(StateCode.STORE_ID_EMPTY);
        }
        // 分类名称为空
        if (name.isEmpty()) {
            return ResponseUtils.error(StateCode.CATEGORY_NAME_EMPTY);
        }
        //2. 如果id不为空查询分类名称是否存在
        if (category.getId() == null) {
            QueryWrapper<Category> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("store_id",storeId);
            queryWrapper.eq("name",name);
            // 分类已存在
            if (categoryMapper.selectOne(queryWrapper) != null){
                log.info("this category can not be existed");
                return ResponseUtils.error(StateCode.CATEGORY_NAME_EXIST);
            }
            log.info("this category can be created");
            // 将表单信息传入实体类
            category.setStatus(NORMAL);
            categoryMapper.insert(category);
        }
        return ResponseUtils.success();
    }

    @Override
    public CommonResponse selectCategories(HttpServletRequest request) {
        // 将数据库分类表内容取出
        QueryWrapper<Category> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("status",NORMAL);
        List<Category> categories = categoryMapper.selectList(queryWrapper);
        return ResponseUtils.success(categories);
    }

    @Override
    public CommonResponse selectCategoriesByStoreId(Long storeId, HttpServletRequest request) {
        // 将数据库分类表内容取出
        QueryWrapper<Category> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("store_id",storeId);
        queryWrapper.eq("status",NORMAL);
        List<Category> categories = categoryMapper.selectList(queryWrapper);
        return ResponseUtils.success(categories);
    }

    @Override
    public CommonResponse updateCategory(Category category, HttpServletRequest request) {
        UpdateWrapper<Category> updateWrapper = new UpdateWrapper<>();
        updateWrapper.set("name", category.getName());  // 设置要更新的字段及值
        updateWrapper.eq("id", category.getId());  // 设置更新条件，这里假设使用 ID 作为更新条件
        categoryMapper.update(null, updateWrapper);
        return ResponseUtils.success();
    }

    @Override
    public CommonResponse deleteCategory(Long id, HttpServletRequest request) {
        UpdateWrapper<Category> updateWrapper = new UpdateWrapper<>();
        updateWrapper.set("status", DELETED);  // 设置要更新的字段及值
        updateWrapper.eq("id", id);  // 设置更新条件，这里假设使用 ID 作为更新条件
        categoryMapper.update(null, updateWrapper);
        return ResponseUtils.success();
    }
}
