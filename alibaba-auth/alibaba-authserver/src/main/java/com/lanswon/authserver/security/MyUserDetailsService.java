package com.lanswon.authserver.security;


import com.lanswon.authserver.dao.UserMapper;
import com.lanswon.authserver.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.social.security.SocialUser;
import org.springframework.social.security.SocialUserDetails;
import org.springframework.social.security.SocialUserDetailsService;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @Author 处理用户信息
 * @Date 2019/10/29 9:22
 */
@Component
@Slf4j
public class MyUserDetailsService implements UserDetailsService, SocialUserDetailsService {

	@Autowired
	PasswordEncoder passwordEncoder;

	@Resource
	UserMapper userMapper;


	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		log.info("表单登录用户名:" + username);
		return buildUser(username);
	}

	@Override
	public SocialUserDetails loadUserByUserId(String userId) throws UsernameNotFoundException {
		log.info("第三方登录用户Id:" + userId);
		return buildUser(userId);
	}

	private SocialUserDetails buildUser(String userId) {

		if(StringUtils.isBlank(userId)){
			throw new UsernameNotFoundException("用户名不能为空");
		}

		// 根据用户名查找用户信息
		String tableName = StringUtils.split(userId,"&")[0]+"_user";

		String username = StringUtils.split(userId,"&")[1];

		User user = userMapper.selectOneByName(tableName,username);

		if(user== null){
			new InternalAuthenticationServiceException(
					"用户不存在");
		}
		//根据查找到的用户信息判断用户是否被冻结

		//此密码是数据库密码，由spring来调用mach()来验证密码是否匹配
		String password = user.getPassword();
		String urls = "";
		return new SocialUser(username, password,
				user.getAccountEnable(), user.getAccountNonExpired(),
				user.getCredentialNonExpired(), user.getAccountNonLock(),
				AuthorityUtils.commaSeparatedStringToAuthorityList(urls));
	}


}
