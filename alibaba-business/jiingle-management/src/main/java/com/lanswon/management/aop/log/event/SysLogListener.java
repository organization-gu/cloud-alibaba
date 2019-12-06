package com.lanswon.management.aop.log.event;

import com.lanswon.management.aop.log.SysLog;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.core.annotation.Order;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

/**
 * @Description:
 * @Author GU-YW
 * @Date 2019/12/4 13:23
 */
@Slf4j
@AllArgsConstructor
public class SysLogListener {

    @Async("taskExecutor")
    @Order
    @EventListener(SysLogEvent.class)
    public void saveSysLog(SysLogEvent event) {
        SysLog sysLog = (SysLog) event.getSource();
        //持久化消息
    }
}
