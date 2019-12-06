package com.lanswon.management.domain.vo;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/** 物资Vo
 * @Description:
 * @Author GU-YW
 * @Date 2019/12/3 17:19
 */
@Data
@Builder
public class ItemVo {

    /**
     * 物资ID
     */
    private Integer id;

    /**
     * 物资类别
     */
    private Integer itemTypeId;

    /**
     * 物资类别名
     */
    private String itemTypeName;

    /**
     * 物资名
     */
    private String itemName;

    /**
     * 物资单价
     */
    private BigDecimal price;

    /**
     * 物资实际数量
     */
    private Integer num;

    /**
     * 创建时间
     */
    private Date crtDate;

    /**
     * 最后修改时间
     */
    private Date lastDate;
}