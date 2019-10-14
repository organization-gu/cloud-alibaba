package com.lanswon.usercenter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import tk.mybatis.spring.annotation.MapperScan;

/**
 * @Author GU-YW
 * @Date 2019/10/10 16:18
 */
@SpringBootApplication
@MapperScan("com.lanswon")
public class UsercenterApplication {
    public static void main(String[] args) {
        SpringApplication.run(UsercenterApplication.class);
    }
}
