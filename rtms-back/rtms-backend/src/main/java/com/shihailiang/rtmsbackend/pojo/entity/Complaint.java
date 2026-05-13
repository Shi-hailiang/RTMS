package com.shihailiang.rtmsbackend.pojo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
@TableName("tb_complaint")
public class Complaint implements Serializable {
    private static final long serialVersionUID = 1L;

    @TableId(type = IdType.AUTO)
    private Long id;
    private Long orderId;
    private Long accountId;
    private Long storeId;
    private String type;
    private String content;
    private String images;
    private String result;
    private String reply;
    private Date createTime;
    private Date handleTime;
    private String status;
}
