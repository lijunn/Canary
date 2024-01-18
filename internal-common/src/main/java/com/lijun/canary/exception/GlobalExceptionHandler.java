package com.lijun.canary.exception;

import cn.hutool.json.JSONUtil;
import com.lijun.canary.constant.ResponseStatusEnum;
import com.lijun.canary.dto.ResponseResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.lang.NonNull;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Controller 全局异常处理
 * @author : LiJun
 * @date : 2024-01-18 14:41
 **/
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 参数异常
     */
    @ResponseBody
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = { BindException.class, HttpMessageNotReadableException.class })
    public ResponseResult<Object> handleParameterException(@NonNull Exception e) {
        log.warn("handleParameterException: {}", e.getMessage());
        return ResponseResult.fail(ResponseStatusEnum.INVALID_PARAMETER);
    }

    /**
     * 参数校验异常
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(value = HttpStatus.OK)
    public ResponseResult<Object> handleParameterVerificationException(MethodArgumentNotValidException ex) {
        BindingResult bindingResult = ex.getBindingResult();
        List<String> messages = bindingResult.getFieldErrors().stream().map(FieldError::getDefaultMessage).collect(Collectors.toList());
        log.warn("handleParameterVerificationException: {}", JSONUtil.toJsonStr(messages));
        return ResponseResult.fail(ResponseStatusEnum.INVALID_PARAMETER, messages);
    }

    /**
     * 自定义业务异常：
     * 返回指定的业务异常code和message
     *
     */
    @ResponseBody
    @ExceptionHandler(BusinessException.class)
    public ResponseResult<Object> processBusinessException(BusinessException businessException) {
        log.error(businessException.getLocalizedMessage(), businessException);
        return ResponseResult.fail(businessException.getExceptionEnum());
    }

    /**
     * 其他异常：
     * 直接返回"server error"
     */
    @ResponseBody
    @ExceptionHandler(Exception.class)
    public ResponseResult<Object> processException(Exception exception) {
        log.error(exception.getLocalizedMessage(), exception);
        return ResponseResult.fail(ResponseStatusEnum.INTERNAL_SERVER_ERROR);
    }

}
