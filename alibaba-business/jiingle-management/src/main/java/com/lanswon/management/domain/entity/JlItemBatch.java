package com.lanswon.management.domain.entity;

import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.*;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Table(name = "jl_item_batch")
@ApiModel(value = "物资进库批次信息")
public class JlItemBatch {
    /**
     * 物资进库批次
     */
    @Id
    @Column(name = "batch_id")
    private Long batchId;

    /**
     * 物资ID
     */
    @Column(name = "item_id")
    private Integer itemId;

    /**
     * 进货数量
     */
    @Column(name = "item_num")
    private Integer itemNum;

    /**
     * 批次单价
     */
    @Column(name = "item_price")
    private BigDecimal itemPrice;

    /**
     * 批次成本
     */
    @Column(name = "item_cost")
    private BigDecimal itemCost;

    /**
     * 进库人
     */
    @Column(name = "batch_into_name")
    private String batchIntoName;

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

    /**
     * 批次状态1（已用完)，0（未用完）
     */
    @Column(name = "use_status")
    private Integer useStatus;
}