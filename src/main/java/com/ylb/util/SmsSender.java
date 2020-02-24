/*
 * 功能：短信来了SDK核心文件
 * 版本：1.3
 * 日期：2016-07-01
 * 说明：
 * 以下代码只是为了方便客户测试而提供的样例代码，客户可以根据自己网站的需要，按照技术文档自行编写,并非一定要使用该代码。
 * 该代码仅供学习和研究使用，只是提供一个参考。
 * 网址：http://www.laidx.com
 */
package com.ylb.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;
import java.text.MessageFormat;

import com.alibaba.fastjson.JSONObject;

public class SmsSender {
	final static private String TOKEN = "e22e0f893b0d6087d94640d4d9e9dd3c";
	final static private String URL = "http://emailapi.gycdn1.com:8888/api.php?c=sms&m=send&d=api&token=";

	public static String send(String mobile, String text, int model) {
		String url = URL + TOKEN;
		System.out.println("url:" + url);
		String body = MessageFormat.format("mobile={0}&text={1}&sendmodel={2}", mobile, text, model);
		System.out.println("params:" + body);
		return getResponse(url, body);
	}

	/**
	 * 获取请求数据
	 * 
	 * @param uriStr 请求路径
	 * @param param  请求参数
	 * @param rType  响应数据类型 0 Json 类型，1 xml 类型
	 * @return
	 */
	private static String getResponse(String uriStr, String param) {
		PrintWriter out = null;
		BufferedReader in = null;
		String result = "";
		try {
			URL realUrl = new URL(uriStr);
			// 打开和URL之间的连接
			URLConnection conn = realUrl.openConnection();
			// 设置通用的请求属性
			conn.setConnectTimeout(50000);
			conn.setRequestProperty("Accept-Charset", "UTF-8");
			conn.setRequestProperty("connection", "Keep-Alive");
			conn.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
			// 发送POST请求必须设置如下两行
			conn.setDoOutput(true);
			conn.setDoInput(true);
			// 获取URLConnection对象对应的输出流
			out = new PrintWriter(conn.getOutputStream());
			// 发送请求参数
			out.print(param);
			// flush输出流的缓冲
			out.flush();
			// 定义BufferedReader输入流来读取URL的响应
			in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
			String line;
			while ((line = in.readLine()) != null) {
				result += line;
			}
		} catch (Exception e) {
			System.out.println("发送 POST 请求出现异常！" + e);
			e.printStackTrace();
		}
		// 使用finally块来关闭输出流、输入流
		finally {
			try {
				if (out != null) {
					out.close();
				}
				if (in != null) {
					in.close();
				}
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
		return result;
	}

	public static void main(String[] args) {
		String result = send("18608849549", "123456", 2);
		JSONObject resultJson = JSONObject.parseObject(result);
		System.out.println(resultJson.toString());
		System.out.println("status:"+resultJson.getString("status"));
		if (resultJson.getString("status").equals("3000001"))// 发送短信失败
		{
			System.out.println("sccuss");
			// output(response, JsonUtil.buildSuccessJson("2", "fail to send sms"));
			return;
		} else {
			System.out.println("failure");
		}
	}

}
