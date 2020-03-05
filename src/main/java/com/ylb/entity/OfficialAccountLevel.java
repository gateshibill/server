package com.ylb.entity;

public class OfficialAccountLevel {

	private int id = 1;
	private String name = "silver";
	private String ensureMoney = "100";
	private int groupNums = 20;
	private int oneGroupNums = 100;
	private int adminNums = 10;
	private int messageNums = 100000;
	private String icon = "";
	private int isOpen = 1;

	
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

	public String getEnsureMoney() {
		return ensureMoney;
	}

	public void setEnsureMoney(String ensureMoney) {
		this.ensureMoney = ensureMoney;
	}

	public int getGroupNums() {
		return groupNums;
	}

	public void setGroupNums(int groupNums) {
		this.groupNums = groupNums;
	}

	public int getOneGroupNums() {
		return oneGroupNums;
	}

	public void setOneGroupNums(int oneGroupNums) {
		this.oneGroupNums = oneGroupNums;
	}

	public int getAdminNums() {
		return adminNums;
	}

	public void setAdminNums(int adminNums) {
		this.adminNums = adminNums;
	}

	public int getMessageNums() {
		return messageNums;
	}

	public void setMessageNums(int messageNums) {
		this.messageNums = messageNums;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public int getIsOpen() {
		return isOpen;
	}

	public void setIsOpen(int isOpen) {
		this.isOpen = isOpen;
	}

}
