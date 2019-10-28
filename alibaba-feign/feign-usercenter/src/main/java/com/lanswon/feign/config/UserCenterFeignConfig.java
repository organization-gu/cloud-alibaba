package com.lanswon.feign.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * feign 配置（日志级别可以配置的方式也可以完成）
 * @Author GU-YW
 * @Date 2019/10/14 14:49
 */
@Configuration
public class UserCenterFeignConfig {

    //@Bean
//    public Logger.Level level(){
//        return Logger.Level.FULL;
//    }

    @Bean
    public TokenRelayRequestIntecepor tokenRelayRequestIntecepor(){
        return new TokenRelayRequestIntecepor();
    }
}
