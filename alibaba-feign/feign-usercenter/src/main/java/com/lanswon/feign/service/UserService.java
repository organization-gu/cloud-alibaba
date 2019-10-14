package com.lanswon.feign.service;

import com.lanswon.feign.entity.user.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@FeignClient(value = "user-center",fallbackFactory =HystrixClientFallbackFactory.class)
public interface UserService {

    @GetMapping("/users/{id}")
    User findById(@PathVariable("id") Integer id);
}
