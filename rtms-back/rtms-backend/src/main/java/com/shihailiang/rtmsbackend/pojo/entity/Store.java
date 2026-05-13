package com.shihailiang.rtmsbackend.pojo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * (Store)实体类
 *
 * @author 石海良
 * @since 2023-12-20 09:51:27
 */
@Data
@TableName("tb_store")
public class Store implements Serializable {
    private static final long serialVersionUID = 3257034215554567632L;
    /**
     * 店铺id
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 账号id
     */
    private Long accountId;

    /**
     * 店铺名称
     */
    private String name;

    /**
     * 店铺logo
     */
    private String logo;

    /**
     * 店铺简介
     */
    private String description;

    /**
     * 电话号码
     */
    private String phone;

    /**
     * 店铺地址
     */
    private String address;

    /**
     * 营业时段
     */
    private String businessHour;

    /**
     * 营业执照
     */
    private String businessLicense;

    /**
     * 打包费用
     */
    private Long packagePrice;

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

    /**
     * 店铺类型
     */
    private String type;
}