package com.lanswon.management.aop.log.aspect;

import com.lanswon.management.aop.log.SysLog;
import com.lanswon.management.aop.log.annotation.Log;
import com.lanswon.management.aop.log.event.SysLogEvent;
import com.lanswon.management.aop.log.util.ParseSpel;
import com.lanswon.management.aop.log.util.SpringContextHolder;
import com.lanswon.management.aop.log.util.SysLogUtils;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

/**
 * @Description: 操作日志 可以使用spring event异步入库
 * @Author GU-YW
 * @Date 2019/12/4 13:31
 */
@Aspect
@Slf4j
public class SysLogAspect {

    @Around("@annotation(sysLog)")
    @SneakyThrows
    public Object around(ProceedingJoinPoint point, Log sysLog) {
        String strClassName = point.getTarget().getClass().getName();
        String strMethodName = point.getSignature().getName();
        log.debug("[类名]:{},[方法]:{}", strClassName, strMethodName);

        SysLog logVo = SysLogUtils.getSysLog();
//		String message = AnnotationResolver.newInstance().dd(point, sysLog.message());

        String message = ParseSpel.parseSpel(point);
        logVo.setLogMessage(message);
        logVo.setOperationType(sysLog.operationType());
        logVo.setLogLevel(sysLog.logLevel());
        // 发送异步日志事件
        Long startTime = System.currentTimeMillis();
        Object obj = point.proceed();
        Long endTime = System.currentTimeMillis();
        logVo.setExecutionTime(endTime - startTime);

        SpringContextHolder.publishEvent(new SysLogEvent(logVo));
        return obj;
    }
}
