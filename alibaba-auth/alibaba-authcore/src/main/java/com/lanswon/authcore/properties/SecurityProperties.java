/**
 * 
 */
package com.lanswon.authcore.properties;

import lombok.*;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 安全配置
 * @author GU-YW
 *
 */
@Data
@ConfigurationProperties(prefix = "lanswon.security")
@ToString
public class SecurityProperties {
	
	private BrowserProperties browser = new BrowserProperties();
	
	private ValidateCodeProperties code = new ValidateCodeProperties();
	
	private SocialProperties social = new SocialProperties();

}

