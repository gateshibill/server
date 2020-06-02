package com.ylb.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.ylb.entity.Role;
import com.ylb.entity.RolePower;
import com.ylb.entity.User;
import com.ylb.service.RolePowerService;
import com.ylb.service.RoleService;
import com.ylb.util.BaseUtil;
import com.ylb.util.HttpUtils;
import com.ylb.util.JsonUtil;

@Controller
@RequestMapping("/stock")
public class StockController extends BaseUtil {
	private final static Logger logger = LoggerFactory.getLogger(StockController.class);
	String TOKEN = "test";

	@ResponseBody
	@RequestMapping(value = "/getLatestPrice", method = RequestMethod.GET)
	public void getLatestPrice(HttpServletRequest request, HttpServletResponse response, String code, String token) {
		logger.info("getLatestPrice code:" + code);
		Double nowPrice = 0.00;
		if (null == token || !token.equalsIgnoreCase(TOKEN)) {
			output(response, JsonUtil.buildObjectJson("3", "无效token"));
			return;
		}

		try {
			String res = HttpUtils.sendGet("https://hq.sinajs.cn/", "list=" + code);
			// System.out.println(new Date());
			System.out.println("response:" + res);
			Pattern p = Pattern.compile("(?<=\").*?(?=\")");
			Matcher matcher = p.matcher(res);
			if (matcher.find()) {
				String group = matcher.group();
				String[] stocks = group.split(",");
				nowPrice = Double.parseDouble(stocks[3]);
				output(response, JsonUtil.buildObjectJson(0, "success", nowPrice.toString()));
			} else {
				output(response, JsonUtil.buildObjectJson("2", "解析错误"));
			}
		} catch (Exception e) {
			e.printStackTrace();
			output(response, JsonUtil.buildObjectJson("1", "系統错误"));
		}
	}

	@ResponseBody
	@RequestMapping(value = "/getLatestQuote", method = RequestMethod.GET)
	public void getLatestQuote(HttpServletRequest request, HttpServletResponse response, String code, String token) {
		logger.info("getLatestQuote() code:" + code);
		if (null == token || !token.equalsIgnoreCase(TOKEN)) {
			output(response, JsonUtil.buildObjectJson("3", "无效token"));
			return;
		}
		try {
			String res = HttpUtils.sendGet("https://hq.sinajs.cn/", "list=" + code);
			output(response, JsonUtil.buildObjectJson(0, "success", res));
		} catch (Exception e) {
			e.printStackTrace();
			output(response, JsonUtil.buildObjectJson("1", "系統错误"));
		}
	}
	
	@ResponseBody
	@RequestMapping(value = "/getMinKLineData", method = RequestMethod.GET)
	public void getMinKLineData(HttpServletRequest request, HttpServletResponse response, String code, String token) {
		logger.info("getKLineData() code:" + code);
		if (null == token || !token.equalsIgnoreCase(TOKEN)) {
			output(response, JsonUtil.buildObjectJson("3", "无效token"));
			return;
		}
		try {
			String res = HttpUtils.sendGet("http://web.ifzq.gtimg.cn/appstock/app/minute/query?","_var=min_data_" + code + "&code=" + code + "&r=0.040982807166606294");
			output(response, JsonUtil.buildObjectJson(0, "success", res));
		} catch (Exception e) {
			e.printStackTrace();
			output(response, JsonUtil.buildObjectJson("1", "系統错误"));
		}
	}
	@ResponseBody
	@RequestMapping(value = "/getDayKLineData", method = RequestMethod.GET)
	public void getDayKLineData(HttpServletRequest request, HttpServletResponse response, String code, String token) {
		logger.info("getKLineData() code:" + code);
		if (null == token || !token.equalsIgnoreCase(TOKEN)) {
			output(response, JsonUtil.buildObjectJson("3", "无效token"));
			return;
		}
		try {
			String res = HttpUtils.sendGet("http://web.ifzq.gtimg.cn/appstock/app/kline/kline?","_var=kline_day&param=" + code + ",day,,,60,&r=0.6762027715074195");
			output(response, JsonUtil.buildObjectJson(0, "success", res));
		} catch (Exception e) {
			e.printStackTrace();
			output(response, JsonUtil.buildObjectJson("1", "系統错误"));
		}
	}
	
	@ResponseBody
	@RequestMapping(value = "/getWeekKLineData", method = RequestMethod.GET)
	public void getWeekKLineData(HttpServletRequest request, HttpServletResponse response, String code, String token) {
		logger.info("getKLineData() code:" + code);
		if (null == token || !token.equalsIgnoreCase(TOKEN)) {
			output(response, JsonUtil.buildObjectJson("3", "无效token"));
			return;
		}
		try {
			String res = HttpUtils.sendGet("http://web.ifzq.gtimg.cn/appstock/app/kline/kline?","_var=kline_week&param=" + code + ",week,,,60,&r=0.28570913281575394");
			output(response, JsonUtil.buildObjectJson(0, "success", res));
		} catch (Exception e) {
			e.printStackTrace();
			output(response, JsonUtil.buildObjectJson("1", "系統错误"));
		}
	}
	@ResponseBody
	@RequestMapping(value = "/getMonthKLineData", method = RequestMethod.GET)
	public void getMonthKLineData(HttpServletRequest request, HttpServletResponse response, String code, String token) {
		logger.info("getKLineData() code:" + code);
		if (null == token || !token.equalsIgnoreCase(TOKEN)) {
			output(response, JsonUtil.buildObjectJson("3", "无效token"));
			return;
		}
		try {
			String res = HttpUtils.sendGet("http://web.ifzq.gtimg.cn/appstock/app/kline/kline?","_var=kline_month&param=" + code + ",month,,,60,&r=0.6762027715074195");
			output(response, JsonUtil.buildObjectJson(0, "success", res));
		} catch (Exception e) {
			e.printStackTrace();
			output(response, JsonUtil.buildObjectJson("1", "系統错误"));
		}
	}
}
