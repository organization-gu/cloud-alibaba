package com.lanswon.authcore.validatecode.sms;

/**系统默认的实现（）
 * @author zhailiang
 *
 */
public class DefaultSmsCodeSender implements SmsCodeSender {


	@Override
	public void send(String mobile, String code) {
		System.out.println("向手机"+mobile+"发送短信验证码"+code);
	}

}
