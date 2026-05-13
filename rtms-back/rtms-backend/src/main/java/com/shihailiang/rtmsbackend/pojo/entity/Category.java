package com.shihailiang.rtmsbackend.pojo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * (Category)实体类
 *
 * @author 石海良
 * @since 2023-12-21 09:51:27
 */
@Data
@TableName("tb_category")
public class Category implements Serializable {
    private static final long serialVersionUID = 3257034218854537652L;
    /**
     * 分类id
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 店铺id
     */
    private Long storeId;

    /**
     * 分类名称
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
