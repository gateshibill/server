package com.ylb.entity;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class Admin {
	private Integer adminId; // 主键id
	private String adminName;	//管理员真实姓名
	private String adminAccount;	//账号
	private String password;	//密码
	private String phone;	//联系方式
	private Integer isEffect;	//是否禁用
	private Integer parentId;	//父ID
	private Integer adminLevel;	//管理员等级
	private Date lastLoginTime;	//最后登录时间
	private Date createTime;	//创建时间
	private String adminCoding;//admin编号
	private Double serviceInto;//代理利息分成
	private Double handInto;//代理手续费分成
	private Set<Role> roles = new HashSet<>();
	public Integer getAdminId() {
		return adminId;
	}
	public void setAdminId(Integer adminId) {
		this.adminId = adminId;
	}
	public String getAdminName() {
		return adminName;
	}
	public void setAdminName(String adminName) {
		this.adminName = adminName;
	}
	public String getAdminAccount() {
		return adminAccount;
	}
	public void setAdminAccount(String adminAccount) {
		this.adminAccount = adminAccount;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public Integer getIsEffect() {
		return isEffect;
	}
	public void setIsEffect(Integer isEffect) {
		this.isEffect = isEffect;
	}
	public Integer getParentId() {
		return parentId;
	}
	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}
	public Integer getAdminLevel() {
		return adminLevel;
	}
	public void setAdminLevel(Integer adminLevel) {
		this.adminLevel = adminLevel;
	}
	public Date getLastLoginTime() {
		return lastLoginTime;
	}
	public void setLastLoginTime(Date lastLoginTime) {
		this.lastLoginTime = lastLoginTime;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getAdminCoding() {
		return adminCoding;
	}

	public void setAdminCoding(String adminCoding) {
		this.adminCoding = adminCoding;
	}

	@Override
	public String toString() {
		return "Admin [adminId=" + adminId + ", adminName=" + adminName + ", adminAccount=" + adminAccount
				+ ", password=" + password + ", phone=" + phone + ", isEffect=" + isEffect + ", parentId=" + parentId
				+ ", adminLevel=" + adminLevel + ", lastLoginTime=" + lastLoginTime + ", createTime=" + createTime
				+ ",adminCoding=" + adminCoding + ", getAdminId()=" + getAdminId() + ", getAdminName()=" + getAdminName() + ", getAdminAccount()="
				+ getAdminAccount() + ", getPassword()=" + getPassword() + ", getPhone()=" + getPhone()
				+ ", getIsEffect()=" + getIsEffect() + ", getParentId()=" + getParentId() + ", getAdminLevel()="
				+ getAdminLevel() + ", getLastLoginTime()=" + getLastLoginTime() + ", getCreateTime()="
				+ getCreateTime() + ",getAdminCoding()=" + getAdminCoding() + "]";
	}
	public Set<Role> getRoles() {
		return roles;
	}
	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}
	public Double getServiceInto() {
		return serviceInto;
	}
	public Double getHandInto() {
		return handInto;
	}
	public void setServiceInto(Double serviceInto) {
		this.serviceInto = serviceInto;
	}
	public void setHandInto(Double handInto) {
		this.handInto = handInto;
	}
}
