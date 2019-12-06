package com.lanswon.management;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import springfox.documentation.swagger2.annotations.EnableSwagger2;
import tk.mybatis.spring.annotation.MapperScan;

/**
 * 系统启动类
 * @author GU-YW
 */

@SpringBootApplication
@MapperScan("com.lanswon.management.dao")
@ComponentScan("com.lanswon")
@EnableSwagger2
@EnableTransactionManagement
public class CloudJinLeApplication {

    public static void main(String[] args) {
        SpringApplication.run(CloudJinLeApplication.class, args);
    }


}
