/**
 * 
 */
package com.lanswon.authcore.properties;

import lombok.*;

/**
 * 第三方登录配置
 * @author GU-YW
 *
 */
@Data
@ToString
public class SocialProperties {
	
	private String filterProcessesUrl = "/auth";

	private QQProperties qq = new QQProperties();
	
	private WeixinProperties weixin = new WeixinProperties();
	
}
