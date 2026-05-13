package com.shihailiang.util;

import java.math.BigDecimal;
import java.util.Map;

/**
 * 地址坐标映射工具
 * 将系统内已知的文本地址映射为 GCJ-02 坐标（广州市天河区范围）
 */
public class CoordinateUtil {

    private static final double DEFAULT_LAT = 23.129;
    private static final double DEFAULT_LNG = 113.322;

    private static final Map<String, double[]> COORDS = Map.ofEntries(
        // 取餐/配送地址
        Map.entry("周大福金融大厦取餐点", new double[]{23.119, 113.325}),
        Map.entry("远洋大厦取餐点", new double[]{23.126, 113.317}),
        Map.entry("天环广场取餐点", new double[]{23.130, 113.323}),
        Map.entry("正佳广场取餐点", new double[]{23.132, 113.322}),
        Map.entry("万菱汇取餐点", new double[]{23.134, 113.326}),
        // 商家地址
        Map.entry("正佳广场", new double[]{23.132, 113.322}),
        Map.entry("正佳广场店", new double[]{23.132, 113.322}),
        Map.entry("天环广场", new double[]{23.130, 113.323}),
        Map.entry("天环广场店", new double[]{23.130, 113.323}),
        Map.entry("万菱汇店", new double[]{23.134, 113.326}),
        Map.entry("万菱汇", new double[]{23.134, 113.326})
    );

    public static BigDecimal getLatitude(String address) {
        double[] c = COORDS.get(address);
        return BigDecimal.valueOf(c != null ? c[0] : DEFAULT_LAT);
    }

    public static BigDecimal getLongitude(String address) {
        double[] c = COORDS.get(address);
        return BigDecimal.valueOf(c != null ? c[1] : DEFAULT_LNG);
    }
}
