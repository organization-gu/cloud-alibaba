/**
 * 
 */
package com.lanswon.authcore.filter;

import java.io.IOException;
import java.util.*;


import com.lanswon.authcore.controller.ValidateCodeController;
import com.lanswon.authcore.properties.SecurityProperties;
import com.lanswon.authcore.validatecode.ValidateCodeException;
import com.lanswon.authcore.validatecode.image.ImageCode;
import lombok.Data;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.social.connect.web.HttpSessionSessionStrategy;
import org.springframework.social.connect.web.SessionStrategy;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/** 处理验证码验证过滤器 OncePerRequestFilter  spring提供的工具类，保证自定义的过滤器被执行一次
 * @author GU-YW
 *
 */
@Data
public class ValidateCodeFilter extends OncePerRequestFilter implements InitializingBean {

	/**
	 * 操作session的工具类
	 */
	private SessionStrategy sessionStrategy = new HttpSessionSessionStrategy();

	/**
	 * 验证码校验失败处理器
	 */
	private AuthenticationFailureHandler authenticationFailureHandler;

	private Set<String> urls = new HashSet<>();

	private SecurityProperties securityProperties;

	/**
	 * 路径匹配的工具类
	 */
	private AntPathMatcher pathMatcher = new AntPathMatcher();

	@Override
	public void afterPropertiesSet() throws ServletException {
		super.afterPropertiesSet();
		String [] confgUrls = StringUtils.splitByWholeSeparatorPreserveAllTokens(securityProperties.getCode().getImage().getUrl(),
				",");
		if(confgUrls!=null&&confgUrls.length>0){
			CollectionUtils.addAll(urls,confgUrls);
		}
		urls.add("/authentication/form");
	}


	@Override
	protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain)
			throws ServletException, IOException {
		boolean action =false;
		for(String url:urls){
			if(pathMatcher.match(url,httpServletRequest.getRequestURI())){
				action = true;
			}
		}
		if(action){
			try{
				//执行校验
				validate(new ServletWebRequest(httpServletRequest));

			}catch (ValidateCodeException e){

				authenticationFailureHandler.onAuthenticationFailure(httpServletRequest,httpServletResponse,e);
				return;
			}

		}
		filterChain.doFilter(httpServletRequest,httpServletResponse);
	}

	private void validate(ServletWebRequest request){
		ImageCode codeInSession = (ImageCode) sessionStrategy.getAttribute(request, ValidateCodeController.SESSION_KEY);

		String codeInRequest;
		try {
			//获取请求中的参数值
			codeInRequest = ServletRequestUtils.getStringParameter(request.getRequest(),
					"imageCode");
		} catch (ServletRequestBindingException e) {
			throw new ValidateCodeException("获取验证码的值失败");
		}

		if (StringUtils.isBlank(codeInRequest)) {
			throw new ValidateCodeException(/*processorType +*/ "验证码的值不能为空");
		}

		if (codeInSession == null) {
			throw new ValidateCodeException(/*processorType +*/ "验证码不存在");
		}

		if (codeInSession.isExpried()) {
			sessionStrategy.removeAttribute(request, ValidateCodeController.SESSION_KEY);
			throw new ValidateCodeException(/*processorType + */"验证码已过期");
		}

		if (!StringUtils.equals(codeInSession.getCode(), codeInRequest)) {
			throw new ValidateCodeException(/*processorType +*/ "验证码不匹配");
		}

		sessionStrategy.removeAttribute(request, ValidateCodeController.SESSION_KEY);
	}
}
