/**
 * 
 */
package com.lanswon.authdemo.code;

import com.lanswon.authcore.validatecode.ValidateCodeGenerator;
import com.lanswon.authcore.validatecode.image.ImageCode;
import org.springframework.web.context.request.ServletWebRequest;

/**
 * @author zhailiang
 *
 */
//@Component
public class DemoImageCodeGenerator implements ValidateCodeGenerator {

	@Override
	public ImageCode validateCodeGenerate(ServletWebRequest request) {
		System.out.println("更高级的图形验证码生成代码");
		return null;
	}

}
