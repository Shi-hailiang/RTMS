package com.shihailiang.rtmsbackend.pojo.vo;

import lombok.Data;
import java.util.Date;

@Data
public class ReviewVO {
    private Long id;
    private Long orderId;
    private Long accountId;
    private Long storeId;
    private Integer rating;
    private String content;
    private String images;
    private Date createTime;
    private String status;
    // 用户信息
    private String nickname;
    private String avatar;
}
