package com.lanswon.uumfeign.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Set;

/**
 * 安全权限的通用User对象
 *
 * @author jaswine
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthUser{

	/** 用户名 */
	private String username;
	/** 密码 */
	private String password;
	/** 昵称 */
	private String nickname;
	/** 账户是否可用 */
	private boolean accountEnable;
	/** 账户是否过期 */
	private boolean accountExpired;
	/** 证书是否过期 */
	private boolean credentialExpired;
	/** 账户是否锁定 */
	private boolean accountLock;
	/** 角色 */
	private List<AuthRole> roles;
	/** url */
	private Set<AuthUrl> urls;



}
