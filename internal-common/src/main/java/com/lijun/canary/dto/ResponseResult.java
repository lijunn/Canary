package com.lijun.canary.dto;

import com.lijun.canary.constant.ResponseStatusEnum;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 通用返回值处理类
 *
 * Accessors(chain = true)
 * chain的中文含义是链式的，设置为true，则setter方法返回当前对象
 *
 * @author : LiJun
 * @since : 2023-12-19 11:47
 */
@Data
@Accessors(chain = true)
public class ResponseResult<T> implements Serializable {

	private static final long serialVersionUID = -2073390651767969040L;

	private int code;
    private String message;
    private T data;

	/**
     * 返回成功数据（status：1）
     *
     * @param data 数据内容
     * @param <T>  数据类型
     * @return ResponseResult
     */
    public static <T> ResponseResult<T> success(T data) {
        return new ResponseResult<T>().setCode(ResponseStatusEnum.SUCCESS.getCode()).setMessage(ResponseStatusEnum.SUCCESS.getMessage()).setData(data);
    }

    /**
     * 自定义返回错误数据
     *
     * @param statusEnum 状态枚举
     * @return ResponseResult
     */
    public static ResponseResult<Object> fail(ResponseStatusEnum statusEnum) {
        return new ResponseResult<Object>().setCode(statusEnum.getCode()).setMessage(statusEnum.getMessage());
    }

    /**
     * 自定义返回错误数据
     *
     * @param statusEnum 状态枚举
     * @param <T>  数据类型
     * @return ResponseResult
     */
    public static <T> ResponseResult<T> fail(ResponseStatusEnum statusEnum,T data) {
        return new ResponseResult<T>().setCode(statusEnum.getCode()).setMessage(statusEnum.getMessage()).setData(data);
    }

    /**
     * 限流
     * @param message 错误消息
     * @return ResponseResult
     */
    public static ResponseResult<Object> limit(String message) {
        return new ResponseResult<Object>().setCode(ResponseStatusEnum.LIMIT.getCode()).setMessage(message);
    }

}
