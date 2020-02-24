package com.ylb.service;

import com.ylb.entity.UserManageOaRole;

public interface UserManageOaRoleService {

	void addManageUser(UserManageOaRole userManageOaRole);

	UserManageOaRole getManageUser(String username, Integer officialAccountId);
	
	Integer delManageUser(String username, Integer officialAccountId);
	
	Integer updateManageUser(UserManageOaRole userManageOaRole);
}
