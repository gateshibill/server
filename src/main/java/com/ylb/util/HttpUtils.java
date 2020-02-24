package com.ylb.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;
import com.ylb.controller.OfficialAccountController;

import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

public class HttpUtils {
	private final static Logger logger = LoggerFactory.getLogger(HttpUtils.class);
//	public final static String serverName = "192.168.10.192";
//	public final static String subscribeUrl = "http://%s:9090/plugins/restapi/v1/pubsub/topic/subscribe";
//	public final static String unsubscribeUrl = "http://%s:9090/plugins/restapi/v1/pubsub/topic/unsubscribe";
//	public final static String createSubscribeUrl = "http://%s:9090/plugins/restapi/v1/pubsub/topic";
//	public final static String publicSubscribeUrl = "http://%s:9090/plugins/restapi/v1/pubsub/topic/publish";
//	public final static String Authorization = "a3O5CXzxFW6Xahhv";
	
	public final static String serverName = "10.1.1.15";
	public final static String subscribeUrl = "https://%s:9091/plugins/restapi/v1/pubsub/topic/subscribe";
	public final static String unsubscribeUrl = "https://%s:9091/plugins/restapi/v1/pubsub/topic/unsubscribe";
	public final static String createSubscribeUrl = "https://%s:9091/plugins/restapi/v1/pubsub/topic";
	public final static String publicSubscribeUrl = "https://%s:9091/plugins/restapi/v1/pubsub/topic/publish";
	public final static String Authorization = "a3O5CXzxFW6Xahhv";
		
