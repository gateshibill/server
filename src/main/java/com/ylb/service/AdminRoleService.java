package com.ylb.service;

import com.ylb.entity.AdminRole;

public interface AdminRoleService {
	/**
	 * 获取代理与角色关联信息
	 * @param adminId
	 * @return
	 */
	public AdminRole getAdminRoleByAdminId(Integer adminId);
	/**
	 * 设置角色
	 * @param ar
	 * @return
	 */
	public String doSetRole(AdminRole ar);
}
