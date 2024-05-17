package com.xiaoxi.except;

import com.xiaoxi.common.Result;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;


/**
 * @Author： momo
 *
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BusinessException.class)
    public Result except(BusinessException ex) {
        return Result.fail(ex.getCode(), ex.getMessage());
    }

//    @ExceptionHandler(AuthenticationException.class)
//    @ResponseStatus(HttpStatus.FORBIDDEN)
//    public Result authenticationExceptionHandling(AuthenticationException ex) {
//        System.out.println("authenticationExceptionHandling = " + ex);
//        return Result.fail(HttpStatus.UNAUTHORIZED.value(), "认证失败");
//    }

}
