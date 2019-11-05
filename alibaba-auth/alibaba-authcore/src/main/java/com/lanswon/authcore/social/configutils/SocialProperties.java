package com.lanswon.authcore.social.configutils;

/**
 * @Author GU-YW
 * @Date 2019/11/4 13:32
 * Description：从springboot1.5.6.RELEASE版本的org.springframework.boot.autoconfigure.social下拷贝的源码
 */
public class SocialProperties {

    private String appId;
    private String appSecret;
    public SocialProperties() {
    }
    public String getAppId() {
        return this.appId;
    }
    public void setAppId(String appId) {
        this.appId = appId;
    }
    public String getAppSecret() {
        return this.appSecret;
    }
    public void setAppSecret(String appSecret) {
        this.appSecret = appSecret;
    }
}
