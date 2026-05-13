package com.shihailiang.rtmsbackend.controller;

import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.shihailiang.response.CommonResponse;
import com.shihailiang.rtmsbackend.pojo.entity.Product;
import com.shihailiang.rtmsbackend.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@Tag(name = "Product", description = "Product相关接口")
public class ProductController {

    @Resource
    ProductService productService;

    @PostMapping("/api/create_product")
    @Operation(summary = "新增产品接口")
    @ApiOperationSupport(author = "石海良")
    public CommonResponse createProduct(@RequestBody Product product, HttpServletRequest request) {
        log.info("CreateProduct API is requested");
        return productService.createProduct(product, request);
    }

    @GetMapping("/api/select_product_by_id/{id}")
    @Operation(summary = "根据id查询产品接口")
    @ApiOperationSupport(author = "石海良")
    public CommonResponse selectProductById(@PathVariable Long id, HttpServletRequest request) {
        log.info("SelectProductById API is requested");
        return productService.selectProductById(id, request);
    }

    @GetMapping("/api/select_products_by_store_id/{storeId}")
    @Operation(summary = "根据店铺id查询产品接口")
    @ApiOperationSupport(author = "石海良")
    public CommonResponse selectProductsByStoreId(@PathVariable Long storeId, HttpServletRequest request) {
        log.info("SelectProductsByStoreId API is requested");
        return productService.selectProductsByStoreId(storeId, request);
    }

    @GetMapping("/api/select_products_by_category_id/{categoryId}")
    @Operation(summary = "根据分类id查询产品接口")
    @ApiOperationSupport(author = "石海良")
    public CommonResponse selectProductsByCategoryId(@PathVariable Long categoryId, HttpServletRequest request) {
        log.info("SelectProductsByCategoryId API is requested");
        return productService.selectProductsByCategoryId(categoryId, request);
    }

    @PostMapping("/api/update_product")
    @Operation(summary = "更新产品接口")
    @ApiOperationSupport(author = "石海良")
    public CommonResponse updateProduct(@RequestBody Product product, HttpServletRequest request) {
        log.info("UpdateProduct API is requested");
        return productService.updateProduct(product, request);
    }

    @DeleteMapping("/api/delete_product/{id}")
    @Operation(summary = "删除产品接口")
    @ApiOperationSupport(author = "石海良")
    public CommonResponse deleteProduct(@PathVariable Long id, HttpServletRequest request) {
        log.info("DeleteProduct API is requested");
        return productService.deleteProduct(id, request);
    }

    @GetMapping("/api/search_products")
    @Operation(summary = "根据关键词搜索产品接口")
    @ApiOperationSupport(author = "石海良")
    public CommonResponse searchProducts(@RequestParam String keyword, HttpServletRequest request) {
        log.info("SearchProducts API is requested, keyword: {}", keyword);
        return productService.searchProducts(keyword, request);
    }
}
