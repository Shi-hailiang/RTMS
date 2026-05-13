package com.shihailiang.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

public class Code {
    /**
     * 生成订单号码
     * 格式：2023-1213-1219-21XX-XXXX-XX
     */
    public static String generateCode() {
        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat ("yyyy-MMdd-HHmm-ss");
        Random random = new Random();
        StringBuilder stringBuilder = new StringBuilder();
        // 生成第一部分，两个数字
        for (int i = 0; i < 2; i++) {
            stringBuilder.append(random.nextInt(10)); // 生成0到9之间的随机数
        }
        stringBuilder.append("-");
        // 生成第二部分，四个数字
        for (int i = 0; i < 4; i++) {
            stringBuilder.append(random.nextInt(10)); // 生成0到9之间的随机数
        }
        stringBuilder.append("-");
        // 生成第三部分，两个数字
        for (int i = 0; i < 2; i++) {
            stringBuilder.append(random.nextInt(10)); // 生成0到9之间的随机数
        }
        return simpleDateFormat.format(date) + stringBuilder;
    }
}

