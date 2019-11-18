package com.lanswon.uumfeign.domain.dto;

import lombok.ToString;


/**
 * 返回结果DTO对象
 * @author jaswine
 */
@ToString
public class SimpleRtnDTO extends AbstractDTO {

	public SimpleRtnDTO(){}

	public SimpleRtnDTO(int code, String msg){
		super(code,msg);
	}


}
