package com.shihailiang.rtmsbackend.pojo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * (Specification)实体类
 *
 * @author 石海良
 * @since 2024-02-24 00:08:12
 */
@Data
@TableName("tb_specification")
public class Specification implements Serializable {
    private static final long serialVersionUID = 137744631568538681L;
    /**
     * 规格id
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 产品id
     */
    private Long productId;

    /**
     * 规格类型
     */
    private String type;

    /**
     * 规格名称
     */
    private String name;

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
