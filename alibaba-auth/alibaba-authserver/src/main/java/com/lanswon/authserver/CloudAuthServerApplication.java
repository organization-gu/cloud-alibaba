package com.lanswon.authserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.client.RestTemplate;
import springfox.documentation.swagger2.annotations.EnableSwagger2;
import tk.mybatis.spring.annotation.MapperScan;

/**
 * 权限服务器
 * @author GU-YW
 */

@SpringBootApplication
@ComponentScan("com.lanswon")
@EnableWebSecurity
@MapperScan("com.lanswon.authserver.dao")
@EnableSwagger2
@EnableTransactionManagement
public class CloudAuthServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(CloudAuthServerApplication.class, args);
    }

    @Bean
    public  RestTemplate restTemplate(){
        return new RestTemplate();
    }


}
