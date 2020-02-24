package com.ylb.service;

import java.util.List;
import java.util.Map;

import com.github.pagehelper.Page;
import com.ylb.entity.Role;

/**
 * 角色
 * @author Administrator
 *
 */
public interface RoleService {
	/**
	 * 新增角色
	 * @param role
	 * @return
	 */
	public String addRole(Role role,String rolePower);
	/**
	 * 删除角色
	 * @param roleId
	 * @return
	 */
	public String delRole(Integer roleId);
	/**
	 * 获取角色列表
	 * @param role
	 * @param page
	 * @param limit
	 * @return
	 */
	public Page<Role> getRoleList(Map<String,Object> param);
	
	/**
	 * 获取所有角色
	 * @return
	 */
	public List<Role> getAllRole();
	/**
	 * 更新角色
	 * @param role
	 * @return
	 */
	public String updateRole(Role role,String rolePower);
	/**
	 * 获取角色详情
	 * @param roleId
	 * @return
	 */
	public Role getRoleById(Integer roleId);
	
	Role getRoleByOfficailAcountAndUsername(String officialAccountId,String username);
}
