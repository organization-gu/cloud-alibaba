package com.lanswon.management.domain.entity.item;

import java.util.Date;
import javax.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Table(name = "jl_item")
public class JlItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 物资类别
     */
    @Column(name = "item_type_id")
    private Integer itemTypeId;

    /**
     * 物资名
     */
    private String name;

    /**
     * 物资单价
     */
    private Long price;

    /**
     * 物资实际数量
     */
    private Integer num;

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