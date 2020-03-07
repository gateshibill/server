package com.ylb.entity;

import java.sql.Timestamp;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class User {
	String username;// 主键
	String password = "";
	String jid = "";// account@domain
	String name = "";
	String nickname;
	String groupNickname; //群昵称
	String avatar = "";
	String phone = "";
	String email = "";
	String organization = "";
	// String status = "";
	String signation = "";
	int sex;
	int type;// 0一般般，1未朋友，2为有联系陌生人;4
	// String vcard = "";

	String remark;
	String encrypted;
	String salt;
	String storedKey;
	String serverKey;
	String token = "";
	Date createTime;
	//Date datetime;


	public void setCreateTime(Long createTime) {
		this.createTime = new Date(createTime);
	}

	String role;
	String roleId;// 角色id
	int status;// 状态

	public String toString() {
		return username + "|jid:" + jid + "|name:" + name + "|nickname:" + nickname + "|phone:" + phone;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getJid() {
		return jid;
	}

	public void setJid(String jid) {
		this.jid = jid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getOrganization() {
		return organization;
	}

	public void setOrganization(String organization) {
		this.organization = organization;
	}

	public String getSignation() {
		return signation;
	}

	public void setSignation(String signation) {
		this.signation = signation;
	}

	public int getSex() {
		return sex;
	}

	public void setSex(int sex) {
		this.sex = sex;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getEncrypted() {
		return encrypted;
	}

	public void setEncrypted(String encrypted) {
		this.encrypted = encrypted;
	}

	public String getSalt() {
		return salt;
	}

	public void setSalt(String salt) {
		this.salt = salt;
	}

	public String getStoredKey() {
		return storedKey;
	}

	public void setStoredKey(String storedKey) {
		this.storedKey = storedKey;
	}

	public String getServerKey() {
		return serverKey;
	}

	public void setServerKey(String serverKey) {
		this.serverKey = serverKey;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}



	public String getGroupNickname() {
		return groupNickname;
	}

	public void setGroupNickname(String groupNickname) {
		this.groupNickname = groupNickname;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public String getRoleId() {
		return roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

}
