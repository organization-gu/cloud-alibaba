package com.lanswon.management.domain.entity;

import java.util.Date;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.Range;

@Getter
@Setter
@ToString
@Table(name = "jl_item")
@ApiModel(value = "物资信息")
public class JlItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 物资类别
     */
    @Column(name = "item_type_id")
    @Range(min = 1,message = "物资类别不能为空")
    @ApiModelProperty(value = "物资类别ID")
    private Integer itemTypeId;

    /**
     * 物资名
     */
    @NotBlank(message = "物资名不能为空")
    @ApiModelProperty(value = "物资名")
    private String name;

    /**
     * 创建时间
     */
    @Column(name = "crt_date")
    @ApiModelProperty(hidden = true)
    private Date crtDate;

    /**
     * 最后修改时间
     */
    @Column(name = "last_date")
    @ApiModelProperty(hidden = true)
    private Date lastDate;
}