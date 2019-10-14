package com.lanswon.feign.service;

import com.lanswon.feign.entity.user.User;
import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;

//必须要此注解，否则无法使用熔断功能
@Component
public class HystrixClientFallbackFactory implements FallbackFactory<UserService> {


	@Override
	public UserService create(Throwable throwable) {
		return new UserService() {
			@Override
			public User findById(Integer id) {
				return User.builder().wxNickname("此服务出现异常").build();
			}
		};
	}
}
