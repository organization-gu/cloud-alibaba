package com.lanswon.userfeign.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;


@FeignClient(value = "user",url = "http://www.baidu.com")
public interface UserServiceTest {

    @GetMapping()
   public  String getBaidu();

}
