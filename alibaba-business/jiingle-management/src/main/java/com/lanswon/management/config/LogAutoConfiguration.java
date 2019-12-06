package com.lanswon.management.config;

import com.lanswon.management.aop.log.aspect.SysLogAspect;
import com.lanswon.management.aop.log.event.SysLogListener;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * @Description:
 * @Author GU-YW
 * @Date 2019/12/5 9:09
 */
@EnableAsync
@Configuration
@ConditionalOnWebApplication
public class LogAutoConfiguration  {


    @Bean
    public SysLogListener sysLogListener() {
        return new SysLogListener();
    }

    @Bean
    public SysLogAspect sysLogAspect() {
        return new SysLogAspect();
    }
}
