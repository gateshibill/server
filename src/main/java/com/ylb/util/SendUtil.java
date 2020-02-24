package com.ylb.util;

import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Random;

/**
 * 发送验证码工具类
 * 
 * @author tyj
 * @date 2018-7-10 11:39:19
 */
public class SendUtil {
	
	private static final String ADDRESS = "apis.xntdx.com";// 远程地址：不带http://   apis.laidx.com

	private static final int PORT = 80;// 远程端口
	
	//private static final String ACCOUNT = "65F0B0B69B44470E8B407837022475B7";// 账户
	private static final String ACCOUNT = "6638EDF2F7504739A95A89B22E143142";// 账户
	//private static final String TOKEN = "dfc80713bd454c149c29915192d9d543";// token
	private static final String TOKEN = "7fdd513bf9a7443e989039f11fa0afef";// token
	
	private static final short R_TYPE = 0;// 响应类型 0 json类型，1 xml类型

	private static final String EXTNO = "";// 扩展号 可选

	/**
	 * 发送验证码
	 * 
	 * @param phone 手机号
	 * @return 		发送后的手机验证码，为null则为未发送
	 */
	public static String sendCode(String phone,String code) {		
		String sendBody = "【千吉时】您的验证码为：" + code + "，如非本人操作，请忽略。";// 短信内容
				
		KXTSmsSDK kxtsms = new KXTSmsSDK();

		kxtsms.init(ADDRESS, PORT, ACCOUNT, TOKEN);// 初始化

		try {
			sendBody = URLEncoder.encode(sendBody, "UTF-8");// URL编码 UTF-8方式
		} catch (Exception e) {

		}
		String result = kxtsms.send(phone, sendBody, R_TYPE, EXTNO);

		HashMap<String, Object> hashMap = null;
		
		hashMap = CommonUtils.jsonToMap(result);

		if (hashMap != null) {
			System.out.println("数据："+hashMap.get("Code"));
			if (hashMap.get("Code").toString().equals("\"0\"")) { // 发送成功
				return "0";
			}else {
				return "1";
			}
		}else {
			return "1";
		}
		
	}
	
}
