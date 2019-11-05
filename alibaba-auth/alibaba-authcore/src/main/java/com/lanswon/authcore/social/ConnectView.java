/**
 * 
 */
package com.lanswon.authcore.social;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.view.AbstractView;

/**
 * 绑定社交账号时需要提供“xxx/connected”视图，其他由spring来做Spring默认实现了ConnectController
 * 需要注意的时在 前端提供绑定链接时只要“/connect/xxx”即可   xxx为系统配置的providerId
 * 解绑链接请求地址不变，method="delete"即可
 * @author
 *
 */
public class ConnectView extends AbstractView {

	@Override
	protected void renderMergedOutputModel(Map<String, Object> model, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		response.setContentType("text/html;charset=UTF-8");
		if (model.get("connection") == null) {
			response.getWriter().write("<h3>解绑成功</h3>");
		} else {
			response.getWriter().write("<h3>绑定成功</h3>");
		}

	}

}
