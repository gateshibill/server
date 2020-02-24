package com.ylb.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ylb.dao.RolePowerDao;
import com.ylb.entity.RolePower;
import com.ylb.service.RolePowerService;

@Service
public class RolePowerServiceImpl implements RolePowerService {
	@Autowired
	private RolePowerDao rolePowerDao;

	@Override
	public List<RolePower> getPowerListByRoleId(Integer roleId) {
		return rolePowerDao.getPowerListByRoleId(roleId);
	}

	@Override
	public void addRolePower(List<RolePower> lists) {
		rolePowerDao.addRolePower(lists);
	}

	@Override
	public Integer delAllPower(Integer roleId) {
		return rolePowerDao.delAllPower(roleId);
	}

}
