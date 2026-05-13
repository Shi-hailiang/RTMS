package com.shihailiang.rtmsbackend.pojo.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * (SystemVO)实体类
 *
 * @author 石海良
 * @since 2023-12-14 22:20:27
 */
@Data
public class SystemVO implements Serializable {
    private static final long serialVersionUID = 8561873686601843537L;
    /**
     * 平台名称
     */
    private String name;

    /**
     * 平台公告
     */
    private String announcement;

    /**
     * 配送费用
     */
    private Long deliveryPrice;

    /**
     * 轮播图1
     */
    private String banner1;

    /**
     * 轮播图2
     */
    private String banner2;

    /**
     * 轮播图3
     */
    private String banner3;
}
