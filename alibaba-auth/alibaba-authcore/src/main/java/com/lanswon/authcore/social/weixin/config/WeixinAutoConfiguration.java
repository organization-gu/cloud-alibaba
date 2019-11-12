/**
 * 
 */
package com.lanswon.authcore.social.weixin.config;

import com.lanswon.authcore.properties.SecurityProperties;
import com.lanswon.authcore.properties.WeixinProperties;
import com.lanswon.authcore.social.ConnectView;
import com.lanswon.authcore.social.configutils.SocialAutoConfigurerAdapter;
import com.lanswon.authcore.social.weixin.connect.WeixinConnectionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.social.connect.ConnectionFactory;
import org.springframework.web.servlet.View;

/**
 * 微信登录配置
 * @author GU-YW
 * Description：将微信连接工厂注入到spring容器
 */
@Configuration
@ConditionalOnProperty(prefix = "lanswon.security.social.weixin", name = "app-id")
public class WeixinAutoConfiguration extends SocialAutoConfigurerAdapter {

	@Autowired
	private SecurityProperties securityProperties;

	@Override
	protected ConnectionFactory<?> createConnectionFactory() {
		WeixinProperties weixinConfig = securityProperties.getSocial().getWeixin();
		return new WeixinConnectionFactory(weixinConfig.getProviderId(), weixinConfig.getAppId(),
				weixinConfig.getAppSecret());
	}

	/***
	 * connect/weixinConnected 绑定成功的视图
	 * connect/weixinConnect 解绑的视图
	 *
	 * 两个视图可以写在一起，通过判断Model对象里有没有Connection对象来确定究竟是解绑还是绑定
	 */
	@Bean({"connect/weixinConnect", "connect/weixinConnected"})
	@ConditionalOnMissingBean(name = "weixinConnectedView")
	public View weixinConnectedView() {
		return new ConnectView();
	}
	
}
