package com.lanswon.authcore.social;

import org.springframework.social.security.SocialAuthenticationFilter;

/**
 * @author : GU-YW
 * @date : 2019/1q/12 9:14
 * Description：指定spring social成功处理器的接口
 */
public interface SocialAuthenticationFilterPostProcessor {

    /**
     * 参数为spring social的过滤器
     * @param socialAuthenticationFilter
     */
    void process(SocialAuthenticationFilter socialAuthenticationFilter);
}
