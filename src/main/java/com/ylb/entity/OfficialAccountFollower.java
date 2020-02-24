package com.ylb.entity;

import java.util.Date;

public class OfficialAccountFollower {
	int id;
	Integer officialAccountId;
	String userId;
	int status=0;//1为关注，0为取消
	Date createTime;

	public  OfficialAccountFollower(Integer officialAccountId, String userId,int status) {
		this.officialAccountId = officialAccountId;
		this.userId = userId;
		this.status=status;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Integer getOfficialAccountId() {
		return officialAccountId;
	}

	public void setOfficialAccountId(Integer officialAccountId) {
		this.officialAccountId = officialAccountId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	
}
