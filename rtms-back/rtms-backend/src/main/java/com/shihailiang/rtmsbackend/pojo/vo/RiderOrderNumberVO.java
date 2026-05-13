package com.shihailiang.rtmsbackend.pojo.vo;

import lombok.Data;

@Data
public class RiderOrderNumberVO {
    private static final long serialVersionUID = 8541179553605848537L;
    int dayNumber;
    int monthNumber;
    long dayIncome;
    long monthIncome;

    public RiderOrderNumberVO(int dayNumber, int monthNumber, long dayIncome, long monthIncome) {
        this.dayNumber = dayNumber;
        this.monthNumber = monthNumber;
        this.dayIncome = dayIncome;
        this.monthIncome = monthIncome;
    }
}
