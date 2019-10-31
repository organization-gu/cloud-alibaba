/**
 * 
 */
package com.lanswon.authbrowser.controller;

import java.io.IOException;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.lanswon.authbrowser.support.SimpleResponse;
import com.lanswon.authcore.properties.SecurityProperties;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 * 浏览器身份认证
 * @Author GU-YW
 * @Date 2019/10/29 8:18
 */
@RestController
@Slf4j
public class BrowserSecurityController {

	@Autowired(required = false)
	private SecurityProperties securityProperties;

	private RequestCache requestCache = new HttpSessionRequestCache();

	private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

	/**
	 * 当需要身份认证时，跳转到这里
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
//	@RequestMapping(SecurityConstants.DEFAULT_UNAUTHENTICATION_URL)

	@RequestMapping("/authentication/require")
	@ResponseStatus(code = HttpStatus.UNAUTHORIZED)
	public SimpleResponse requireAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws IOException {

		SavedRequest savedRequest = requestCache.getRequest(request, response);

		if (savedRequest != null) {
			String targetUrl = savedRequest.getRedirectUrl();
			log.info("引发跳转的请求是:" + targetUrl);
			if (StringUtils.endsWithIgnoreCase(targetUrl, ".html")) {
				redirectStrategy.sendRedirect(request, response, securityProperties.getBrowser().getLoginPage());
			}
		}

		return new SimpleResponse("访问的服务需要身份认证，请引导用户到登录页");
	}

//	@GetMapping("/social/user")
//	public SocialUserInfo getSocialUserInfo(HttpServletRequest request) {
//		SocialUserInfo userInfo = new SocialUserInfo();
//		Connection<?> connection = providerSignInUtils.getConnectionFromSession(new ServletWebRequest(request));
//		userInfo.setProviderId(connection.getKey().getProviderId());
//		userInfo.setProviderUserId(connection.getKey().getProviderUserId());
//		userInfo.setNickname(connection.getDisplayName());
//		userInfo.setHeadimg(connection.getImageUrl());
//		return userInfo;
//	}

}
