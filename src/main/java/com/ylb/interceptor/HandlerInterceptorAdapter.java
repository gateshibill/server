//package com.ylb.interceptor;
//
//import org.springframework.stereotype.Component;
//import org.springframework.web.servlet.HandlerInterceptor;
//
//import com.ylb.entity.Admin;
//
//import java.util.Enumeration;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//
//@Component
//public class HandlerInterceptorAdapter implements HandlerInterceptor {
//	/**
//	 * 在请求处理之前进行调用(Controller 方法调用之前)
//	 * 
//	 * @return
//	 */
//	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
//			throws Exception {
//		System.out.println("HandlerInterceptorAdapter preHandle():" + response.getStatus());
//		// 1.判断登录
//		Admin admin = (Admin) request.getSession().getAttribute("stockManageAdmin");
//		Enumeration<String> token = request.getHeaders("token");
//		if (admin != null) {
//			System.out.println("this is pc login:" + admin.getAdminAccount());
//		} else if (null != token) {
//			System.out.println("this is mobile login:" + token);
//		} else {
//			response.sendRedirect("/user/login");
//			return false;
//		}
//
//		if (response.getStatus() == 404) { // 请求失败,请求所希望得到的资源在服务器上未发现
//			response.sendRedirect("/error/page404");
//			return false;
//		} else if (response.getStatus() == 403) { // 服务器拒绝执行
//			response.sendRedirect("/error/page403");
//			return false;
//		} else if (response.getStatus() == 500) { // 遇到错误
//			response.sendRedirect("/error/page500");
//			return false;
//		}
//		return true;
//	}
//
//	/**
//	 * 请求处理之后调用,但是在视图被渲染之前(Controller 方法调用之后)
//	 */
//	public void postHandle() {
//		System.out.println("456");
//	}
//
//	/**
//	 * 在整个请求结束之后被调用,也就是在dispatcherServlet渲染对应的视图之后执行
//	 */
//	public void afterCompletion() {
//		System.out.println("789");
//	}
//}
