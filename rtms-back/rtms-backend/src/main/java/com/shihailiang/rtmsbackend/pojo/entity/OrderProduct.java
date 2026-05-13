package com.shihailiang.rtmsbackend.pojo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * (OrderProduct)实体类
 *
 * @author 石海良
 * @since 2024-02-24 00:08:12
 */
@Data
@TableName("tb_order_product")
public class OrderProduct implements Serializable {
    private static final long serialVersionUID = 6519484823416581131L;
    /**
     * 订单产品id
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 订单id
     */
    private Long orderId;

    /**
     * 产品id
     */
    private Long productId;

    /**
     * 产品名称
     */
    private String name;

    /**
     * 产品单价
     */
    private Long price;

    /**
     * 产品数量
     */
    private Long number;

    /**
     * 产品规格
     */
    private String specification;

    /**
     * 合计费用
     */
    private Long totalPrice;

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
