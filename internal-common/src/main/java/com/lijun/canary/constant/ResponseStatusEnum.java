package com.lijun.canary.constant;

import lombok.Getter;

/**
 * 返回状态枚举
 * @author : LiJun
 * @since : 2023-12-19 11:47
 **/
public enum ResponseStatusEnum {

    /**
     * 	服务异常
     */
    INTERNAL_SERVER_ERROR(500, "server error"),

    /**
     * 参数异常
     */
    INVALID_PARAMETER(400, "invalid parameter"),


    /**
     * 	操作成功
     */
    SUCCESS(0, "success"),


    /**
     * 	限流
     */
    LIMIT(2, "你被限流了")

    ;


    @Getter
    private final int code;

    @Getter
    private final String message;

    ResponseStatusEnum(int code, String message) {
        this.code = code;
        this.message = message;
    }

}
