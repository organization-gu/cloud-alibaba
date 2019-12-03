package com.lanswon.management;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * 系统启动类
 * @author GU-YW
 */

@SpringBootApplication
@ComponentScan("com.lanswon")
public class CloudJinLeApplication {

    public static void main(String[] args) {
        SpringApplication.run(CloudJinLeApplication.class, args);
    }


}
