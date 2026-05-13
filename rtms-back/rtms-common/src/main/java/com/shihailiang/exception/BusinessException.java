package com.shihailiang.exception;

import com.shihailiang.enumeration.StateCode;

/**
 * 自定义异常类
 *
 * @author 石海良
 */
public class BusinessException extends RuntimeException {

    private final int code;

    private final String description;

    public int getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }

    public BusinessException(String message, int code, String description) {
        super(message);
        this.code = code;
        this.description = description;
    }

    public BusinessException(StateCode stateCode ) {
        super(stateCode.getMessage());
        this.code = stateCode.getCode();
        this.description = stateCode.getDescription();
    }

    public BusinessException(StateCode stateCode, String description ) {
        super(stateCode.getMessage());
        this.code = stateCode.getCode();
        this.description = description;
    }

}
