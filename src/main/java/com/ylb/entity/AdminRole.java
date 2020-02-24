package com.ylb.entity;

public class AdminRole {
	private Integer adminId ;//管理员id
	private Integer roleId;//角色id
	public Integer getAdminId() {
		return adminId;
	}
	public Integer getRoleId() {
		return roleId;
	}
	public void setAdminId(Integer adminId) {
		this.adminId = adminId;
	}
	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}
	@Override
	public String toString() {
		return "AdminRole [adminId=" + adminId + ", roleId=" + roleId + ", getAdminId()=" + getAdminId()
				+ ", getRoleId()=" + getRoleId() + "]";
	}
}
