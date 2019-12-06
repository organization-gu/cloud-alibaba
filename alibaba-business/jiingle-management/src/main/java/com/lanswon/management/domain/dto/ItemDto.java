package com.lanswon.management.domain.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Description:
 * @Author GU-YW
 * @Date 2019/12/3 17:24
 */
@Data
@ApiModel(value = "查询物资信息参数" ,description = "查询物资信息参数")
public class ItemDto extends BasePageDto{

    @ApiModelProperty(value = "物资类型ID")
    private int itemTypeId;



}
