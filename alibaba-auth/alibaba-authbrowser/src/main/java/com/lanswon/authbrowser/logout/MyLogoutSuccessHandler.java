package com.lanswon.authbrowser.logout;

import com.lanswon.authcore.properties.SecurityProperties;
import com.lanswon.authcore.support.SimpleResponse;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Author GU-YW
 * @Date 2019/11/7 16:53
 */
@Slf4j
@Data
public class MyLogoutSuccessHandler implements LogoutSuccessHandler {

    private SecurityProperties securityProperties;

    private ObjectMapper objectMapper = new ObjectMapper();
    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        log.info("系统退出成功处理…………");
        String signOutUrl = securityProperties.getBrowser().getSignOutSuccessUrl();
        if(StringUtils.isBlank(signOutUrl)){
            response.setContentType("application/json;charset=UTF-8");
            response.setStatus(HttpStatus.OK.value());
            response.getWriter().write(objectMapper.writeValueAsString(new SimpleResponse("退出成功")));
        }else{
            response.sendRedirect(signOutUrl);
        }

    }
}
