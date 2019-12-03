package com.lanswon.management.domain.entity.department;

import java.util.Date;
import javax.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Table(name = "jl_department")
public class JlDepartment {
    /**
     * 部门代码
     */
    @Id
    @Column(name = "dep_id")
    private Integer depId;

    /**
     * 部门名称
     */
    @Column(name = "dep_name")
    private String depName;

    /**
     * 部门领导
     */
    @Column(name = "dep_lead_id")
    private String depLeadId;

    /**
     * 创建时间
     */
    @Column(name = "crt_date")
    private Date crtDate;

    /**
     * 最后修改时间
     */
    @Column(name = "last_date")
    private Date lastDate;
}