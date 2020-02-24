package com.ylb.service;

import com.ylb.entity.VerifyCode;

public interface VerifyCodeService {
	public String sendCode(VerifyCode code);
	public Integer checkVerifyCode(VerifyCode code);
}	
