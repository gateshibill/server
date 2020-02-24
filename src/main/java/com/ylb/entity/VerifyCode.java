package com.ylb.entity;

import java.util.Date;

public class VerifyCode {
	private Integer id;
	private String verifyCode;
	private Date sendTime;
	private String sendPhone;
	private Integer  sendType;
	private Integer isUse;
	private Date useTime;
	public Integer getId() {
		return id;
	}
	public String getVerifyCode() {
		return verifyCode;
	}
	public Date getSendTime() {
		return sendTime;
	}
	public String getSendPhone() {
		return sendPhone;
	}
	public Integer getSendType() {
		return sendType;
	}
	public Integer getIsUse() {
		return isUse;
	}
	public Date getUseTime() {
		return useTime;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public void setVerifyCode(String verifyCode) {
		this.verifyCode = verifyCode;
	}
	public void setSendTime(Date sendTime) {
		this.sendTime = sendTime;
	}
	public void setSendPhone(String sendPhone) {
		this.sendPhone = sendPhone;
	}
	public void setSendType(Integer sendType) {
		this.sendType = sendType;
	}
	public void setIsUse(Integer isUse) {
		this.isUse = isUse;
	}
	public void setUseTime(Date useTime) {
		this.useTime = useTime;
	}
	@Override
	public String toString() {
		return "VerifyCode [id=" + id + ", verifyCode=" + verifyCode + ", sendTime=" + sendTime + ", sendPhone="
				+ sendPhone + ", sendType=" + sendType + ", isUse=" + isUse + ", useTime=" + useTime + ", getId()="
				+ getId() + ", getVerifyCode()=" + getVerifyCode() + ", getSendTime()=" + getSendTime()
				+ ", getSendPhone()=" + getSendPhone() + ", getSendType()=" + getSendType() + ", getIsUse()="
				+ getIsUse() + ", getUseTime()=" + getUseTime() + "]";
	}
	
}
