package com.lanswon.authdemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * demo
 * @author GU-YW
 */

@RestController
@SpringBootApplication
@ComponentScan("com.lanswon")
@EnableWebSecurity
@EnableRedisHttpSession
public class CloudAuthDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(CloudAuthDemoApplication.class, args);
    }

    @GetMapping("/hello")
    public String hello() {
        return "hello spring security";
    }

}
