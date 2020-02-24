package com.ylb.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.github.pagehelper.Page;
import com.ylb.entity.Role;

@Mapper
public interface RoleDao {
	/**
	 * 添加角色
	 * @param role
	 * @return
	 */
	Integer addRole(Role role);
	/**
	 * 删除角色
	 * @param roleId
	 * @return
	 */
	Integer delRole(Integer roleId);
	/**
	 * 获取角色总数
	 * @param role
	 * @return
	 */
	int getRoleCount(@Param("role")Role role);
	/**
	 * 获取角色列表
	 * @param role
	 * @param page
	 * @param limit
	 * @return
	 */
	Page<Role> getRoleList(Map<String,Object> param);
	/**
	 * 获取所有角色
	 * @return
	 */
	List<Role> getAllRole();
	/**
	 * 更新角色
	 * @param role
	 * @return
	 */
	Integer updateRole(Role role);
	/**
	 * 获取角色详情
	 * @param roleId
	 * @return
	 */
	Role getRoleById(@Param("roleId")Integer roleId);
	/**
	 * 查询该角色名称是否存在
	 * @param roleName
	 * @param roleId
	 * @return
	 */
	int checkRoleIsAlready(@Param("roleName")String roleName,@Param("roleId")Integer roleId);
	
	Role getRoleByOfficailAcountAndUsername(String officialAccountId,String username);
}
