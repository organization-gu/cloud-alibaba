package com.lanswon.management.domain.entity.type;

import javax.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Table(name = "jl_item_type")
public class JlItemType {
    /**
     * 物资类别代码1/一次性，2/布件易耗品，3/酒水类
     */
    @Id
    @Column(name = "type_code")
    private Integer typeCode;

    /**
     * 物资类别名
     */
    @Column(name = "type_name")
    private String typeName;
}