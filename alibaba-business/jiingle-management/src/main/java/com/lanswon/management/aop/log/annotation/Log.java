package com.lanswon.management.aop.log.annotation;



import com.lanswon.management.aop.log.constant.LogConstant;

import java.lang.annotation.*;

/**
 * @Description: 日志操作注解
 * @Author GU-YW
 * @Date 2019/12/4 13:06
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Log {

    /**
     * 描述
     *
     * @return {String}
     */
    String message() default "";

    /**
     * 操作类型
     * @return
     */
    int operationType() default LogConstant.LOG_OPERATION_TYPE_INSERT;

    /**
     * 日志级别
     * @return
     */
    int logLevel() default LogConstant.LOG_LEVEL_INFO;

}
