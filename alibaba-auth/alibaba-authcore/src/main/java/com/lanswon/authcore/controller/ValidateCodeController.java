/**
 * 
 */
package com.lanswon.authcore.controller;


import com.lanswon.authcore.contants.SecurityConstants;
import com.lanswon.authcore.validatecode.ValidateCodeProcessor;
import com.lanswon.authcore.validatecode.ValidateCodeProcessorHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.ServletWebRequest;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


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
