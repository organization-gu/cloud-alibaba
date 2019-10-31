/**
 * 
 */
package com.lanswon.authcore.validatecode.impl;

import java.util.Map;

import com.lanswon.authcore.contants.ValidateCodeType;
import com.lanswon.authcore.controller.ValidateCodeController;
import com.lanswon.authcore.validatecode.ValidateCode;
import com.lanswon.authcore.validatecode.ValidateCodeException;
import com.lanswon.authcore.validatecode.ValidateCodeGenerator;
import com.lanswon.authcore.validatecode.ValidateCodeProcessor;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.connect.web.HttpSessionSessionStrategy;
import org.springframework.social.connect.web.SessionStrategy;
import org.springframework.web.context.request.ServletWebRequest;


/**
 * @author 实现ValidateCodeProcessor 抽象类
 *
 */
public abstract class AbstractValidateCodeProcessor<C extends ValidateCode> implements ValidateCodeProcessor {

	/**
	 * 操作session的工具类
	 */
	private SessionStrategy sessionStrategy = new HttpSessionSessionStrategy();

	/**
	 * spring 自动收集系统中所有的 {@link ValidateCodeGenerator} 接口的实现。
	 * key 是bean 名字
	 */
	@Autowired
	private Map<String, ValidateCodeGenerator> validateCodeGenerators;


	@Override
	public void create(ServletWebRequest request) throws Exception {
		C validateCode = generate(request);
		save(request, validateCode);
		send(request, validateCode);
	}

	/**
	 * 生成校验码
	 * 
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private C generate(ServletWebRequest request) {
//		String type = getValidateCodeType(request).toString().toLowerCase();
		String type =getProcessorType(request);
//		String generatorName = type + ValidateCodeGenerator.class.getSimpleName();

		String generatorName=type+"CodeGenerator";
		ValidateCodeGenerator validateCodeGenerator = validateCodeGenerators.get(generatorName);
		if (validateCodeGenerator == null) {
			throw new ValidateCodeException("验证码生成器" + generatorName + "不存在");
		}
		return (C) validateCodeGenerator.validateCodeGenerate(request);
	}

	/**
	 * 保存校验码
	 * 
	 * @param request
	 * @param validateCode
	 */
	private void save(ServletWebRequest request, C validateCode) {

		sessionStrategy.setAttribute(request, ValidateCodeController.SESSION_KEY, validateCode);
//		sessionStrategy.setAttribute(request, getSessionKey(request), validateCode);
	}

	/**
	 * 构建验证码放入session时的key
	 * 
	 * @param request
	 * @return
	 */
	private String getSessionKey(ServletWebRequest request) {
		return SESSION_KEY_PREFIX + getProcessorType(request).toUpperCase();
	}

	/**
	 * 发送校验码，由子类实现
	 * 
	 * @param request
	 * @param validateCode
	 * @throws Exception
	 */
	protected abstract void send(ServletWebRequest request, C validateCode) throws Exception;

	/**
	 * 根据请求的url获取校验码的类型
	 * 
	 * @param
	 * @return
	 */
	private String getProcessorType(ServletWebRequest servletWebRequest){
		return StringUtils.substringAfter(servletWebRequest.getRequest().getRequestURI(),"/code/");
	}
//	private ValidateCodeType getValidateCodeType(ServletWebRequest request) {
//		String type = StringUtils.substringBefore(getClass().getSimpleName(), "CodeProcessor");
//		return ValidateCodeType.valueOf(type.toUpperCase());
//	}

	@SuppressWarnings("unchecked")
	@Override
	public void validate(ServletWebRequest request) {

//		ValidateCodeType processorType = getValidateCodeType(request);
//		String sessionKey = getSessionKey(request);
//
//		C codeInSession = (C) sessionStrategy.getAttribute(request, sessionKey);
//
//		String codeInRequest;
//		try {
//			codeInRequest = ServletRequestUtils.getStringParameter(request.getRequest(),
//					processorType.getParamNameOnValidate());
//		} catch (ServletRequestBindingException e) {
//			throw new ValidateCodeException("获取验证码的值失败");
//		}
//
//		if (StringUtils.isBlank(codeInRequest)) {
//			throw new ValidateCodeException(processorType + "验证码的值不能为空");
//		}
//
//		if (codeInSession == null) {
//			throw new ValidateCodeException(processorType + "验证码不存在");
//		}
//
//		if (codeInSession.isExpried()) {
//			sessionStrategy.removeAttribute(request, sessionKey);
//			throw new ValidateCodeException(processorType + "验证码已过期");
//		}
//
//		if (!StringUtils.equals(codeInSession.getCode(), codeInRequest)) {
//			throw new ValidateCodeException(processorType + "验证码不匹配");
//		}
//
//		sessionStrategy.removeAttribute(request, sessionKey);
	}

}
