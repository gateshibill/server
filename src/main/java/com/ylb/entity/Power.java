package com.ylb.entity;

import java.util.Date;
import java.util.List;
import java.util.Set;

public class Power {
	private Integer powerId;
	private String powerName;
	private String powerUrl;
	private Integer parentId;
	private Integer isHasPower = 0; //是否拥有权限
	private Date createTime;
	private List<Power> children;
	private Set<Role> roles;
	public Integer getPowerId() {
		return powerId;
	}
	public void setPowerId(Integer powerId) {
		this.powerId = powerId;
	}
	public String getPowerName() {
		return powerName;
	}
	public void setPowerName(String powerName) {
		this.powerName = powerName;
	}
	public String getPowerUrl() {
		return powerUrl;
	}
	public void setPowerUrl(String powerUrl) {
		this.powerUrl = powerUrl;
	}
	public Integer getParentId() {
		return parentId;
	}
	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Set<Role> getRoles() {
		return roles;
	}
	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}
	public List<Power> getChildren() {
		return children;
	}
	public void setChildren(List<Power> children) {
		this.children = children;
	}
	public Integer getIsHasPower() {
		return isHasPower;
	}
	public void setIsHasPower(Integer isHasPower) {
		this.isHasPower = isHasPower;
	}
	@Override
	public String toString() {
		return "Power [powerId=" + powerId + ", powerName=" + powerName + ", powerUrl=" + powerUrl + ", parentId="
				+ parentId + ", isHasPower=" + isHasPower + ", createTime=" + createTime + ", children=" + children
				+ ", roles=" + roles + ", getPowerId()=" + getPowerId() + ", getPowerName()=" + getPowerName()
				+ ", getPowerEnName()=" + getPowerUrl() + ", getParentId()=" + getParentId() + ", getCreateTime()="
				+ getCreateTime() + ", getRoles()=" + getRoles() + ", getChildren()=" + getChildren()
				+ ", getIsHasPower()=" + getIsHasPower() + "]";
	}
}
