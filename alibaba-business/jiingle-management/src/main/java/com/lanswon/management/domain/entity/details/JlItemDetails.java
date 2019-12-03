package com.lanswon.management.domain.entity.details;

import java.util.Date;
import javax.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Table(name = "jl_item_details")
public class JlItemDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 处理类别01/领用，02/消耗，03/进库
     */
    @Column(name = "handle_type")
    private String handleType;

    /**
     * 物品ID
     */
    @Column(name = "item_id")
    private Integer itemId;

    /**
     * 物品数量
     */
    @Column(name = "item_num")
    private Integer itemNum;

    /**
     * 物品单价
     */
    @Column(name = "item_price")
    private Long itemPrice;

    /**
     * 创建时间
     */
    @Column(name = "crt_date")
    private Date crtDate;

    /**
     * 上个月库存
     */
    @Column(name = "last_month_num")
    private Integer lastMonthNum;

    /**
     * 处理部门
     */
    @Column(name = "handle_dep")
    private Integer handleDep;

    /**
     * 处理人
     */
    @Column(name = "handele_user_name")
    private String handeleUserName;
}