package com.ylb.entity;

import java.util.Date;

public class Message {
	Integer messageId;//消息ID
	//String sender;//
	String username;// 用户者
	String name;// 发送者利宝号
	String nickname;// 用户名
	String fromId;// 用户ID
	String groupId;// 群id
	String groupName;// 群名
	String content;//消息内容
	String clientOs;//发送者操作系统
	int type = 0; // 0为文字，1为图片,2为名片，3声音
	int category; // 0个人，1,群聊，2为系统推送，3为公众号推送
	String extra; // 可用作语音时长
	int status;
	int remove;
	Date dateTime;//消息发送时间

	
	public Integer getMessageId() {
		return messageId;
	}
	public void setMessageId(Integer messageId) {
		this.messageId = messageId;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
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
	public String getFromId() {
		return fromId;
	}
	public void setFromId(String fromId) {
		this.fromId = fromId;
	}
	public String getGroupId() {
		return groupId;
	}
	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}
	public String getGroupName() {
		return groupName;
	}
	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getClientOs() {
		return clientOs;
	}
	public void setClientOs(String clientOs) {
		this.clientOs = clientOs;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public int getCategory() {
		return category;
	}
	public void setCategory(int category) {
		this.category = category;
	}
	public String getExtra() {
		return extra;
	}
	public void setExtra(String extra) {
		this.extra = extra;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public Date getDateTime() {
		return dateTime;
	}
	public void setDateTime(long dateTime) {
		this.dateTime = new Date(dateTime);
	}
	public int getRemove() {
		return remove;
	}
	public void setRemove(int remove) {
		this.remove = remove;
	}
	
}
