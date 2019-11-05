/**
 * 
 */
package com.lanswon.authcore.social.qq.connet;


import com.lanswon.authcore.social.qq.api.QQ;
import org.springframework.social.connect.support.OAuth2ConnectionFactory;

/** 连接认证服务工厂类
 * @author GU-YW
 *
 */
public class QQConnectionFactory extends OAuth2ConnectionFactory<QQ> {

	public QQConnectionFactory(String providerId, String appId, String appSecret) {
		super(providerId, new QQServiceProvider(appId, appSecret), new QQAdapter());
	}

}
