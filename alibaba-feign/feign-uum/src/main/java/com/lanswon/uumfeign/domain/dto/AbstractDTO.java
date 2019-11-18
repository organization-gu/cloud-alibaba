package com.lanswon.uumfeign.domain.dto;



/**
 * 抽象DTO
 * @author jaswine
 */
public abstract class AbstractDTO implements DTO {

	/** 状态码*/
	public int status;
	/** 返回信息 */
	public String msg;

	public  AbstractDTO(){}

	public AbstractDTO(int status,String msg){
		this.status = status;
		this.msg = msg;
	}


	@Override
	public void setStatus(int status) {
		this.status = status;
	}

	@Override
	public int getStatus() {
		return this.status;
	}

	@Override
	public void setMsg(String msg) {
		this.msg = msg;
	}

	@Override
	public String getMsg() {
		return this.msg;
	}

}


