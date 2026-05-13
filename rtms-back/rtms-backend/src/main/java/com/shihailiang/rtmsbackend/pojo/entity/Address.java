package com.shihailiang.rtmsbackend.pojo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * (Address)实体类
 *
 * @author 石海良
 * @since 2023-12-14 22:20:27
 */
@Data
@TableName("tb_address")
public class Address implements Serializable {
    private static final long serialVersionUID = 5392676142605847537L;
    /**
     * 地址id
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 用户id
     */
    private Long accountId;

    /**
     * 地址信息
     */
    private String address;

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
