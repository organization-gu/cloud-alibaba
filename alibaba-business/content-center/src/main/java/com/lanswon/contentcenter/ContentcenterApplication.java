package com.lanswon.contentcenter;

import com.lanswon.contentcenter.config.irule.NacosClusterRule;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

import ribbonConfig.CusTomizeIruleConfig;
import tk.mybatis.spring.annotation.MapperScan;

/**
 * 内容中心
 * @Author GU-YW
 * @Date 2019/10/10 16:18
 */
@SpringBootApplication
@MapperScan("com.lanswon")
@RibbonClient(name = "user-center",configuration = CusTomizeIruleConfig.class)
@ComponentScan(basePackages={"com.lanswon.contentcenter","ribbonConfig","com.lanswon.feign"})
@EnableFeignClients(basePackages = "com.lanswon.feign")
public class ContentcenterApplication {
    public static void main(String[] args) {
        SpringApplication.run(ContentcenterApplication.class);
    }
}
