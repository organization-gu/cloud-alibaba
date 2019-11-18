package com.lanswon.userfeign.service;

import com.lanswon.userfeign.entity.user.User;
import feign.hystrix.FallbackFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @Author GU-YW
 * @Date 2019/10/15 15:47
 */
@Component
@Slf4j
public class UserServiceFallback implements FallbackFactory<UserService> {

    @Override
    public UserService create(Throwable throwable) {
        return new UserService() {
            @Override
            public User findById(Integer id) {
                log.warn("服务被降级了",throwable.getStackTrace());
                return User.builder().wxNickname("服务降级了").build();
            }
        };
    }
}
