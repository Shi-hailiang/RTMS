package com.shihailiang.rtmsbackend.pojo.vo;

import lombok.Data;
import java.util.Date;

@Data
public class ComplaintVO {
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
    // 用户信息
    private String nickname;
    private String avatar;
    // 商家信息
    private String storeName;
    private String storeLogo;
}
