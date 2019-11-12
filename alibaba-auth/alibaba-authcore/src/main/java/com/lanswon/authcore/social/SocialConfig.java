/**
 * 
 */
package com.lanswon.authcore.social;

import javax.annotation.Resource;
import javax.sql.DataSource;

import com.lanswon.authcore.properties.SecurityProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.encrypt.Encryptors;
import org.springframework.social.config.annotation.EnableSocial;
import org.springframework.social.config.annotation.SocialConfigurerAdapter;
import org.springframework.social.connect.ConnectionFactoryLocator;
import org.springframework.social.connect.ConnectionSignUp;
import org.springframework.social.connect.UsersConnectionRepository;
import org.springframework.social.connect.jdbc.JdbcUsersConnectionRepository;
import org.springframework.social.connect.web.ProviderSignInUtils;
import org.springframework.social.security.SpringSocialConfigurer;

/**
 * 配置UsersConnectionRepository将user和qq用户信息进行绑定
 * @author GU-YW
 *
 */
@Configuration
@EnableSocial
public class SocialConfig extends SocialConfigurerAdapter {

	@Autowired
	private DataSource dataSource;

	@Resource
	private SecurityProperties securityProperties;

	/**
	 * 设置spring social成功处理器相关的类
	 */
	@Autowired(required = false)
	private SocialAuthenticationFilterPostProcessor socialAuthenticationFilterPostProcessor;

	/**
	 * 并不一定所有的系统都会在用户进行第三方登陆时“偷偷地”给用户注册一个新用户
	 * 所以这里需要标明required = false
	 */
	@Autowired(required = false)
	private ConnectionSignUp connectionSignUp;

	@Override
	public UsersConnectionRepository getUsersConnectionRepository(ConnectionFactoryLocator connectionFactoryLocator) {
		/**
		 * 第二个参数的作用：根据条件查找该用哪个ConnectionFactory来构建Connection对象
		 * 第三个参数的作用: 对插入到userconnection表中的数据进行加密和解密
		 */
		JdbcUsersConnectionRepository repository = new JdbcUsersConnectionRepository(dataSource,
				connectionFactoryLocator, Encryptors.noOpText());
		//表名可以加一个前缀 sql脚本可以在JdbcUsersConnectionRepository找到
		//repository.setTablePrefix("lanswon_");
		//在首次登录就可以自动保存个人信息配置

		if(connectionSignUp != null) {
			repository.setConnectionSignUp(connectionSignUp);
		}
		return repository;
	}

	/**
	 * 通过apply()该实例，可以将SocialAuthenticationFilter加入到spring-security的过滤器链
	 */
	@Bean(name = "mySpringSocialConfigurer")
	public SpringSocialConfigurer socialSecurityConfig() {
		// 默认配置类，进行组件的组装
		// 包括了过滤器SocialAuthenticationFilter 添加到security过滤链中
		String filterProcessesUrl = securityProperties.getSocial().getFilterProcessesUrl();
		MySpringSocialConfigurer configurer = new MySpringSocialConfigurer(filterProcessesUrl);
		////指定SpringSocial/SpringSecurity跳向注册页面时的url为我们配置的url
		configurer.signupUrl(securityProperties.getBrowser().getSignUpUrl());

		//设置springsocial的认证成功处理器 -- app下可以返回token，browser下使用spring-security默认的
		configurer.setSocialAuthenticationFilterPostProcessor(socialAuthenticationFilterPostProcessor);
		return configurer;
	}

	/**
	 * 获取第三方登录的个人信息工具类
	 * @param connectionFactoryLocator
	 * @return
	 */
	@Bean
	public ProviderSignInUtils providerSignInUtils(ConnectionFactoryLocator connectionFactoryLocator) {
		return new ProviderSignInUtils(connectionFactoryLocator,
				getUsersConnectionRepository(connectionFactoryLocator)) {
		};
	}
}
