package com.ylb.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.ylb.entity.RolePower;
@Mapper
public interface RolePowerDao {
	/**
	 * 批量插入
	 * @param lists
	 */
	void addRolePower(@Param("lists")List<RolePower> lists);
	/**
	 * 获取某个角色的权限
	 * @param roleId
	 * @return
	 */
	List<RolePower> getPowerListByRoleId(@Param("roleId")Integer roleId);
	
	/**
	 * 删除某角色的权限
	 * @param roleId
	 * @return
	 */
	Integer delAllPower(@Param("roleId")Integer roleId);
}
