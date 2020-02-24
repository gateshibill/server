package com.ylb.entity;

import java.util.Date;

public class Group {
	  String name;// 群id
	  String subject = ""; //群名称
	  String naturalName = ""; //群头像
	  int roomID;
	//  String creator;
	  int membersOnly; //是否需要审核,0为不需要，1为需要
	  int manageCount;
	  int memberCount;
	  Date createTime;
	  Bulletin bulletin;//群公告
	  
	  int officialAccountId;//推送消息用

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getNaturalName() {
		return naturalName;
	}

	public void setNaturalName(String naturalName) {
		this.naturalName = naturalName;
	}

	public int getRoomID() {
		return roomID;
	}

	public void setRoomID(int roomID) {
		this.roomID = roomID;
	}

	public int getMembersOnly() {
		return membersOnly;
	}

	public void setMembersOnly(int membersOnly) {
		this.membersOnly = membersOnly;
	}

	public int getManageCount() {
		return manageCount;
	}

	public void setManageCount(int manageCount) {
		this.manageCount = manageCount;
	}

	public int getMemberCount() {
		return memberCount;
	}

	public void setMemberCount(int memberCount) {
		this.memberCount = memberCount;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(long createTime) {
		this.createTime = new Date(createTime);
	}

	public Bulletin getBulletin() {
		return bulletin;
	}

	public void setBulletin(Bulletin bulletin) {
		this.bulletin = bulletin;
	}

	public int getOfficialAccountId() {
		return officialAccountId;
	}

	public void setOfficialAccountId(int officialAccountId) {
		this.officialAccountId = officialAccountId;
	}
	  

	  
}
