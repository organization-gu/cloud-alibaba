package com.lanswon.management.aop.log.util;

import cn.hutool.core.util.URLUtil;
import cn.hutool.extra.servlet.ServletUtil;
import cn.hutool.http.HttpUtil;
import com.lanswon.management.aop.log.SysLog;
import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Date;
import java.util.Objects;

/**
 * @Description: 系统日志工具类
 * @Author GU-YW
 * @Date 2019/12/4 13:39
 */
public class SysLogUtils {

    public static SysLog getSysLog()  {
        HttpServletRequest request = ((ServletRequestAttributes) Objects
                .requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest();
        SysLog sysLog = new SysLog();
        sysLog.setRemoteAddr(ServletUtil.getClientIP(request));
        sysLog.setRequestUri(URLUtil.getPath(request.getRequestURI()));
        sysLog.setMethod(request.getMethod());
        sysLog.setUserAgent(request.getHeader("user-agent"));
        sysLog.setCreateUser(request.getParameter("username"));
        String params = "";
        try {
            params = URLDecoder.decode(HttpUtil.toParams(request.getParameterMap()),"UTF-8");
        } catch (UnsupportedEncodingException e) {
            params = HttpUtil.toParams(request.getParameterMap());
        }
        sysLog.setParams(params);
//        sysLog.setServiceId(getClientId());
        sysLog.setCreateTime(new Date());

        return sysLog;
    }

    /**
     * 获取客户端
     *
     * @return clientId
     */
//    private String getClientId() {
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        if (authentication instanceof OAuth2Authentication) {
//            OAuth2Authentication auth2Authentication = (OAuth2Authentication) authentication;
//            return auth2Authentication.getOAuth2Request().getClientId();
//        }
//        return null;
//    }
}
