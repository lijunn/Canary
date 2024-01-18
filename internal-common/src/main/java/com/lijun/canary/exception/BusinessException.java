package com.lijun.canary.exception;

import com.lijun.canary.constant.ResponseStatusEnum;

/**
 * @author : LiJun
 * @date : 2024-01-18 15:30
 **/
public class BusinessException extends RuntimeException{

    private final ResponseStatusEnum exceptionEnum;

    public BusinessException(ResponseStatusEnum exceptionEnum) {
        super(exceptionEnum.getMessage());
        this.exceptionEnum = exceptionEnum;
    }

    public ResponseStatusEnum getExceptionEnum() {
        return exceptionEnum;
    }
}
