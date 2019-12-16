package com.lanswon.authserver.security;

import com.lanswon.authcore.authorize.AuthorizeConfigProvider;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.stereotype.Component;

/** 扩展权限配置
 * @Author GU-YW
 * @Date 2019/11/14 16:51
 */
@Component
@Order(Integer.MAX_VALUE)
public class SpreadAuthorizeConfigProvider implements AuthorizeConfigProvider {

    @Override
    public boolean config(ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry config) {
        config.antMatchers(
                "/actuator/**",
                "/favicon.ico",
                "/*swagger*/**",
                "/webjars/**",
                "/v2/**",
                "/sys/**",
                "/login.html",
                "/js/**",
                "/css/**",
                "/images/**",
                "/oauth/checkToken")
                .permitAll()
                .anyRequest()
                .authenticated()
                /*.access("@rbacService.hasPermission(request, authentication)")*/;
        return true;
    }
}
