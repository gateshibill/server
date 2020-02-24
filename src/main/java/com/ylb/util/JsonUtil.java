package com.ylb.util;

import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class JsonUtil {

	private static Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").serializeNulls().create();

	public static String buildJson(List list) {
		Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").serializeNulls().create();
		String jsonString = "{";
		String JsonContext = "";
		int count = 0;
		if (!CommonUtil.isEmpty(list)) {
			JsonContext = gson.toJson(list);
			count = list.size();
			jsonString += "\"code\": \"0\", ";
			jsonString += "\"msg\": \"处理成功\",";
		} else {
			JsonContext = "[] ";
			count = 0;
			jsonString += "\"code\": \"1\", ";
			jsonString += "\"msg\": \"处理失败,没有数据\",";
		}
		jsonString += "\"count\": " + count + ",";
		jsonString += "\"objects\":";
		jsonString += JsonContext;
		jsonString += "} ";
		return jsonString;
	}

	public static String buildJsonForPage(List list, long totalCount, int pageSize) {
		Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").serializeNulls().create();
		String jsonString = "{";
		String JsonContext = "";
		int count = 0;
		if (!CommonUtil.isEmpty(list)) {
			JsonContext = gson.toJson(list);
			jsonString += "\"code\": \"0\", ";
			jsonString += "\"msg\": \"处理成功\",";
		} else {
			JsonContext = "[] ";
			jsonString += "\"code\": \"0\", ";
			jsonString += "\"msg\": \"没有数据\",";
		}
		jsonString += "\"count\": " + totalCount + ",";
		jsonString += "\"pageSize\": " + pageSize + ",";
		jsonString += "\"objects\":";
		jsonString += JsonContext;
		jsonString += "} ";
		return jsonString;
	}

	public static String buildCustomJson(String code, String msg, List list) {
		Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").serializeNulls().create();
		String jsonString = "{";
		String JsonContext = "";
		JsonContext = gson.toJson(list);
		int count = list.size();
		jsonString += "\"code\": \"" + code + "\", ";
		jsonString += "\"msg\": \"" + msg + "\",";
		jsonString += "\"count\": " + count + ",";
		jsonString += "\"objects\":";
		jsonString += JsonContext;
		jsonString += "} ";
		return jsonString;
	}

	public static String buildObjectJson(String code, String message) {
		String jsonString = "{";
		jsonString += "\"code\": \"" + code + "\", ";
		jsonString += "\"msg\": \"" + message + "\" ";
		jsonString += "}";
		return jsonString;
	}

	/**
	 * 返回json字符串
	 * 
	 * @param code
	 * @param msg
	 * @param object
	 * @return
	 */
	public static String buildObjectJson(int code, String msg, Object object) {
		String str = JsonUtil.gson.toJson(object);
		return String.format("{\"code\": \"%d\", \"msg\": \"%s\", \"objects\": %s}", code, msg, str);
	}

	public static String buildSuccessJson(String code, String message) {
		return String.format("{\"code\": \"%s\", \"msg\": \"%s\"}", code, message);
	}

	public static String buildSuccessJsonCount(String code, int count) {
		return String.format("{\"code\": %s, \"count\": %s}", code, count);
	}

	// 返回总记录数
	public static String buildJsonByTotalCount(List list, int totalCount) {
		Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").serializeNulls().create();
		String jsonString = "{";
		String JsonContext = "";
		int count = 0;
		if (!CommonUtil.isEmpty(list)) {
			JsonContext = gson.toJson(list);
			count = totalCount;
			jsonString += "\"code\": \"0\", ";
			jsonString += "\"msg\": \"处理成功\",";
		} else {
			JsonContext = "[] ";
			count = 0;
			jsonString += "\"code\": \"1\", ";
			jsonString += "\"msg\": \"此查询无数据\",";
		}
		jsonString += "\"count\": " + count + ",";
		jsonString += "\"data\":";
		jsonString += JsonContext;
		jsonString += "} ";
		return jsonString;
	}

	// 返回总数、商品销量总数
	public static String buildBackJsonCountPlat(List list, int totalCount, String goodsCount) {

		Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").serializeNulls().create();
		String jsonString = "{";
		String JsonContext = "";
		int count = 0;
		if (!CommonUtil.isEmpty(list)) {
			JsonContext = gson.toJson(list);
			count = totalCount;
			jsonString += "\"code\": \"0\", ";
			jsonString += "\"msg\": \"处理成功\",";
		} else {
			JsonContext = "[] ";
			count = 0;
			jsonString += "\"code\": \"1\", ";
			jsonString += "\"msg\": \"此查询无数据\",";
		}
		jsonString += "\"count\": " + count + ",";
		jsonString += "\"goodsCount\": " + goodsCount + ",";
		jsonString += "\"objects\":";
		jsonString += JsonContext;
		jsonString += "} ";
		return jsonString;
	}

	// 返回总数、商品销量总数
	public static String buildBackJsonCountPlat5(List list, int totalCount, String goodsCount, String rebateMoney) {

		Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").serializeNulls().create();
		String jsonString = "{";
		String JsonContext = "";
		int count = 0;
		if (!CommonUtil.isEmpty(list)) {
			JsonContext = gson.toJson(list);
			count = totalCount;
			jsonString += "\"code\": \"0\", ";
			jsonString += "\"msg\": \"处理成功\",";
		} else {
			JsonContext = "[] ";
			count = 0;
			jsonString += "\"code\": \"1\", ";
			jsonString += "\"msg\": \"此查询无数据\",";
		}
		jsonString += "\"count\": " + count + ",";
		jsonString += "\"goodsCount\": " + goodsCount + ",";
		jsonString += "\"rebateMoneyCount\": " + rebateMoney + ",";
		jsonString += "\"data\":";
		jsonString += JsonContext;
		jsonString += "} ";
		return jsonString;
	}

	public static String MapToJson(Map<String, Object> m) {
		String jsonString = "{";
		Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").serializeNulls().create();
		if (!CommonUtil.isEmpty(m)) {
			jsonString += "\"code\": \"0\", ";
			jsonString += "\"msg\": \"处理成功\",";
			String listJson = gson.toJson(m);
			jsonString += "\"objects\":";
			jsonString += listJson;
		} else {

			jsonString += "[] ";
		}
		jsonString += "} ";
		return jsonString;
	}

	public static String MapToJsonString(Map<String, String> m) {
		String jsonString = "{";
		Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").serializeNulls().create();
		if (!CommonUtil.isEmpty(m)) {
			jsonString += "\"code\": \"0\", ";
			jsonString += "\"msg\": \"处理成功\",";
			String listJson = gson.toJson(m);
			jsonString += "\"objects\":";
			jsonString += listJson;
		} else {

			jsonString += "[] ";
		}
		jsonString += "} ";
		return jsonString;
	}

	public static String objectToJson(Object obj) {
		String str = JsonUtil.gson.toJson(obj);
		return String.format("{\"code\": \"0\", \"msg\": \"处理成功\", \"objects\": %s}", str);
	}

	public static String objectToJson(String code, Object obj) {
		String str = JsonUtil.gson.toJson(obj);
		return String.format("{\"code\": %s, \"msg\": \"处理成功\", \"objects\": %s}", code, str);
	}

	public static String buildBackJson(List list, int totalCount) {
		Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").serializeNulls().create();
		String jsonString = "{";
		String JsonContext = "";
		if (!CommonUtil.isEmpty(list)) {
			JsonContext = gson.toJson(list);
			jsonString += "\"code\": \"0\", ";
			jsonString += "\"msg\": \"处理成功\",";
		} else {
			JsonContext = "[] ";
			jsonString += "\"code\": \"1\", ";
			jsonString += "\"msg\": \"此查询无数据\",";
		}
		jsonString += "\"count\": " + totalCount + ",";
		jsonString += "\"objects\":";
		jsonString += JsonContext;
		jsonString += "} ";
		return jsonString;
	}

	public static String buildLayeditJson(Map<String, Object> m) {
		String jsonString = "{";
		Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").serializeNulls().create();
		if (!CommonUtil.isEmpty(m)) {
			jsonString += "\"code\": \"0\", ";
			jsonString += "\"msg\": \"处理成功\",";
			String listJson = gson.toJson(m);
			jsonString += "\"data\":";
			jsonString += listJson;
		} else {

			jsonString += "[] ";
		}
		jsonString += "} ";
		return jsonString;
	}

	public static String stringify(Object obj) {
		return JsonUtil.gson.toJson(obj);
	}

	public static JSONObject parse(String json) {
		return JSONObject.parseObject(json);
	}

	public static String bulidObjectJson(Object gud) {
		Gson gson = new GsonBuilder().serializeNulls().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
		String jsonString = "{";
		String JsonContext = "";
		if (!CommonUtil.isEmpty(gud)) {
			JsonContext = gson.toJson(gud);
			jsonString += "\"code\": \"0\", ";
			jsonString += "\"msg\": \"处理成功\",";
		} else {
			JsonContext = "[] ";
			jsonString += "\"code\": \"1\", ";
			jsonString += "\"msg\": \"此查询无数据\",";
		}
		jsonString += "\"data\":";
		jsonString += JsonContext;
		jsonString += "} ";
		return jsonString;
	}
}
