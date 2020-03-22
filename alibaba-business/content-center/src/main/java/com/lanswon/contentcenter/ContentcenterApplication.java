package com.lanswon.contentcenter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.context.annotation.ComponentScan;

import org.springframework.transaction.annotation.EnableTransactionManagement;
import ribbonConfig.CusTomizeIruleConfig;
import tk.mybatis.spring.annotation.MapperScan;

/**
 * 内容中心
 * @Author GU-YW
 * @Date 2019/10/10 16:18
 */
@SpringBootApplication
@MapperScan("com.lanswon.contentcenter.dao")
@ComponentScan(basePackages={"com.lanswon.contentcenter","ribbonConfig", "com.lanswon.userfeign"})
@EnableFeignClients(basePackages = "com.lanswon.userfeign")
@EnableTransactionManagement
@EnableBinding(Source.class)
public class ContentcenterApplication {
    public static void main(String[] args) {
        SpringApplication.run(ContentcenterApplication.class);
    }
}
