package com.lanswon.authcore.social.configutils;

import org.springframework.core.env.Environment;
import org.springframework.social.config.annotation.ConnectionFactoryConfigurer;
import org.springframework.social.config.annotation.SocialConfigurerAdapter;
import org.springframework.social.connect.ConnectionFactory;

/**
 * @Author GU-YW
 * @Date 2019/11/4 13:25
 * Description：从springboot1.5.6.RELEASE版本的org.springframework.boot.autoconfigure.social下拷贝的源码
 */
public abstract class SocialAutoConfigurerAdapter extends SocialConfigurerAdapter {

    public SocialAutoConfigurerAdapter() {
    }
    @Override
    public void addConnectionFactories(ConnectionFactoryConfigurer configurer, Environment environment) {
        configurer.addConnectionFactory(this.createConnectionFactory());
    }
    protected abstract ConnectionFactory<?> createConnectionFactory();
}
