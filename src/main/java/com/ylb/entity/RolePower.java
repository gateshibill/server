package com.ylb.entity;

public class RolePower {
	private Integer roleId;
	private Integer powerId;
	public Integer getRoleId() {
		return roleId;
	}
	public Integer getPowerId() {
		return powerId;
	}
	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}
	public void setPowerId(Integer powerId) {
		this.powerId = powerId;
	}
	@Override
	public String toString() {
		return "RolePower [roleId=" + roleId + ", powerId=" + powerId + ", getRoleId()=" + getRoleId()
				+ ", getPowerId()=" + getPowerId() + "]";
	}
	
}
