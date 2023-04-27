package com.yinsel.usercenter.exception;

import com.yinsel.usercenter.common.BaseResponse;
import com.yinsel.usercenter.common.ErrorCode;
import com.yinsel.usercenter.common.ResultUtlis;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 全局异常处理器
 * @author yinsel
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    @ExceptionHandler(BusinessException.class)
    public BaseResponse businessExceptionHandler(BusinessException e) {
        log.error("businessException" + e.getMessage(),e);
        return ResultUtlis.error(e.getCode(),e.getMessage(),e.getDescription());
    }

    @ExceptionHandler(RuntimeException.class)
    public BaseResponse runtimeExceptionHandler(BusinessException e) {
        log.error("runtimeException",e);
        return ResultUtlis.error(ErrorCode.SYSTEM_ERROR,e.getMessage(),"");
    }
}
