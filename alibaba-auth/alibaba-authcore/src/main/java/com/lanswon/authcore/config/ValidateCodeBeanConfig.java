/**
 * 
 */
package com.lanswon.authcore.config;

import com.lanswon.authcore.properties.SecurityProperties;
import com.lanswon.authcore.validatecode.ValidateCode;
import com.lanswon.authcore.validatecode.ValidateCodeGenerator;
import com.lanswon.authcore.validatecode.image.ImageCodeGenerator;
import com.lanswon.authcore.validatecode.sms.DefaultSmsCodeSender;
import com.lanswon.authcore.validatecode.sms.SmsCodeGenerator;
import com.lanswon.authcore.validatecode.sms.SmsCodeSender;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author zhailiang
 *
 */
@Configuration
public class ValidateCodeBeanConfig {

	
	@Bean(name = "imageCodeGenerator")
	@ConditionalOnMissingBean(name="imageCodeGenerator")
	public ValidateCodeGenerator imageValidateCodeGenerator(SecurityProperties securityProperties) {
		ImageCodeGenerator codeGenerator = new ImageCodeGenerator();
		codeGenerator.setSecurityProperties(securityProperties);
		return codeGenerator;
	}

	@Bean(name = "smsCodeGenerator")
	@ConditionalOnMissingBean(name = "smsCodeGenerator")
	public ValidateCodeGenerator smsCodeGenerator(SecurityProperties securityProperties) {
		SmsCodeGenerator smsCodeGenerator = new SmsCodeGenerator();
		smsCodeGenerator.setSecurityProperties(securityProperties);
		return smsCodeGenerator;
	}
	
	@Bean
	@ConditionalOnMissingBean
	public SmsCodeSender smsCodeSender() {
		return new DefaultSmsCodeSender();
	}

}
