package com.lanswon.authserver.controller;

import com.lanswon.authcore.support.SimpleResponse;
import com.lanswon.authserver.entity.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @Description: 简单统一异常处理
 * @Author GU-YW
 * @Date 2019/12/16 9:22
 */
@ControllerAdvice
@Slf4j
public class ExceptionController {

    @ExceptionHandler
    @ResponseBody
    public Result handleException(Exception ex){
        log.error("===系统出现异常[{}]===",ex.getMessage());
        ex.printStackTrace();
        Result simpleResponse = Result
                .builder()
                .status(HttpStatus.MULTIPLE_CHOICES.value())
                .build();
        simpleResponse.setSimpleResponse(new SimpleResponse(ex.getMessage()));
        return simpleResponse;
    }

}
