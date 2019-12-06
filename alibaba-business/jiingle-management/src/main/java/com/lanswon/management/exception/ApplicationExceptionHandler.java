package com.lanswon.management.exception;

import com.lanswon.management.domain.vo.SimpleResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @Description:
 * @Author GU-YW
 * @Date 2019/12/3 16:54
 */
@ControllerAdvice
@Slf4j
public class ApplicationExceptionHandler {

    @ExceptionHandler
    @ResponseBody
    public SimpleResponse handleException(Exception ex){
        log.error("===系统出现异常[{}]===",ex.getMessage());
        ex.printStackTrace();
        SimpleResponse simpleResponse = SimpleResponse
                .builder()
                .status(HttpStatus.MULTIPLE_CHOICES.value())
                .build();
        if(ex instanceof ApplicationException){
            simpleResponse.setStatus(((ApplicationException) ex).getErrorCode());
            simpleResponse.setDescription(ex.getMessage());
        }else if(ex instanceof MethodArgumentNotValidException){
            StringBuilder sb=new StringBuilder();
            for (ObjectError error : ((MethodArgumentNotValidException) ex).getBindingResult().getAllErrors()) {
                sb.append("[").append(error.getDefaultMessage()).append("]");
            }
            simpleResponse.setDescription(sb.toString());
        }else if(ex instanceof Exception){
            simpleResponse.setDescription("系统出现异常，请查看详细信息");
        }
        return simpleResponse;
    }
}
