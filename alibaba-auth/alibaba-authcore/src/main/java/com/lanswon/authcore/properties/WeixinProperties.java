/**
 * 
 */
package com.lanswon.authcore.properties;

import lombok.*;

/**
 * 微信登录配置
 * @author GU-YW
 *
 */
@Data
@ToString
public class WeixinProperties  {
	
	/**
	 * 第三方id，用来决定发起第三方登录的url，默认是 weixin。
	 */
	private String providerId = "weixin";

}
