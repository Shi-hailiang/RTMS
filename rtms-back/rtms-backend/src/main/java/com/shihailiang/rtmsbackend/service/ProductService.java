package com.shihailiang.rtmsbackend.service;

import com.shihailiang.response.CommonResponse;
import com.shihailiang.rtmsbackend.pojo.entity.Product;
import jakarta.servlet.http.HttpServletRequest;

public interface ProductService {
    /**
     * 新增产品
     */
    CommonResponse createProduct(Product product, HttpServletRequest request);

    /**
     * 根据id查询产品
     */
    CommonResponse selectProductById(Long id, HttpServletRequest request);

    /**
     * 查询产品列表
     */
    CommonResponse selectProducts(HttpServletRequest request);

    /**
     * 根据店铺id查询产品列表
     */
    CommonResponse selectProductsByStoreId(Long storeId, HttpServletRequest request);

    /**
     * 根据分类id查询产品列表
     */
    CommonResponse selectProductsByCategoryId(Long categoryId, HttpServletRequest request);

    /**
     * 修改产品
     */
    CommonResponse updateProduct(Product product, HttpServletRequest request);

    /**
     * 删除产品
     */
    CommonResponse deleteProduct(Long id, HttpServletRequest request);

    /**
     * 根据关键词搜索产品
     */
    CommonResponse searchProducts(String keyword, HttpServletRequest request);
}
