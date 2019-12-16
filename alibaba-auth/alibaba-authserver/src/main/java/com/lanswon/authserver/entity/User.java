package com.lanswon.authserver.entity;

import lombok.*;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class User {

    private String tableName;

    /**
     * 用户名
     */
    private String username;

    /**
     * 密码
     */
    private String password;

    /**
     * 昵称
     */
    private String nickname;

    /**
     * 电话
     */
    private String phone;

    /**
     * 邮箱
     */
    private String email;

    private Date createdTime;

    private Date updatedTime;

    private String pic;

    private String wxid;

    private String location;

    private Integer sex;

    private Long createdBy;

    private Long updatedBy;

    /**
     * 是否可用
     */
    private Boolean accountEnable;

    /**
     * 账户是否没过期
     */
    private Boolean accountNonExpired;

    /**
     * 密码(凭证)是否没过期
     */
    private Boolean credentialNonExpired;

    /**
     * 账户是否没锁定
     */
    private Boolean accountNonLock;

    /**
     * 部门
     */
    private Integer depId;
}