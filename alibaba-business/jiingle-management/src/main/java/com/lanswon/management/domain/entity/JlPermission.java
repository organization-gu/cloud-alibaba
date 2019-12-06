package com.lanswon.management.domain.entity;

import java.util.Date;
import javax.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Table(name = "jl_permission")
public class JlPermission {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 父id
     */
    @Column(name = "parent_id")
    private Long parentId;

    /**
     * 权限名称(中文)
     */
    @Column(name = "cn_name")
    private String cnName;

    /**
     * 权限名称(英文)
     */
    @Column(name = "en_name")
    private String enName;

    /**
     * 请求方法
     */
    private String method;

    /**
     * 请求路径
     */
    private String url;

    /**
     * 类型(1.页面;2.按钮)
     */
    private Integer type;

    /**
     * 描述
     */
    private String description;

    @Column(name = "created_time")
    private Date createdTime;

    @Column(name = "updated_time")
    private Date updatedTime;

    @Column(name = "created_by")
    private Long createdBy;

    @Column(name = "updated_by")
    private Long updatedBy;
}