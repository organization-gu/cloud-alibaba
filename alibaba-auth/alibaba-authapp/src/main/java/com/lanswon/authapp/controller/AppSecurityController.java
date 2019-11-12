/**
 * 
 */
package com.lanswon.authapp.controller;

import com.lanswon.authapp.social.utils.AppSignUpHandle;
import com.lanswon.authcore.contants.SecurityConstants;
import com.lanswon.authcore.properties.SecurityProperties;
import com.lanswon.authcore.support.SimpleResponse;
import com.lanswon.authcore.support.SocialUserInfo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.web.ProviderSignInUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.ServletWebRequest;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Author GU-YW
 * @Date 2019/10/29 8:18
 * Description：将社交账户从session转存到redis，并返回给APP一个
 * 社交登陆失败的原因+社交账户信息（便于APP前端显示当前社交账户的用户名、头像等信息）
 */
@RestController
@Slf4j
public class AppSecurityController {

	@Autowired
	private ProviderSignInUtils providerSignInUtils;

	/**
	 * 将社交账户信息转存到redis和从redis取出社交账户信息的工具类
	 */
	@Autowired
	private AppSignUpHandle appSignUpHandle;

	@GetMapping("/social/signUp")
	@ResponseStatus(HttpStatus.UNAUTHORIZED)
	public SocialUserInfo getSocialUserInfo(HttpServletRequest request) {
		SocialUserInfo userInfo = new SocialUserInfo();
		Connection<?> connection = providerSignInUtils.getConnectionFromSession(new ServletWebRequest(request));
		userInfo.setProviderId(connection.getKey().getProviderId());
		userInfo.setProviderUserId(connection.getKey().getProviderUserId());
		userInfo.setNickname(connection.getDisplayName());
		userInfo.setHeadimg(connection.getImageUrl());

		//从session里将社交账户信息取出并转存到redis
		appSignUpHandle.saveConnectionData(new ServletWebRequest(request), connection.createData());
		return userInfo;
	}

}
