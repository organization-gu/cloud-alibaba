package com.lanswon.authapp.social.processor;

import com.lanswon.authcore.social.MySpringSocialConfigurer;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

/**
 * @Author GU-YW
 * @Date 2019/11/12 10:29
 */
@Component
public class SpringSocialConfigurerPostProcessor implements BeanPostProcessor {

    /***
     * spring启动时所有的bean初始化之前都会调用该方法 --- 可以在bean初始化之前对bean做一些操作
     * @param bean
     * @param beanName
     * @return
     * @throws BeansException
     */
    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }

    /***
     * spring启动时所有的bean初始化之后都会调用该方法 --- 可以对初始化好的bean做一些修改
     * @param bean
     * @param beanName
     * @return
     * @throws BeansException
     */
    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        if(StringUtils.equals(beanName, "mySpringSocialConfigurer")){
            MySpringSocialConfigurer config = (MySpringSocialConfigurer) bean;
            config.signupUrl("/social/signUp");
            return config;
        }
        return bean;
    }
}
