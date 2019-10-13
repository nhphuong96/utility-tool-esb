package com.nhphuong.utilitytool.esb.dto;

import org.springframework.security.core.GrantedAuthority;

public class RoleDTO extends BaseDTO implements GrantedAuthority {
	/**
	 * 
	 */
	private static final long serialVersionUID = 6388288667373337138L;
	private Long roleKey;
	private String roleId;

	public RoleDTO() {
		super();
	}

	public Long getRoleKey() {
		return roleKey;
	}

	public void setRoleKey(Long roleKey) {
		this.roleKey = roleKey;
	}

	public String getRoleId() {
		return roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

	@Override
	public String getAuthority() {
		return roleId;
	}

}
