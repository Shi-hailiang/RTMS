package com.shihailiang.response;

import com.shihailiang.enumeration.StateCode;
import lombok.Data;

/**
 * 通用返回类
 * @param <T>
 */
@Data
public class CommonResponse<T> {

    private int code;
    private String message;
    private T data;

    public CommonResponse(int code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public CommonResponse(int code, T data) {
        this(code, "", data);
    }

    public CommonResponse(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public CommonResponse(StateCode stateCode) {
        this(stateCode.getCode(), stateCode.getMessage(), null);
    }
}