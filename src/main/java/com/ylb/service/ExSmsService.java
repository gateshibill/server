package com.ylb.service;

import org.apache.ibatis.annotations.Param;

import com.ylb.entity.ExSms;

public interface ExSmsService {
	public ExSms getExSmsByPhone(@Param("phone") String phone);

	public void addExSms(ExSms userModel);

	public void updateExSms(ExSms userModel);
	
}
