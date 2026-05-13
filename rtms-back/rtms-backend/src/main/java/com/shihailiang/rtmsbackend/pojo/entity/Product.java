package com.shihailiang.rtmsbackend.pojo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
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
@TableName("tb_product")
public class Product implements Serializable {
    private static final long serialVersionUID = 1967084631288537682L;
    /**
     * 产品id
     */
    @TableId(type = IdType.AUTO)
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
