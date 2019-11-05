/**
 * 
 */
package com.lanswon.authcore.properties;

import com.lanswon.authcore.social.configutils.SocialProperties;
import lombok.*;

/**
 * qq 登录验证
 * @author GU-YW
 *
 */
@Setter
@ToString
@Getter
public class QQProperties extends SocialProperties {

	//提供默认的providerId

	private String providerId = "qq";

}
