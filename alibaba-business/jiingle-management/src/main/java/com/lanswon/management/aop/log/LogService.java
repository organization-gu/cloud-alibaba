package com.lanswon.management.aop.log;

import com.lanswon.management.aop.log.constant.LogConstant;
import com.lanswon.management.aop.log.event.SysLogEvent;
import com.lanswon.management.aop.log.util.SysLogUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

/**
 * @Description:
 * @Author GU-YW
 * @Date 2019/12/5 9:14
 */
@Service
public class LogService {

    @Autowired
    ApplicationContext applicationContext;


    public void saveAuthLoginLog(String msg, int logLevel ) {

        SysLog sysLog = SysLogUtils.getSysLog();
        sysLog.setLogMessage(msg);
        sysLog.setOperationType(LogConstant.LOG_OPERATION_TYPE_LOGIN);
        sysLog.setLogLevel(logLevel);
        sysLog.setServiceId("auth");
        applicationContext.publishEvent(new SysLogEvent(sysLog));

    }

    public void saveGateWayLog(String msg, int logLevel) {

        SysLog sysLog = SysLogUtils.getSysLog();
        sysLog.setLogMessage(msg);
        sysLog.setOperationType(LogConstant.LOG_OPERATION_TYPE_LOGIN);
        sysLog.setLogLevel(logLevel);
        sysLog.setServiceId("gateway");
        applicationContext.publishEvent(new SysLogEvent(sysLog));

    }

    public void saveGateWayErrorLog(String msg) {

        SysLog sysLog = SysLogUtils.getSysLog();
        sysLog.setLogMessage(msg);
        sysLog.setOperationType(LogConstant.LOG_OPERATION_TYPE_LOGIN);
        sysLog.setLogLevel(LogConstant.LOG_LEVEL_ERROR);
        sysLog.setServiceId("gateway");
        applicationContext.publishEvent(new SysLogEvent(sysLog));

    }
}
