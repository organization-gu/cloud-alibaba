package com.lanswon.mp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 *
 * @Author GU-YW
 * @Date 2019/10/10 16:18
 */
@SpringBootApplication
@ComponentScan({"com.lanswon"})
public class WxMpApplication {
    public static void main(String[] args) {
        SpringApplication.run(WxMpApplication.class);
    }
}
