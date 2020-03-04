package com.lanswon.authserver.entity;

import javax.validation.constraints.NotBlank;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @Description:
 * @Author GU-YW
 * @Date 2019/11/18 14:10
 */
@Getter
@Setter
@ToString
@ApiModel(value = "系统注册")
public class SysSignupInfo {
    /**
     * 系统注册分配ID
     */
    @NotBlank(message = "注册分配ID不能为空")
    private String sysClientId;

    /**
     * 系统名
     */
    private String sysName;

    /**
     * 系统主页地址
     */
    private String sysIndex;

    /**
     * 系统注册分配密码
     */
    @NotBlank(message = "注册密码不能为空")
    private String sysPassword;

    /**
     * token有效期
     */
    private Integer tokenValiditySeconds;

    /**
     * 刷新token有效期
     */
    private Integer refreshTokenValiditySeconds;
}