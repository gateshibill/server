package com.ylb.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.ylb.entity.VerifyCode;

import java.util.Date;

@Mapper
public interface VerifyCodeDao {
	Integer insertCode(VerifyCode code);

	VerifyCode selectOne(@Param("code")VerifyCode code);

	Integer updateCode(VerifyCode code);

	String selectTwo(@Param("sendPhone") String sendPhone);

	Date selectThree(@Param("sendPhone") String sendPhone);
}
