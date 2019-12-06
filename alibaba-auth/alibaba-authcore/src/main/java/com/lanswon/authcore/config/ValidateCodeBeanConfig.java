/**
 * 
 */
package com.lanswon.authcore.config;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lanswon.authcore.validatecode.ValidateCodeGenerator;
import com.lanswon.authcore.properties.SecurityProperties;
import com.lanswon.authcore.validatecode.image.ImageCodeGenerator;
import com.lanswon.authcore.validatecode.sms.DefaultSmsCodeSender;
import com.lanswon.authcore.validatecode.sms.SmsCodeGenerator;
import com.lanswon.authcore.validatecode.sms.SmsCodeSender;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * @author GU-YW
 *
 */
@Configuration
@EnableConfigurationProperties(SecurityProperties.class)
public class ValidateCodeBeanConfig {


//	@Bean
//	public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
//		//设置序列化
//		Jackson2JsonRedisSerializer jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer(Object.class);
//		ObjectMapper om = new ObjectMapper();
//		om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
//		om.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
//		jackson2JsonRedisSerializer.setObjectMapper(om);
//		// 配置redisTemplate
//		RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
//		redisTemplate.setEnableDefaultSerializer(false);
//		redisTemplate.setConnectionFactory(redisConnectionFactory);
//		RedisSerializer stringSerializer = new StringRedisSerializer();
//		// key序列化
//		redisTemplate.setKeySerializer(stringSerializer);
//		// value序列化
//		redisTemplate.setValueSerializer(jackson2JsonRedisSerializer);
//		// Hash key序列化
//		redisTemplate.setHashKeySerializer(stringSerializer);
//		// Hash value序列化
//		redisTemplate.setHashValueSerializer(jackson2JsonRedisSerializer);
//		redisTemplate.afterPropertiesSet();
//		return redisTemplate;
//	}

	
	@Bean(name = "imageValidateCodeGenerator")
	@ConditionalOnMissingBean(name="imageValidateCodeGenerator")
	public ValidateCodeGenerator imageValidateCodeGenerator(SecurityProperties securityProperties) {
		ImageCodeGenerator codeGenerator = new ImageCodeGenerator();
		codeGenerator.setSecurityProperties(securityProperties);
		return codeGenerator;
	}

	@Bean(name = "smsValidateCodeGenerator")
	@ConditionalOnMissingBean(name = "smsValidateCodeGenerator")
	public ValidateCodeGenerator smsValidateCodeGenerator(SecurityProperties securityProperties) {
		SmsCodeGenerator smsCodeGenerator = new SmsCodeGenerator();
		smsCodeGenerator.setSecurityProperties(securityProperties);
		return smsCodeGenerator;
	}
	
	@Bean
	@ConditionalOnMissingBean
	public SmsCodeSender smsCodeSender() {
		return new DefaultSmsCodeSender();
	}


	@Bean
	public PasswordEncoder passwordEncoder(){
		return  new BCryptPasswordEncoder();
	}

}
