package com.shihailiang.rtmsbackend.pojo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * (Rider)实体类
 *
 * @author 石海良
 * @since 2023-12-20 09:51:27
 */
@Data
@TableName("tb_rider")
public class Rider implements Serializable {
    private static final long serialVersionUID = 6357014215854597671L;
    /**
     * 骑手id
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 账号id
     */
    private Long accountId;

    /**
     * 骑手姓名
     */
    private String name;

    /**
     * 身份证号
     */
    private String idNumber;

    /**
     * 骑手性别
     */
    private String gender;

    /**
     * 电话号码
     */
    private String phone;

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