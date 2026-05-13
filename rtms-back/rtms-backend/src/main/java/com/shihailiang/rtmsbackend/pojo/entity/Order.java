package com.shihailiang.rtmsbackend.pojo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * (Order)实体类
 *
 * @author 石海良
 * @since 2024-02-24 00:08:12
 */
@Data
@TableName("tb_order")
public class Order implements Serializable {
    private static final long serialVersionUID = 1167084826941582132L;
    /**
     * 订单id
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 账号id
     */
    private Long accountId;

    /**
     * 店铺id
     */
    private Long storeId;

    /**
     * 骑手id
     */
    private Long riderId;

    /**
     * 订单号码
     */
    private String code;

    /**
     * 订单备注
     */
    private String note;

    /**
     * 期望时间
     */
    private String expectedTime;

    /**
     * 配送地址
     */
    private String address;

    /**
     * 顾客电话
     */
    private String phone;

    /**
     * 打包费用
     */
    private Long packagePrice;

    /**
     * 配送费用
     */
    private Long deliveryPrice;

    /**
     * 合计费用
     */
    private Long totalPrice;

    /**
     * 支付方式
     */
    private String payment;

    /**
     * 订单进度
     */
    private String progress;

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
