package com.ylb.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
/**
 * 错误输出页面
 * @author Administrator
 *
 */
@Controller
@RequestMapping("/error")
public class ErrorController{
	/**
	 * 进入404页面
	 * @return
	 */
	@RequestMapping("/page404")
	public String page404() {
		return "404";
	}
	/**
	 * 进入403页面
	 * @return
	 */
	@RequestMapping("/page403")
	public String page403() {
		return  "403";
	}
	/**
	 * 进入500页面
	 * @return
	 */
	@RequestMapping("/page500")
	public String page500() {
		return "500";
	}
}
