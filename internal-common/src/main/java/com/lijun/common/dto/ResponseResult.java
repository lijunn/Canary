package com.lijun.common.dto;

import com.lijun.common.constant.CommonStatusEnum;
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
 * @date : 2023-12-19 11:47
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
     * @return ResponseResult实例
     */
    public static <T> ResponseResult<T> success(T data) {
        return new ResponseResult<T>().setCode(CommonStatusEnum.SUCCESS.getCode()).setMessage(CommonStatusEnum.SUCCESS.getValue()).setData(data);
    }

    /**
     * 返回错误数据（status：500）
     *
     * @param data 错误内容
     * @param <T>  数据类型
     * @return ResponseResult实例
     */
    public static <T> ResponseResult<T> fail(T data) {
        return new ResponseResult<T>().setCode(CommonStatusEnum.INTERNAL_SERVER_EXCEPTION.getCode()).setMessage(CommonStatusEnum.INTERNAL_SERVER_EXCEPTION.getValue()).setData(data);
    }

    /**
     * 自定义返回错误数据
     *
     * @param code    错误代码
     * @param message 错误消息
     * @return ResponseResult实例
     */
    public static ResponseResult<Object> fail(CommonStatusEnum statusEnum) {
        return new ResponseResult<Object>().setCode(statusEnum.getCode()).setMessage(statusEnum.getValue());
    }

    /**
     * 自定义返回错误数据
     *
     * @param code    错误代码
     * @param message 错误消息
     * @return ResponseResult实例
     */
    public static ResponseResult<Object> limit(String message) {
        return new ResponseResult<Object>().setCode(CommonStatusEnum.LIMIT.getCode()).setMessage(message);
    }

    /**
     * @param code    错误代码
     * @param message 错误消息
     * @param data    错误内容
     * @return ResponseResult实例
     */
    public static ResponseResult<String> fail(int code, String message, String data) {
        return new ResponseResult<String>().setCode(code).setMessage(message).setData(data);
    }
}
