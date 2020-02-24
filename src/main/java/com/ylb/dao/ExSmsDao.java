package com.ylb.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.ylb.entity.ExSms;

@Mapper
public interface ExSmsDao {

	ExSms getExSmsByCode(@Param("code") String code);

	void addExSms(ExSms sms);

	void updateExSms(ExSms sms);
}
