package com.lanswon.management.domain.entity;

import java.util.Date;
import javax.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Table(name = "jl_role")
public class JlRole {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 父级角色
     */
    @Column(name = "parent_id")
    private Long parentId;

    /**
     * 中文名°
     */
    @Column(name = "cn_name")
    private String cnName;

    /**
     * 英文名°
     */
    @Column(name = "en_name")
    private String enName;

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