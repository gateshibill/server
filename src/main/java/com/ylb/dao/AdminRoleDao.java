package com.ylb.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.ylb.entity.AdminRole;

@Mapper
public interface AdminRoleDao {
	AdminRole getAdminRoleByAdminId(@Param("adminId")Integer adminId);
	Integer delAdminRole(@Param("adminId")Integer adminId);
	Integer addAdminRole(AdminRole adminRole);
	Integer updateAdminRole(AdminRole adminRole);
}
