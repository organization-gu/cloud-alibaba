package com.lanswon.uumfeign.domain.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


/**
 * 带实体消息的DTO
 *
 * @param <T> 实体消息
 *
 * @author jaswine
 */
@ToString
public class DataRtnDTO<T> extends AbstractDTO {


	public DataRtnDTO(){}

	public DataRtnDTO(int status, String msg, T data){
		this.status = status;
		this.msg = msg;
		this.data = data;
	}

	@Getter
	@Setter
	private T data;


}
