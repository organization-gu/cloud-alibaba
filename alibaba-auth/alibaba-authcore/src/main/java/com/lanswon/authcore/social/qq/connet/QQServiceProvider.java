/**
 * 
 */
package com.lanswon.authcore.social.qq.connet;

import com.lanswon.authcore.social.qq.api.QQ;
import com.lanswon.authcore.social.qq.api.QQImpl;
import org.springframework.social.oauth2.AbstractOAuth2ServiceProvider;

/** QQ认证服务
 * @author GU-YW
 *
 */
public class QQServiceProvider extends AbstractOAuth2ServiceProvider<QQ> {

	private String appId;

	/**
	 * 导向QQ认证服务地址
	 */
	private static final String URL_AUTHORIZE = "https://graph.qq.com/oauth2.0/authorize";

	/**
	 * 获取Token地址
	 */
	private static final String URL_ACCESS_TOKEN = "https://graph.qq.com/oauth2.0/token";
	
	public QQServiceProvider(String appId, String appSecret) {
		super(new QQOAuth2Template(appId, appSecret, URL_AUTHORIZE, URL_ACCESS_TOKEN));
		this.appId = appId;
	}
	
	@Override
	public QQ getApi(String accessToken) {
		return new QQImpl(accessToken, appId);
	}

}
