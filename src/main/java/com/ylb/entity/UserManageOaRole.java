package com.ylb.entity;

import java.util.Date;

public class UserManageOaRole {
	//int id;
	String username;
	Integer officialAccountId;
	Integer roleId;
	Integer status;//有效性，0为可用，1为禁用
	Date createTime;
	Date updateTime;

//	public Integer getId() {
//		return id;
//	}
//
//	public void setId(int id) {
//		this.id = id;
//	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public Integer getOfficialAccountId() {
		return officialAccountId;
	}

	public void setOfficialAccountId(int officialAccountId) {
		this.officialAccountId = officialAccountId;
	}
	public Integer getRoleId() {
		return roleId;
	}

	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

}
