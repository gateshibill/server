package com.ylb.dao;

import org.apache.ibatis.annotations.Mapper;

import com.ylb.entity.UserManageOaRole;

@Mapper
public interface UserManageOaRoleDao {

	void addUserManageOaRole(UserManageOaRole userManageOaRole);
	
	UserManageOaRole getUserManageOaRole(String username, Integer officialAccountId);

	Integer delUserManageOaRole(String username, Integer officialAccountId);
	
	Integer updateUserManageOaRole(UserManageOaRole userManageOaRole);

}
