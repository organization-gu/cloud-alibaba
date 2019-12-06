package com.lanswon.management.domain.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

/** 统一返回对象
 * @Description:
 * @Author GU-YW
 * @Date 2019/12/3 16:50
 */
@Slf4j
@Data
@Builder
@ApiModel(value = "统一返回对象" )
public class SimpleResponse {

    @ApiModelProperty(value = "返回状态码")
    private int status;

    @ApiModelProperty(value = "返回描述信息")
    private String description;

    @ApiModelProperty(value = "返回数据")
    private Object object;
}
