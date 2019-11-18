package com.lanswon.authdemo.security;

import com.lanswon.authcore.authorize.AuthorizeConfigProvider;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.stereotype.Component;

/** demo权限配置
 * @Author GU-YW
 * @Date 2019/11/14 16:51
 */
@Component
@Order(Integer.MAX_VALUE)
public class DemoAuthorizeConfigProvider implements AuthorizeConfigProvider {

    @Override
    public boolean config(ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry config) {
        config.antMatchers("/user/register")
                .permitAll()
                .anyRequest()
               .access("@rbacService.hasPermission(request, authentication)");
        return true;
    }
}
