package com.lanswon.management.domain.entity;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Table(name = "jl_item_type")
@ApiModel(value = "物资类别信息")
public class JlItemType {
    /**
     * 物资类别代码1/一次性，2/布件易耗品，3/酒水类
     */
    @Id
    @Column(name = "type_code")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer typeCode;

    /**
     * 物资类别名
     */
    @Column(name = "type_name")
    @NotBlank(message = "物资类别名不能为空")
    private String typeName;
}