package com.shihailiang.rtmsbackend.pojo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
@TableName("tb_review")
public class Review implements Serializable {
    private static final long serialVersionUID = 1L;

    @TableId(type = IdType.AUTO)
    private Long id;
    private Long orderId;
    private Long accountId;
    private Long storeId;
    private Integer rating;
    private String content;
    private String images;
    private Date createTime;
    private Date updateTime;
    private String status;
}
