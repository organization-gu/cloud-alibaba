/**
 * 
 */
package com.lanswon.authcore.properties;

import lombok.*;

/**
 * 手机验证码配置
 * @author GU-YW
 *
 */
@Data
@ToString
public class SmsCodeProperties {
	
	private int length = 6;

	private int expireIn = 60;
	
	private String url;

}
