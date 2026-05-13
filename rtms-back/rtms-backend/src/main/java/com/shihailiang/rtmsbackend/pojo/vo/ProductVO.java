package com.shihailiang.rtmsbackend.pojo.vo;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * (Product)实体类
 *
 * @author 石海良
 * @since 2024-02-24 00:08:12
 */

@Data
@TableName(value = "v_product")
public class ProductVO implements Serializable {
    private static final long serialVersionUID = 6367084612296527682L;
    /**
     * 产品id
     */
    private Long id;

    /**
     * 店铺id
     */
    private Long storeId;

    /**
     * 分类id
     */
    private Long categoryId;

    /**
     * 分类名称
     */
    private String categoryName;

    /**
     * 产品名称
     */
    private String name;

    /**
     * 产品单价
     */
    private Long price;

    /**
     * 产品单位
     */
    private String unit;

    /**
     * 产品简介
     */
    private String description;

    /**
     * 产品图片
     */
    private String picture;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 状态
     */
    private String status;
}
