package com.shihailiang.rtmsbackend.pojo.vo;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * (StoreHomeVO)实体类
 *
 * @author 石海良
 * @since 2024-02-24 00:08:12
 */

@Data
@TableName(value = "v_store_home")
public class StoreHomeVO implements Serializable {
    private static final long serialVersionUID = 5367084891196321682L;
    /**
     * 今日订单总数
     */
    private Long dayOrders;

    /**
     * 今日订单总数较昨日
     */
    private Long lastDayOrdersRate;

    /**
     * 今日销售总额
     */
    private Long daySales;

    /**
     * 今日销售总额较昨日
     */
    private Long lastDaySalesRate;

    /**
     * 今月销售总额
     */
    private Long monthSales;

    /**
     * 今月销售总额较上月
     */
    private Long lastMonthSalesRate;

    /**
     * 分类总数
     */
    private Long categoryNumber;

    /**
     * 产品总数
     */
    private Long productNumber;

    /**
     * 订单总数
     */
    private Long orderNumber;
}
