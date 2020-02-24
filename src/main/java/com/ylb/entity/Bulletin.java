package com.ylb.entity;

import java.util.Date;

public class Bulletin {
	  String id;
	  String content;
	  String groupJid;
	  Date createTime;
	  String status;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getGroupJid() {
		return groupJid;
	}
	public void setGroupJid(String groupJid) {
		this.groupJid = groupJid;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}	
	  
}
