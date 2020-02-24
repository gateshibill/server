package com.ylb.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ylb.dao.PowerDao;
import com.ylb.entity.Power;
import com.ylb.service.PowerService;
@Service
public class PowerServiceImpl implements PowerService{
	@Autowired
	private PowerDao powerDao;
	@Override
	public List<Power> getPowerList() {
		return powerDao.getPowerList(null, null, null);
	}

}
