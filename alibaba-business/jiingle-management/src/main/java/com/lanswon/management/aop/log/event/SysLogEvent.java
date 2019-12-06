package com.lanswon.management.aop.log.event;

import com.lanswon.management.aop.log.SysLog;
import org.springframework.context.ApplicationEvent;

/**
 * @Description: 系统日志事件
 * @Author GU-YW
 * @Date 2019/12/4 13:22
 */
public class SysLogEvent extends ApplicationEvent {
    /**
     * Create a new ApplicationEvent.
     *
     * @param sysLog the object on which the event initially occurred (never {@code null})
     */
    public SysLogEvent(SysLog sysLog) {
        super(sysLog);
    }
}
