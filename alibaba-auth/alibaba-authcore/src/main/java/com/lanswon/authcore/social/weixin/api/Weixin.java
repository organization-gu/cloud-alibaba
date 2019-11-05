/**
 * 
 */
package com.lanswon.authcore.social.weixin.api;

/**
 * 微信API调用接口
 * 
 * @author GU-YW
 *
 */
public interface Weixin {


	WeixinUserInfo getUserInfo(String openId);
	
}
