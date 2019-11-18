package com.lanswon.authbrowser.config;

import com.lanswon.authcore.authorize.AuthorizeConfigProviderManger;
import com.lanswon.authcore.config.autentication.FormAuthenticationSecurityConfig;
import com.lanswon.authcore.config.autentication.SmsCodeAuthenticationSecurityConfig;
import com.lanswon.authcore.config.security.ValidateCodeSecurityConfig;
import com.lanswon.authcore.properties.SecurityProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.security.web.session.InvalidSessionStrategy;
import org.springframework.security.web.session.SessionInformationExpiredStrategy;
import org.springframework.social.security.SpringSocialConfigurer;

import javax.annotation.Resource;
import javax.sql.DataSource;

/**
 * 浏览器安全配置
 * @Author GU-YW
 * @Date 2019/10/29 8:18
 */
@Configuration
public class BrowserSecurityConfig extends FormAuthenticationSecurityConfig {

    @Resource
    private SecurityProperties securityProperties;

    @Resource
    private DataSource dataSource;

    @Resource
    private UserDetailsService userDetailsService;

    @Resource
    private SmsCodeAuthenticationSecurityConfig smsCodeAuthenticationSecurityConfig;

    @Resource
    private ValidateCodeSecurityConfig validateCodeSecurityConfig;

    @Resource
    private SpringSocialConfigurer springSocialConfigurer;

    @Resource
    private SessionInformationExpiredStrategy sessionInformationExpiredStrategy;

    @Resource
    private InvalidSessionStrategy invalidSessionStrategy;

    @Resource
    private LogoutSuccessHandler logoutSuccessHandler;

    @Resource
    private AuthorizeConfigProviderManger authorizeConfigProviderManger;



    /**
     * remember-me 设置数据库基本信息
     * @return
     */
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
        //表单登录-------------------------------------------------------------------------------

        applyPasswordAuthenticationConfig(http);
        
        //过滤器配置
        http.apply(validateCodeSecurityConfig)
                .and()
            .apply(springSocialConfigurer)
                .and()
            //加入自定义配置手机验证码认证配置---------------------------------------------------------------------------
            .apply(smsCodeAuthenticationSecurityConfig)
                .and()
            //session管理----------------------------------------------------------------------------------
            .sessionManagement()
                //session 失效跳转地址
                .invalidSessionStrategy(invalidSessionStrategy)
                //单个用户只能一个session有效
                .maximumSessions(securityProperties.getBrowser().getSession().getMaximumSessions())
                //当由session登录后，就不允许再登录
                .maxSessionsPreventsLogin(securityProperties.getBrowser().getSession().isMaxSessionsPreventsLogin())
                .expiredSessionStrategy(sessionInformationExpiredStrategy)
                .and()
                .and()
            //rememberMe配置-------------------------------------------------------------------------------
            .rememberMe()
                .tokenRepository(persistentTokenRepository())
                .userDetailsService(userDetailsService)
                .tokenValiditySeconds(securityProperties.getBrowser().getRememberMeSeconds())
                .and()
            //退出配置-------------------------------------------------------------------------------
            .logout()
                .logoutUrl("/signOut")
                //退出成功处理与logoutSuccessHandler互斥
                //.logoutSuccessUrl(securityProperties.getBrowser().getLoginPage())
                .logoutSuccessHandler(logoutSuccessHandler)
                .deleteCookies("JSESSIONID")
                .and()
            //权限配置---------------------------------------------------------------------------------------
//            .authorizeRequests()
//            //将指定路径排除授权认证
//                .antMatchers(SecurityConstants.DEFAULT_VALIDATE_CODE_URL_PREFIX+"/*",
//                        SecurityConstants.DEFAULT_UNAUTHENTICATION_URL,
//                        SecurityConstants.DEFAULT_LOGIN_PROCESSING_URL_MOBILE,
//                        securityProperties.getBrowser().getSignUpUrl(),
//                        "/user/regist",
//                        securityProperties.getBrowser().getSession().getSessionInvalidUrl(),
//                        securityProperties.getBrowser().getSignOutSuccessUrl(),
////                        securityProperties.getBrowser().getSession().getSessionInvalidUrl()+".json",
////                        securityProperties.getBrowser().getSession().getSessionInvalidUrl()+".html",
//                        securityProperties.getBrowser().getLoginPage())
//                    .permitAll()
//                .anyRequest()
//                .authenticated()
//                .and()
            //暂时关闭跨域
            .csrf().disable();

        authorizeConfigProviderManger.config(http.authorizeRequests());
    }



}
