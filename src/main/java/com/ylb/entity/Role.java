package com.ylb.entity;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class Role {
	private Integer officialAccountId;
	private Integer roleId;
	private String roleName;
	private Integer isEffect;
	private Date createTime;
	// private Set<Admin> admins = new HashSet<>();
	private Set<Power> powers = new HashSet<>();

	public String detail() {
		return String.format("roleName:%s|officialAccountId:%d|roleId:%d|isEffect:%d|", roleName, officialAccountId,
				roleId, isEffect);
	}

	public Integer getRoleId() {
		return roleId;
	}

	public String getRoleName() {
		return roleName;
	}

	public Integer getIsEffect() {
		return isEffect;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public void setIsEffect(Integer isEffect) {
		this.isEffect = isEffect;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

//	public Set<Admin> getAdmins() {
//		return admins;
//	}
//	public void setAdmins(Set<Admin> admins) {
//		this.admins = admins;
//	}
	public Set<Power> getPowers() {
		return powers;
	}

	public void setPowers(Set<Power> powers) {
		this.powers = powers;
	}

	public Integer getOfficialAccountId() {
		return officialAccountId;
	}

	public void setOfficialAccountId(Integer officialAccountId) {
		this.officialAccountId = officialAccountId;
	}

	@Override
	public String toString() {
		return "Role [roleId=" + roleId + ", roleName=" + roleName + ", isEffect=" + isEffect + ", createTime="
				+ createTime + ", getRoleId()=" + getRoleId() + ", getRoleName()=" + getRoleName() + ", getIsEffect()="
				+ getIsEffect() + ", getCreateTime()=" + getCreateTime() + "]";
	}

}
