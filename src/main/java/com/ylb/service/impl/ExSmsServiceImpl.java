package com.ylb.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.ylb.dao.ExSmsDao;
import com.ylb.entity.ExSms;
import com.ylb.service.ExSmsService;

@Service
public class ExSmsServiceImpl implements ExSmsService {
	@Resource
	private ExSmsDao exSmsDao;

	@Override
	public ExSms getExSmsByPhone(String code) {
		return exSmsDao.getExSmsByCode(code);
	}

	@Override
	public void addExSms(ExSms exSms) {
		exSmsDao.addExSms(exSms);
	}

	@Override
	public void updateExSms(ExSms exSms) {
		exSmsDao.updateExSms(exSms);

	}

}
