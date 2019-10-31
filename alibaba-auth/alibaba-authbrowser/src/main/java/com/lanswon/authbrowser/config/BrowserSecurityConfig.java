package com.lanswon.authbrowser.config;

import com.lanswon.authbrowser.authentication.CustomAuthenticationFailureHandler;
import com.lanswon.authbrowser.authentication.CustomAuthenticationSuccessHandler;
import com.lanswon.authcore.properties.SecurityProperties;
import com.lanswon.authcore.filter.ValidateCodeFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

import javax.sql.DataSource;

/**
 * 浏览器安全配置
 * @Author GU-YW
 * @Date 2019/10/29 8:18
 */
@Configuration
public class BrowserSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired(required = false)
    SecurityProperties securityProperties;

    @Autowired
    CustomAuthenticationSuccessHandler customAuthenticationSuccessHandler;

    @Autowired
    CustomAuthenticationFailureHandler customAuthenticationFailureHandler;

    @Autowired
    DataSource dataSource;

    @Autowired
    UserDetailsService userDetailsService;

    @Bean
    public PasswordEncoder passwordEncoder(){
        return  new BCryptPasswordEncoder();
    }

    @Bean
    public PersistentTokenRepository persistentTokenRepository(){
        //存储token
        JdbcTokenRepositoryImpl jdbcTokenRepository=new JdbcTokenRepositoryImpl();
        jdbcTokenRepository.setDataSource(dataSource);
        //表存在就不要设置成true
        jdbcTokenRepository.setCreateTableOnStartup(false);
        return jdbcTokenRepository;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //basic登录
//        http.httpBasic()
//                .and()
//                .authorizeRequests()
//                .anyRequest()
//                .authenticated();

        ValidateCodeFilter validateCodeFilter = new ValidateCodeFilter();
        validateCodeFilter.setAuthenticationFailureHandler(customAuthenticationFailureHandler);
        validateCodeFilter.setSecurityProperties(securityProperties);
        validateCodeFilter.afterPropertiesSet();

        http.addFilterBefore(validateCodeFilter, UsernamePasswordAuthenticationFilter.class)
                //表单登录-------------------------------------------------------------------------------
                .formLogin()
                //.loginPage("/imooc-signIn.html")
                .loginPage("/authentication/require")
                //替换默认的login登录请求
                .loginProcessingUrl("/authentication/form")
                //加入自定义认证成功处理（默认时跳转上一个请求）
                .successHandler(customAuthenticationSuccessHandler)
                //加入自定义认证失败处理
                .failureHandler(customAuthenticationFailureHandler)
                .and()

                //rememberMe配置-------------------------------------------------------------------------------
                .rememberMe()
                .tokenRepository(persistentTokenRepository())
                .userDetailsService(userDetailsService)
                .tokenValiditySeconds(securityProperties.getBrowser().getRememberMeSeconds())
                .and()

                //权限配置-------------------------------------------------------------------------------
                .authorizeRequests()
                //将指定路径排除授权认证
                .antMatchers("/code/*","/authentication/require",securityProperties.getBrowser().getLoginPage()).permitAll()
                //.antMatchers("").permitAll()
                .anyRequest()
                .authenticated()
                .and()

                //暂时关闭跨域
                .csrf().disable();
    }



}
