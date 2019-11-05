/**
 * 
 */
package com.lanswon.authcore.social.qq.config;

import com.lanswon.authcore.properties.QQProperties;
import com.lanswon.authcore.properties.SecurityProperties;
import com.lanswon.authcore.social.configutils.SocialAutoConfigurerAdapter;
import com.lanswon.authcore.social.qq.connet.QQConnectionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;
import org.springframework.social.connect.ConnectionFactory;


/**
 * @author GU-YW
 *
 */
@Configuration
@ConditionalOnProperty(prefix = "lanswon.security.social.qq", name = "app-id")
public class QQAutoConfig extends SocialAutoConfigurerAdapter {

	@Autowired
	private SecurityProperties securityProperties;

	@Override
	protected ConnectionFactory<?> createConnectionFactory() {
		QQProperties qqConfig = securityProperties.getSocial().getQq();
		return new QQConnectionFactory(qqConfig.getProviderId(), qqConfig.getAppId(), qqConfig.getAppSecret());
	}

}
