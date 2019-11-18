package com.lanswon.userfeign.service;

import com.lanswon.userfeign.config.UserCenterFeignConfig;
import com.lanswon.userfeign.entity.user.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@FeignClient(value = "user-center",configuration = UserCenterFeignConfig.class,fallbackFactory = UserServiceFallback.class)
public interface UserService {

    @GetMapping("/users/{id}")
    User findById(@PathVariable("id") Integer id);
}
