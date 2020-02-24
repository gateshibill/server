package com.ylb.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.ylb.dao.UserManageOaRoleDao;
import com.ylb.entity.UserManageOaRole;
import com.ylb.service.UserManageOaRoleService;

@Service
public class UserManageOaRoleServiceImpl implements UserManageOaRoleService {
	@Resource
	private UserManageOaRoleDao userManageOaRoleDao;

	@Override
	public void addManageUser(UserManageOaRole userManageOaRole) {
		userManageOaRoleDao.addUserManageOaRole(userManageOaRole);

	}
	
	@Override
	public UserManageOaRole getManageUser(String username, Integer officialAccountId) {
		return userManageOaRoleDao.getUserManageOaRole(username, officialAccountId);
	}

	@Override
	public Integer delManageUser(String username, Integer officialAccountId) {
		return userManageOaRoleDao.delUserManageOaRole(username, officialAccountId);
	}

	@Override
	public Integer updateManageUser(UserManageOaRole userManageOaRole) {
		return userManageOaRoleDao.updateUserManageOaRole(userManageOaRole);
	}


}
