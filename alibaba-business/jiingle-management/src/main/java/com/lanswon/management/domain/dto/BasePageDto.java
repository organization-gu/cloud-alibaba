package com.lanswon.management.domain.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Description: 分页参数
 * @Author GU-YW
 * @Date 2019/12/4 9:32
 */
@ApiModel(value = "分页参数")
public class BasePageDto {

    @ApiModelProperty(value = "分页参数-每页个数" )
    private int limit = 10;

    @ApiModelProperty(value = "分页参数-当前页数")
    private int page = 1;

    @ApiModelProperty(hidden = true)
    private int startLine= 0;

    public int getLimit() {
        if(limit == 0){
            return 10;
        }
        return limit;
    }

    public int getPage() {
        if(page == 0){
            return 1;
        }
        return page;
    }

    public int getStartLine() {
        return startLine;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public void setStartLine(int startLine) {
        this.startLine = (getPage()-1)*getLimit();
    }
}
