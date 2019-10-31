/**
 * 
 */
package com.lanswon.authcore.properties;

import com.lanswon.authcore.contants.LoginResponseType;
import com.lanswon.authcore.contants.SecurityConstants;
import lombok.*;

/**
 * 浏览器安全配置
 * @author GU-YW
 *
 */
@Data
@ToString
public class BrowserProperties {
	
	private SessionProperties session = new SessionProperties();
	
	private String signUpUrl = "/imooc-signUp.html";
	
	private String loginPage = SecurityConstants.DEFAULT_LOGIN_PAGE_URL;
	
	private LoginResponseType loginType = LoginResponseType.JSON;
	
	private int rememberMeSeconds = 3600;
	
}
