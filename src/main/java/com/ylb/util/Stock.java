package com.ylb.util;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONObject;

public class Stock {
	private final static Logger logger = LoggerFactory.getLogger(Stock.class);
	
	public static void main(String[] args) {
		System.out.println(new Date());
		String response = HttpUtils.sendGet("https://hq.sinajs.cn/", "list=sh600809");
		System.out.println(new Date());
		System.out.println("response:"+response);
		 Pattern p=Pattern.compile("(?<=\").*?(?=\")"); 
		 Matcher matcher=p.matcher(response); 
	     while(matcher.find()) {
	            System.out.println(matcher.group());
	            String group= matcher.group();
	            String [] stocks= group.split(",");
	            for(int i=0;i<stocks.length;i++) {
	            	System.out.println(i+":"+stocks[i]);	
	            }
	        }

	}
}