	/**
	 * 发送GET请求
	 * 
	 * @param url        目的地址
	 * @param parameters 请求参数，Map类型。
	 * @return 远程响应结果
	 */
	public static String sendGet(String url, Map<String, String> parameters) {
		String result = "";
		BufferedReader in = null;// 读取响应输入流
		StringBuffer sb = new StringBuffer();// 存储参数
		String params = "";// 编码之后的参数
		try {
			// 编码请求参数
			String full_url = url;
			if (parameters != null) {
				if (parameters.size() == 1) {
					for (String name : parameters.keySet()) {
						sb.append(name).append("=").append(java.net.URLEncoder.encode(parameters.get(name), "UTF-8"));
					}
					params = sb.toString();
				} else {
					for (String name : parameters.keySet()) {
						sb.append(name).append("=").append(java.net.URLEncoder.encode(parameters.get(name), "UTF-8"))
								.append("&");
					}
					String temp_params = sb.toString();
					params = temp_params.substring(0, temp_params.length() - 1);
				}
				full_url = url + "?" + params;
			}
			System.out.println(full_url);
			// 创建URL对象
			java.net.URL connURL = new java.net.URL(full_url);
			// 打开URL连接
			java.net.HttpURLConnection httpConn = (java.net.HttpURLConnection) connURL.openConnection();
			// 设置通用属性
			httpConn.setRequestProperty("Accept", "*/*");
			httpConn.setRequestProperty("Connection", "Keep-Alive");
			httpConn.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 6.1)");
			// 建立实际的连接
			httpConn.connect();
			// 响应头部获取
			// Map<String, List<String>> headers = httpConn.getHeaderFields();
			// 遍历所有的响应头字段
//            for (String key : headers.keySet()) {  
//                System.out.println(key + "\t：\t" + headers.get(key));  
//            }  
			// 定义BufferedReader输入流来读取URL的响应,并设置编码方式
			in = new BufferedReader(new InputStreamReader(httpConn.getInputStream(), "UTF-8"));
			String line;
			// 读取返回的内容
			while ((line = in.readLine()) != null) {
				result += line;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (in != null) {
					in.close();
				}
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
		return result;
	}

	public static String sendPostEx(String url, String authorization, MessageBody mb) {
		System.out.println("sendPostEx:" + url + "/" + authorization);
		String result = "";// 返回的结果
		BufferedReader in = null;// 读取响应输入流
		PrintWriter out = null;
		StringBuffer sb = new StringBuffer();// 处理请求参数
		String params = "";// 编码之后的参数
		try {
			// 编码请求参数
//			if (parameters != null) {
//				if (parameters.size() == 1) {
//					for (String name : parameters.keySet()) {
//						sb.append(name).append("=").append(java.net.URLEncoder.encode(parameters.get(name), "UTF-8"));
//					}
//					params = sb.toString();
//				} else {
//					for (String name : parameters.keySet()) {
//						sb.append(name).append("=").append(java.net.URLEncoder.encode(parameters.get(name), "UTF-8"))
//								.append("&");
//					}
//					String temp_params = sb.toString();
//					params = temp_params.substring(0, temp_params.length() - 1);
//				}
			// }
			// 创建URL对象
			java.net.URL connURL = new java.net.URL(url);
			// 打开URL连接
			java.net.HttpURLConnection httpConn = (java.net.HttpURLConnection) connURL.openConnection();
			// 设置通用属性
			httpConn.setRequestProperty("Content-Type", "application/json");
			// httpConn.setRequestProperty("Accept", "*/*");
			httpConn.setRequestProperty("Connection", "Keep-Alive");
			httpConn.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 6.1)");
			httpConn.setRequestProperty("Authorization", authorization);
			// 设置POST方式
			httpConn.setDoInput(true);
			httpConn.setDoOutput(true);

			// 获取HttpURLConnection对象对应的输出流
			out = new PrintWriter(httpConn.getOutputStream());
			Gson gson = new Gson();
			String jsonStr = gson.toJson(mb);
			// 发送请求参数
			System.out.println("sendPostExbody:" + jsonStr);
			out.write(jsonStr);
			// flush输出流的缓冲
			out.flush();
			// 定义BufferedReader输入流来读取URL的响应，设置编码方式
			in = new BufferedReader(new InputStreamReader(httpConn.getInputStream(), "UTF-8"));
			String line;
			// 读取返回的内容
			while ((line = in.readLine()) != null) {
				result += line;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
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

	public static String post(String strURL, String authorization, MessageBody mb) {
		BufferedReader reader = null;
		try {
			URL url = new URL(strURL);// 创建连接
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setDoOutput(true);
			connection.setDoInput(true);
			connection.setUseCaches(false);
			connection.setInstanceFollowRedirects(true);
			connection.setRequestMethod("POST"); // 设置请求方式

			connection.setRequestProperty("Content-Type", "application/json");
			// httpConn.setRequestProperty("Accept", "*/*");
			//connection.setRequestProperty("Connection", "Keep-Alive");
			//connection.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 6.1)");
			connection.setRequestProperty("Authorization", authorization);
			connection.connect();
			// 一定要用BufferedReader 来接收响应， 使用字节来接收响应的方法是接收不到内容的
			OutputStreamWriter out = new OutputStreamWriter(connection.getOutputStream(), "UTF-8"); // utf-8编码
			Gson gson = new Gson();
			String jsonStr = gson.toJson(mb);
			System.out.println("body:" + jsonStr);
			out.append(jsonStr);
			out.flush();
			out.close();
			// 读取响应
			reader = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"));
			String line;
			String res = "";
			while ((line = reader.readLine()) != null) {
				res += line;
			}
			reader.close();
			return res;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "error"; // 自定义错误信息
	}

	/**
	 * 发送POST请求
	 * 
	 * @param url        目的地址
	 * @param parameters 请求参数，Map类型。
	 * @return 远程响应结果
	 */
	public static String sendPost(String url, Map<String, String> parameters) {
		String result = "";// 返回的结果
		BufferedReader in = null;// 读取响应输入流
		PrintWriter out = null;
		StringBuffer sb = new StringBuffer();// 处理请求参数
		String params = "";// 编码之后的参数
		try {
			// 编码请求参数
			if (parameters != null) {
				if (parameters.size() == 1) {
					for (String name : parameters.keySet()) {
						sb.append(name).append("=").append(java.net.URLEncoder.encode(parameters.get(name), "UTF-8"));
					}
					params = sb.toString();
				} else {
					for (String name : parameters.keySet()) {
						sb.append(name).append("=").append(java.net.URLEncoder.encode(parameters.get(name), "UTF-8"))
								.append("&");
					}
					String temp_params = sb.toString();
					params = temp_params.substring(0, temp_params.length() - 1);
				}
			}
			// 创建URL对象
			java.net.URL connURL = new java.net.URL(url);
			// 打开URL连接
			java.net.HttpURLConnection httpConn = (java.net.HttpURLConnection) connURL.openConnection();
			// 设置通用属性
			httpConn.setRequestProperty("Accept", "*/*");
			httpConn.setRequestProperty("Connection", "Keep-Alive");
			httpConn.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 6.1)");
			// 设置POST方式
			httpConn.setDoInput(true);
			httpConn.setDoOutput(true);
			// 获取HttpURLConnection对象对应的输出流
			out = new PrintWriter(httpConn.getOutputStream());
			// 发送请求参数
			out.write(params);
			// flush输出流的缓冲
			out.flush();
			// 定义BufferedReader输入流来读取URL的响应，设置编码方式
			in = new BufferedReader(new InputStreamReader(httpConn.getInputStream(), "UTF-8"));
			String line;
			// 读取返回的内容
			while ((line = in.readLine()) != null) {
				result += line;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
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

	/**
	 * get请求
	 * 
	 * @return
	 */
	public static String doGet(String url) {
		String result = "";
		BufferedReader in = null;// 读取响应输入流
		try {
			// 编码请求参数
			String full_url = url;
			// 创建URL对象
			java.net.URL connURL = new java.net.URL(full_url);
			// 打开URL连接
			java.net.HttpURLConnection httpConn = (java.net.HttpURLConnection) connURL.openConnection();
			// 设置通用属性
			httpConn.setRequestProperty("Accept", "*/*");
			httpConn.setRequestProperty("Connection", "Keep-Alive");
			httpConn.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 6.1)");
			// 建立实际的连接
			httpConn.connect();
			// 响应头部获取
//	            Map<String, List<String>> headers = httpConn.getHeaderFields();  
			// 遍历所有的响应头字段
//	            for (String key : headers.keySet()) {  
//	                System.out.println(key + "\t：\t" + headers.get(key));  
//	            }  
			// 定义BufferedReader输入流来读取URL的响应,并设置编码方式
			in = new BufferedReader(new InputStreamReader(httpConn.getInputStream(), "UTF-8"));
			String line;
			// 读取返回的内容
			while ((line = in.readLine()) != null) {
				result += line;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (in != null) {
					in.close();
				}
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
		return result;
	}

	public static String sendGet(String url, String param) {
		String result = "";
		BufferedReader in = null;
		try {
			String urlNameString = url + "?" + param;
			URL realUrl = new URL(urlNameString);
			// 打开和URL之间的连接
			URLConnection connection = realUrl.openConnection();
			// 设置通用的请求属性
			connection.setRequestProperty("accept", "*/*");
			connection.setRequestProperty("connection", "Keep-Alive");
			connection.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
			// 建立实际的连接
			connection.connect();
			// 获取所有响应头字段
			Map<String, List<String>> map = connection.getHeaderFields();
			// 遍历所有的响应头字段
			for (String key : map.keySet()) {
				System.out.println(key + "--->" + map.get(key));
			}
			// 定义 BufferedReader输入流来读取URL的响应
			in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			String line;
			while ((line = in.readLine()) != null) {
				result += line;
			}
		} catch (Exception e) {
			System.out.println("发送GET请求出现异常！" + e);
			e.printStackTrace();
		}
		// 使用finally块来关闭输入流
		finally {
			try {
				if (in != null) {
					in.close();
				}
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		// System.out.println("httpGet方法返回值：" + result);
		return result;
	}

	public static int pushOfficailAccoutMessage(HttpServletRequest request, String subscribeServiceId, String username,
			Integer messageId, Integer subject, String content) {
		System.out.println("officailaccoutServer:" + subscribeServiceId + "/" + username + "/" + messageId + "/" + subject + "/"
				+ content);
		//String serverName = HttpUtils.getServerName(request);
		//String serverName = "192.168.10.195";
		String url = String.format(publicSubscribeUrl, serverName);
		MessageBody mb = new MessageBody();
		mb.topicId=subscribeServiceId;
		mb.itemId=messageId.toString();
		mb.username=username;
		mb.subject=subject.toString();
		mb.data= content;
		String result = HttpUtils.post(url, Authorization, mb);
		int code = 0;
		if (null == result) {
			code = 2;
		} else {
			System.out.println("response:" + result);
			JSONObject jsonObj = JSONObject.parseObject(result);
			if (null == jsonObj) {
				code = 1;
			} else {
				code = (int) jsonObj.get("code");
				String message = (String) jsonObj.get("message");
				logger.info("message:" + message);
			}
		}

		if (200 != code) {
			logger.info("fail to subscribe userId/officialAccountId" + username + "/" + subscribeServiceId);
		}
		return code;
	}

	public static int subscribeOfficailAccoutServer(HttpServletRequest request, String officialAccountId,
			String userId) {
		System.out.println("subscribeOfficailAccoutServer():" + officialAccountId + "/" + userId);
		//String serverName = HttpUtils.getServerName(request);
		//serverName = "192.168.10.195";
		String url = String.format(subscribeUrl, serverName);
		return officailaccoutServer(officialAccountId, userId, url);
	}

	public static int unsubscribeOfficailAccoutServer(HttpServletRequest request, String officialAccountId,
			String userId) {
		//String serverName = HttpUtils.getServerName(request);
		//serverName = "192.168.10.195";
		String url = String.format(unsubscribeUrl, serverName);
		return officailaccoutServer(officialAccountId, userId, url);
	}

	private static int officailaccoutServer(String officialAccountId, String userId, String url) {
		System.out.println("officailaccoutServer:" + officialAccountId + "/" + userId + "/" + url);
		Map<String, String> parameters = new HashMap<String, String>();
		parameters.put("topicId", officialAccountId);
		parameters.put("username", userId);
		MessageBody mb = new MessageBody();
		mb.topicId = officialAccountId;
		mb.username = userId;
		String result = HttpUtils.post(url, Authorization, mb);
		int code = 0;
		if (null == result) {
			code = 2;
		} else {
			System.out.println("response:" + result);
			JSONObject jsonObj = JSONObject.parseObject(result);
			if (null == jsonObj) {
				code = 1;
			} else {
				code = (int) jsonObj.get("code");
				String message = (String) jsonObj.get("message");
				logger.info("message:" + message);
			}
		}

		if (200 != code) {
			logger.error("fail to subscribe userId/officialAccountId" + userId + "/" + officialAccountId);
		}
		return code;
	}

	public static String getServerName(HttpServletRequest request) {
		String serverName = request.getServerName(); // 获取域名
		int serverPort = request.getServerPort(); // 获取端口号
		if (80 == serverPort) {
			serverName = serverName + ":80";
		}
		return serverName;
	}
}

class MessageBody {
	String topicId;
	String itemId;
	String username;
	String subject;
	String data;
}
