package com.lanswon.authserver.security;

import com.lanswon.uumfeign.domain.dto.DataRtnDTO;
import com.lanswon.uumfeign.domain.entity.AuthUrl;
import com.lanswon.uumfeign.domain.entity.AuthUser;
import com.lanswon.uumfeign.service.UumService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
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
import java.util.Objects;
import java.util.Set;

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
	UumService uumService;

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
		// 根据用户名查找用户信息
		if(StringUtils.isBlank(userId)){
			throw new UsernameNotFoundException("用户不能为空");
		}
		DataRtnDTO<AuthUser> user = uumService.getUserInfoByUsername(userId);
		//根据查找到的用户信息判断用户是否被冻结
		if(Objects.isNull(user.getData())){
			throw new UsernameNotFoundException("用户不存在");
		}
		//此密码是数据库密码，由spring来调用mach()来验证密码是否匹配
		AuthUser authUser = user.getData();
		String password = authUser.getPassword();
		String urls = getAuthorityString(authUser.getUrls());
		log.info("权限信息[{}]",urls);
		return new SocialUser(userId, password,
				authUser.isAccountEnable(), authUser.isAccountExpired(),
				authUser.isAccountExpired(), authUser.isAccountLock(),
				AuthorityUtils.commaSeparatedStringToAuthorityList(urls));
	}

	private String getAuthorityString(Set<AuthUrl> urls){
		StringBuffer stringBuffer = new StringBuffer();
		for(AuthUrl authUrl :urls){
			stringBuffer.append(authUrl.getUrl()).append("|").append(authUrl.getMethod()).append(",");
		}

		return  StringUtils.substring(stringBuffer.toString(),0,stringBuffer.length()-1);
	}

}
