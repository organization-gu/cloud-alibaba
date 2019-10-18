package com.lanswon.usercenter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.messaging.Sink;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import tk.mybatis.spring.annotation.MapperScan;

/**
 * @Author GU-YW
 * @Date 2019/10/10 16:18
 */
@SpringBootApplication
@MapperScan("com.lanswon")
@EnableTransactionManagement
@EnableBinding(Sink.class)
public class UserCenterApplication {
    public static void main(String[] args) {
        SpringApplication.run(UserCenterApplication.class);
    }
}