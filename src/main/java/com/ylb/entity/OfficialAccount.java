package com.ylb.entity;

import java.util.Date;

public class OfficialAccount {
	int id;
	String username;//公众号所属用户名
	String name;//公众号用户名
	String header;//公众号头像
	String phone;//电话号码
	String email;
	String poster;//公众号海报
	String organization;//公司
	String description;//公众号描述
	String address;//地址
	String website;//网址
	int level;//级别
	int type;//类型
	int status;//状态
	int followerCount;//粉丝数
	String subscribeServiceId;//公众号对应推送服务id
	Date createTime;//申请时间
	Date updateTime;
	
    OfficialAccountLevel officialAccountLevel;

	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getWebsite() {
		return website;
	}
	public void setWebsite(String website) {
		this.website = website;
	}
	public int getFollowerCount() {
		return followerCount;
	}
	public void setFollowerCount(int followerCount) {
		this.followerCount = followerCount;
	}
	public String getSubscribeServiceId() {
		return subscribeServiceId;
	}
	public void setSubscribeServiceId(String subscribeServiceId) {
		this.subscribeServiceId = subscribeServiceId;
	}

	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getHeader() {
		return header;
	}
	public void setHeader(String header) {
		this.header = header;
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
	public int getLevel() {
		return level;
	}
	public void setLevel(int level) {
		this.level = level;
	}
	public String getPoster() {
		return poster;
	}
	public void setPoster(String poster) {
		this.poster = poster;
	}
	public String getOrganization() {
		return organization;
	}
	public void setOrganization(String organization) {
		this.organization = organization;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public OfficialAccountLevel getOfficialAccountLevel() {
		return officialAccountLevel;
	}
	public void setOfficialAccountLevel(OfficialAccountLevel officialAccountLevel) {
		this.officialAccountLevel = officialAccountLevel;
	}

	
}
