package com.shihailiang.rtmsbackend.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.shihailiang.enumeration.StateCode;
import com.shihailiang.response.CommonResponse;
import com.shihailiang.response.ResponseUtils;
import com.shihailiang.rtmsbackend.mapper.ProductMapper;
import com.shihailiang.rtmsbackend.mapper.ProductVOMapper;
import com.shihailiang.rtmsbackend.pojo.entity.Product;
import com.shihailiang.rtmsbackend.pojo.vo.ProductVO;
import com.shihailiang.rtmsbackend.service.ProductService;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.shihailiang.constant.StatusConstant.DELETED;
import static com.shihailiang.constant.StatusConstant.NORMAL;

@Slf4j
@Service
public class ProductServiceImpl implements ProductService {

    @Resource
    ProductMapper productMapper;

    @Resource
    ProductVOMapper productVOMapper;

    @Override
    public CommonResponse createProduct(Product product, HttpServletRequest request) {
        // 1. 校验数据是否合法
        String storeId = String.valueOf(product.getStoreId());
        String categoryId = String.valueOf(product.getCategoryId());
        // 店铺id为空
        if (storeId.isEmpty()) {
            return ResponseUtils.error(StateCode.STORE_ID_EMPTY);
        }
        // 分类id为空
        if (categoryId.isEmpty()) {
            return ResponseUtils.error(StateCode.CATEGORY_ID_EMPTY);
        }

        // 2. 如果分类存在则查询产品名是否存在
        if (product.getId() == null) {
            QueryWrapper<Product> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("store_id",storeId);
            queryWrapper.eq("name",product.getName());
            queryWrapper.eq("price",product.getPrice());
            queryWrapper.eq("unit",product.getUnit());
            queryWrapper.eq("description",product.getDescription());
            queryWrapper.eq("picture",product.getPicture());
            // 产品已存在
            if (productMapper.selectOne(queryWrapper) != null){
                log.info("this product can not be existed");
                return ResponseUtils.error(StateCode.PRODUCT_EXIST);
            }
            log.info("this product can be created");
            // 将表单信息传入实体类
            product.setStatus(NORMAL);
            productMapper.insert(product);
        }
        return ResponseUtils.success();
    }

    @Override
    public CommonResponse selectProductById(Long id, HttpServletRequest request) {
        // 将数据库产品表内容取出
        QueryWrapper<Product> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id",id);
        Product product = productMapper.selectOne(queryWrapper);
        return ResponseUtils.success(product);
    }

    @Override
    public CommonResponse selectProducts(HttpServletRequest request) {
        return null;
    }

    @Override
    public CommonResponse selectProductsByStoreId(Long storeId, HttpServletRequest request) {
        // 将数据库产品表内容取出
        QueryWrapper<ProductVO> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("store_id",storeId);
        queryWrapper.eq("status",NORMAL);
        List<ProductVO> products = productVOMapper.selectList(queryWrapper);
        return ResponseUtils.success(products);
    }

    @Override
    public CommonResponse selectProductsByCategoryId(Long categoryId, HttpServletRequest request) {
        // 将数据库产品表内容取出
        QueryWrapper<Product> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("category_id",categoryId);
        queryWrapper.eq("status",NORMAL);
        List<Product> products = productMapper.selectList(queryWrapper);
        return ResponseUtils.success(products);
    }

    @Override
    public CommonResponse updateProduct(Product product, HttpServletRequest request) {
        UpdateWrapper<Product> updateWrapper = new UpdateWrapper<>();
        updateWrapper.set("category_id", product.getCategoryId());  // 设置要更新的字段及值
        updateWrapper.set("name", product.getName());
        updateWrapper.set("price", product.getPrice());
        updateWrapper.set("unit", product.getUnit());
        updateWrapper.set("description", product.getDescription());
        updateWrapper.set("picture", product.getPicture());
        updateWrapper.eq("id", product.getId());  // 设置更新条件，这里假设使用 ID 作为更新条件
        productMapper.update(null, updateWrapper);
        return ResponseUtils.success();
    }

    @Override
    public CommonResponse deleteProduct(Long id, HttpServletRequest request) {
        UpdateWrapper<Product> updateWrapper = new UpdateWrapper<>();
        updateWrapper.set("status", DELETED);  // 设置要更新的字段及值
        updateWrapper.eq("id", id);  // 设置更新条件，这里假设使用 ID 作为更新条件
        productMapper.update(null, updateWrapper);
        return ResponseUtils.success();
    }

    @Override
    public CommonResponse searchProducts(String keyword, HttpServletRequest request) {
        // 根据关键词模糊搜索产品名称和描述
        QueryWrapper<ProductVO> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("status", NORMAL);
        queryWrapper.and(wrapper -> wrapper
                .like("name", keyword)
                .or()
                .like("description", keyword)
        );
        queryWrapper.orderByDesc("create_time");
        List<ProductVO> products = productVOMapper.selectList(queryWrapper);
        return ResponseUtils.success(products);
    }
}
