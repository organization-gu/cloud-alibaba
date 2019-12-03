package com.lanswon.management.domain.entity.user;

import java.util.Date;
import javax.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Table(name = "jl_user")
public class JlUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

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

    @Column(name = "created_time")
    private Date createdTime;

    @Column(name = "updated_time")
    private Date updatedTime;

    private String pic;

    private String wxid;

    private String location;

    private Integer sex;

    @Column(name = "created_by")
    private Long createdBy;

    @Column(name = "updated_by")
    private Long updatedBy;

    /**
     * 是否可用
     */
    @Column(name = "account_enable")
    private Boolean accountEnable;

    /**
     * 账户是否没过期
     */
    @Column(name = "account_non_expired")
    private Boolean accountNonExpired;

    /**
     * 密码(凭证)是否没过期
     */
    @Column(name = "credential_non_expired")
    private Boolean credentialNonExpired;

    /**
     * 账户是否没锁定
     */
    @Column(name = "account_non_lock")
    private Boolean accountNonLock;

    /**
     * 部门
     */
    @Column(name = "dep_id")
    private Integer depId;
}