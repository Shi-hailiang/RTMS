package com.shihailiang.rtmsbackend.controller;

import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.shihailiang.response.CommonResponse;
import com.shihailiang.rtmsbackend.pojo.entity.Category;
import com.shihailiang.rtmsbackend.service.CategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@Tag(name = "Category", description = "Category相关接口")
public class CategoryController {

    @Resource
    CategoryService categoryService;

    @PostMapping("/api/create_category")
    @Operation(summary = "新增分类接口")
    @ApiOperationSupport(author = "石海良")
    public CommonResponse createCategory(@RequestBody Category category, HttpServletRequest request) {
        log.info("CreateCategory API is requested");
        return categoryService.createCategory(category, request);
    }

    @GetMapping("/api/select_categories_by_store_id/{storeId}")
    @Operation(summary = "根据店铺id查询分类接口")
    @ApiOperationSupport(author = "石海良")
    public CommonResponse selectCategoriesByStoreId(@PathVariable Long storeId, HttpServletRequest request) {
        log.info("SelectCategoriesByStoreId API is requested");
        return categoryService.selectCategoriesByStoreId(storeId, request);
    }

    @PostMapping("/api/update_category")
    @Operation(summary = "更新分类接口")
    @ApiOperationSupport(author = "石海良")
    public CommonResponse updateCategory(@RequestBody Category category, HttpServletRequest request) {
        log.info("UpdateCategory API is requested");
        return categoryService.updateCategory(category, request);
    }

    @DeleteMapping("/api/delete_category/{id}")
    @Operation(summary = "删除分类接口")
    @ApiOperationSupport(author = "石海良")
    public CommonResponse deleteCategory(@PathVariable Long id, HttpServletRequest request) {
        log.info("DeleteCategory API is requested");
        return categoryService.deleteCategory(id, request);
    }
}
