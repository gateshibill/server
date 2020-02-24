package com.ylb.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ylb.entity.Admin;
import com.ylb.service.AdminService;
import com.ylb.util.JsonUtil;

@Controller
@RequestMapping("/login")
public class LoginController{
	@Autowired
	private AdminService adminService;
	/**
	 * 进入登录页面
	 * @return
	 */
	@RequestMapping("/login")
	public String login(HttpServletRequest request) {
		Admin admin = (Admin) request.getSession().getAttribute("stockManageAdmin");
		if(admin == null) {
			return "login";
		}else {
			return "index";
		}
	}
	/**
	 * 登录
	 * @param request
	 * @param response
	 * @param adminAccount
	 * @param password
	 */
	@ResponseBody
	@RequestMapping(value="/dologin",method=RequestMethod.POST)
	public String doLogin(HttpServletRequest request,HttpServletResponse response,String adminAccount,String password) {
		String result = adminService.adminLogin(request, adminAccount, password);
		return result;
	}
	/**
	 * shiro登录
	 * @param adminAccount
	 * @param password
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/shirologin")
	public String shiroLogin(HttpSession session,String adminAccount,String password) {
		return adminService.shiroLogin(session, adminAccount, password);
	}
//	/**
//	 * 退出系统
//	 * @param request
//	 * @param response
//	 */
//	@ResponseBody
//	@RequestMapping(value="/loginout",method=RequestMethod.POST)
//	public String loginout(HttpSession session,HttpServletResponse response) {
//		int code = 1;
//		String msg = "退出系统失败";
//		try {
//			session.removeAttribute("stockManageAdmin");
//			code = 0;
//			msg="退出成功";
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return JsonUtil.buildFalseJson(code, msg, "");
//	}
}
