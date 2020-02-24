package com.ylb.service.impl;

import com.ylb.dao.VerifyCodeDao;
import com.ylb.entity.VerifyCode;
import com.ylb.service.VerifyCodeService;
import com.ylb.util.JsonUtil;
import com.ylb.util.SendUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Random;

@Service
public class VerifyCodeServiceImpl implements VerifyCodeService{
	Logger logger = LoggerFactory.getLogger(VerifyCodeServiceImpl.class);
	@Autowired
	private VerifyCodeDao verifyCodeDao;
	@Override
	public String sendCode(VerifyCode code) {
		String str = "";
		if(code.getSendPhone().equals("") || code.getSendPhone() == null) {
			str = JsonUtil.buildObjectJson(1, "手机号码不能为空", "");
		}else {
			if(code.getSendType() == null) {
				str = JsonUtil.buildObjectJson(1, "无法识别验证码类型", "");
			}else {
				code.setIsUse(0);
				VerifyCode newcode = verifyCodeDao.selectOne(code);
				if(newcode== null) { //第一次发送验证码
					logger.info("第一次发送..."+code.getSendPhone());
					String sendcode = String.valueOf(new Random().nextInt(899999) + 100000);
					String resultcode = SendUtil.sendCode(code.getSendPhone(), sendcode);
					if(resultcode.equals("0")) {//发送成功
						//插入数据库
						code.setSendTime(new Date());
						code.setIsUse(0);
						code.setSendTime(new Date());
						code.setVerifyCode(sendcode);
						Integer result = verifyCodeDao.insertCode(code);
						if(result != null) { //插入成功
							str = JsonUtil.buildObjectJson(0,"验证码发送成功", "");
						}else {
							str = JsonUtil.buildObjectJson(1,"验证码发送失败", ""); 
						}
					}else {
						str = JsonUtil.buildObjectJson(1,"验证码发送失败", ""); 
					}
					
				}else {//防止产生太多重复数据
					long nowTime = new Date().getTime() / 1000;
				    long sendTime = newcode.getSendTime().getTime() /1000;
				    if((nowTime - sendTime)>180) { //重新发送
				    	String sendcode = String.valueOf(new Random().nextInt(899999) + 100000);
						String resultcode = SendUtil.sendCode(code.getSendPhone(), sendcode);
						if(resultcode.equals("0")) {//发送成功
							//插入数据库
							code.setSendTime(new Date());
							code.setIsUse(0);
							code.setSendTime(new Date());
							code.setVerifyCode(sendcode);
							Integer result = verifyCodeDao.insertCode(code);
							if(result != null) { //插入成功
								str = JsonUtil.buildObjectJson(0,"验证码发送成功", "");
							}else {
								str = JsonUtil.buildObjectJson(1,"验证码发送失败", ""); 
							}
						}else {
							str = JsonUtil.buildObjectJson(1,"验证码发送失败", ""); 
						}
				    }else {
				    	str = JsonUtil.buildObjectJson(1, "发送短信太频繁,请稍后重试", "");
				    }

				}
			}
		}
		return str;
	}
	@Override
	public Integer checkVerifyCode(VerifyCode code) {
		code.setIsUse(0);
		VerifyCode check = verifyCodeDao.selectOne(code);
		if(check != null) {
			long sendTime = check.getSendTime().getTime() / 1000;
			long nowTime = new Date().getTime() /1000;
			if((nowTime-sendTime)>180) {
				//map.put("code",1);
				//map.put("msg","验证码已过期");
				return 1;
			}else {
				//map.put("code",0);
				//map.put("msg","验证通过");
				return 0;
			}
		}else {
			//map.put("code",1);
			//map.put("msg","手机号或验证码有误");
			return 2;
		}
	}
}
