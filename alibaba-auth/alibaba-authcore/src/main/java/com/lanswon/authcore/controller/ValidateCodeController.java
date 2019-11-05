/**
 * 
 */
package com.lanswon.authcore.controller;


import com.lanswon.authcore.contants.SecurityConstants;
import com.lanswon.authcore.properties.SecurityProperties;
import com.lanswon.authcore.validatecode.ValidateCode;
import com.lanswon.authcore.validatecode.ValidateCodeGenerator;
import com.lanswon.authcore.validatecode.ValidateCodeProcessor;
import com.lanswon.authcore.validatecode.ValidateCodeProcessorHolder;
import com.lanswon.authcore.validatecode.image.ImageCode;
import com.lanswon.authcore.validatecode.sms.SmsCodeSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.connect.web.HttpSessionSessionStrategy;
import org.springframework.social.connect.web.SessionStrategy;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.ServletWebRequest;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Map;
import java.util.Random;


/**
 * @author GU-YW
 *
 */
@RestController
public class ValidateCodeController {

//	private SessionStrategy sessionStrategy=new HttpSessionSessionStrategy();
//
//	public static final  String SESSION_KEY = "SESSION_KEY_IMAGE_CODE";
//
//	@Resource(name = "imageValidateCodeGenerator")
//	ValidateCodeGenerator imageCodeGenerator;
//
//	@Resource(name = "smsCodeGenerator")
//	ValidateCodeGenerator smsCodeGenerator;
//
//	@Resource
//	SmsCodeSender smsCodeSender;
//
//	@Resource
//
//	/**
//	 * 创建验证码，根据验证码类型不同，调用不同的 {@link }接口实现ValidateCodeProcessor
//	 *
//	 * @param request
//	 * @param response
//	 * @param
//	 * @throws Exception
//	 */
////	@GetMapping(SecurityConstants.DEFAULT_VALIDATE_CODE_URL_PREFIX + "/{type}")
//	@GetMapping("code/image")
//	public void createCode(HttpServletRequest request, HttpServletResponse response/*, @PathVariable String type*/)
//			throws Exception {
//		ImageCode imageCode = (ImageCode) imageCodeGenerator.validateCodeGenerate(new ServletWebRequest(request));
//		sessionStrategy.setAttribute(new ServletWebRequest(request),SESSION_KEY,imageCode);
//		//将验证码图片发送出去
//		ImageIO.write(imageCode.getImage(),"JPEG",response.getOutputStream());
//
//	}
//
//
//	@GetMapping("code/sms")
//	public void createSmsCode(HttpServletRequest request, HttpServletResponse response/*, @PathVariable String type*/)
//			throws Exception {
//		ValidateCode smsCode =  smsCodeGenerator.validateCodeGenerate(new ServletWebRequest(request));
//		sessionStrategy.setAttribute(new ServletWebRequest(request),SESSION_KEY,smsCode);
//		// 获取请求参数的手机号码
//		String mobile = ServletRequestUtils.getRequiredStringParameter(request,"mobile");
//		//发送短信的服务商
//		smsCodeSender.send(mobile,smsCode.getCode());
//
//
//	}


	//=============================代码重构后============================================================================

	@Resource
	private ValidateCodeProcessorHolder validateCodeProcessorHolder;

	/**
	 * 创建验证码，根据验证码类型不同，调用不同的 {@link ValidateCodeProcessor}接口实现
	 *
	 * @param request
	 * @param response
	 * @param type
	 * @throws Exception
	 */
	@GetMapping(SecurityConstants.DEFAULT_VALIDATE_CODE_URL_PREFIX + "/{type}")
	public void createCode(HttpServletRequest request, HttpServletResponse response, @PathVariable String type)
			throws Exception {
		validateCodeProcessorHolder.findValidateCodeProcessor(type).create(new ServletWebRequest(request, response));
	}




}
