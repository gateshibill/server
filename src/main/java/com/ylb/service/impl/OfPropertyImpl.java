package com.ylb.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ylb.dao.OfPropertyDao;
import com.ylb.entity.OfProperty;
import com.ylb.service.OfPropertyService;

@Service
public class OfPropertyImpl implements OfPropertyService {
	@Autowired
	private OfPropertyDao ofPropertyDao;

	@Override
	public OfProperty getOfProperty(String name) {
		return ofPropertyDao.getOfProperty(name);
	}
}
