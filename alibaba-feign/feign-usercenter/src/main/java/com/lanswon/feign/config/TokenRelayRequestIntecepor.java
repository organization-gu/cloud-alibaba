package com.lanswon.feign.config;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * feign 拦截器token传递
 * @Author GU-YW
 * @Date 2019/10/14 14:49
 */
public class TokenRelayRequestIntecepor implements RequestInterceptor {
    @Override
    public void apply(RequestTemplate template) {
        // 1. 获取到token
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        ServletRequestAttributes attributes = (ServletRequestAttributes) requestAttributes;
        HttpServletRequest request = attributes.getRequest();
        String token = request.getHeader("X-Token");

        // 2. 将token传递
        if (!StringUtils.isEmpty(token)) {
            template.header("X-Token", token);
        }

    }
}
