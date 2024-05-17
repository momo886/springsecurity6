package com.xiaoxi.except;

import lombok.Data;

/**
 * @Author： momo
 *
 */
@Data
public class BusinessException extends RuntimeException {

    private Integer code=101;

    public BusinessException(Integer code, String message) {
        super(message);
        this.code = code;
    }

    public BusinessException(String message) {
        super(message);
    }
}
