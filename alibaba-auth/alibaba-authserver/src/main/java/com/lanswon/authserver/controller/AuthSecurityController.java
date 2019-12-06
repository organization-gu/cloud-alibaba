/**
 * 
 */
package com.lanswon.authserver.controller;

import com.lanswon.authcore.contants.SecurityConstants;
import com.lanswon.authcore.properties.SecurityProperties;
import com.lanswon.authcore.support.SimpleResponse;
import com.lanswon.authcore.support.SocialUserInfo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
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
 * 跳转身份认证
 * @Author GU-YW
 * @Date 2019/10/29 8:18
 */
@RestController
@Slf4j
public class AuthSecurityController {

	@Resource
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
	@RequestMapping(SecurityConstants.DEFAULT_UNAUTHENTICATION_URL)
	@ResponseStatus(code = HttpStatus.UNAUTHORIZED)
	public SimpleResponse requireAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws IOException {

		SavedRequest savedRequest = requestCache.getRequest(request, response);
//		if (savedRequest != null) {
			String targetUrl = request.getRequestURI();
			log.info("引发跳转的请求是:" + targetUrl);
//			redirectStrategy.sendRedirect(request, response, securityProperties.getBrowser().getLoginPage());
//		}

		return new SimpleResponse("访问的服务需要身份认证，请先登录");
	}


}
