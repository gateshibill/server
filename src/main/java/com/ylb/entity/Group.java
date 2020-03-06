package com.ylb.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Group {
	  String jid; //群jid
	  String name;// 群id
	  String subject = ""; //群名称
	  String naturalName = ""; //群头像
	  String userNickname; //用户在群昵称
	  int roomID;
	  String creator;
	  int membersOnly; //是否需要审核,0为不需要，1为需要
	  int manageCount;
	  int memberCount;
	  Date createTime;
	  Bulletin bulletin;//群公告
	  String status;
	  String level;
	  int officialAccountId;
	  int maxUsers;
	  
	  public int getMaxUsers() {
		return maxUsers;
	}

	public void setMaxUsers(int maxUsers) {
		this.maxUsers = maxUsers;
	}

	List<User> admins = new ArrayList<User>(); //管理员列表
	  List<User> applicants = new ArrayList<User>();
	  List<User> owners = new ArrayList<User>(); //群主

	  List<User> members = new ArrayList<User>(); //成员
	  List<User> strangers = new ArrayList<User>(); //保存本地已经退出群聊人员

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

	public String getJid() {
		return jid;
	}

	public void setJid(String jid) {
		this.jid = jid;
	}

	public String getUserNickname() {
		return userNickname;
	}

	public void setUserNickname(String userNickname) {
		this.userNickname = userNickname;
	}

	public String getCreator() {
		return creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}

	public List<User> getAdmins() {
		return admins;
	}

	public void setAdmins(List<User> admins) {
		this.admins = admins;
	}

	public List<User> getApplicants() {
		return applicants;
	}

	public void setApplicants(List<User> applicants) {
		this.applicants = applicants;
	}

	public List<User> getOwners() {
		return owners;
	}

	public void setOwners(List<User> owners) {
		this.owners = owners;
	}

	public List<User> getMembers() {
		return members;
	}

	public void setMembers(List<User> members) {
		this.members = members;
	}

	public List<User> getStrangers() {
		return strangers;
	}

	public void setStrangers(List<User> strangers) {
		this.strangers = strangers;
	}


	  
}
