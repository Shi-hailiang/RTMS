package com.shihailiang.rtmsbackend.service;

import com.shihailiang.response.CommonResponse;
import com.shihailiang.rtmsbackend.pojo.entity.Category;
import jakarta.servlet.http.HttpServletRequest;

public interface CategoryService {
    /**
     * 新增分类
     */
    CommonResponse createCategory(Category category, HttpServletRequest request);

    /**
     * 查询分类列表
     */
    CommonResponse selectCategories(HttpServletRequest request);

    /**
     * 根据店铺id查询分类列表
     */
    CommonResponse selectCategoriesByStoreId(Long storeId, HttpServletRequest request);

    /**
     * 修改分类
     */
    CommonResponse updateCategory(Category category, HttpServletRequest request);

    /**
     * 删除分类
     */
    CommonResponse deleteCategory(Long id, HttpServletRequest request);
}
