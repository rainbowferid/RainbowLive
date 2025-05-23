package org.web.controller;

import jakarta.validation.ConstraintViolationException;
import org.common.enums.ResponseCodeEnum;
import org.common.exception.BaseException;
import org.common.result.Result;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.net.BindException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public Result handleException(Exception e) {

        Integer code = 500;

        if(e instanceof NoHandlerFoundException){
            //404
            code = ResponseCodeEnum.CODE_404.getCode();
        }
        else if(e instanceof BaseException){
            //业务错误
            code = ResponseCodeEnum.CODE_600.getCode();
        }
        else if (e instanceof BindException|| e instanceof MethodArgumentTypeMismatchException) {
            //主键冲突
            code = ResponseCodeEnum.CODE_600.getCode();

        }
        else if (e instanceof DuplicateKeyException) {
            //参数类型错误
            code = ResponseCodeEnum.CODE_601.getCode();
        }
        else if (e instanceof ConstraintViolationException) {
            //参数异常
            code = ResponseCodeEnum.CODE_600.getCode();
        }
        else{
            //其他异常
            code = ResponseCodeEnum.CODE_500.getCode();
        }

        return Result.error(code,e.getMessage());
    }

}
