package com.lanswon.uumfeign.domain.dto;

/**
 * 数据返回 对象
 *
 * @author jaswine
 */
public interface DTO {

	/**
	 * 设置状态码
	 * @param status 状态码
	 */
	void setStatus(int status);

	/**
	 * 获得状态码
	 * @return 状态码
	 */
	int getStatus();

	/**
	 * 设置返回信息
	 * @param msg  返回信息
	 */
	void setMsg(String msg);

	/**
	 * 获得返回信息
	 * @return 返回信息
	 */
	String getMsg();
}
