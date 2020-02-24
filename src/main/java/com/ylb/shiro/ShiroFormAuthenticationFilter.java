package com.ylb.shiro;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMethod;

import com.alibaba.fastjson.JSONObject;
import com.ylb.util.TokenUtils;

/**
 *
 * 重写权限验证问题，登录失效后返回状态码
 *
 */
public class ShiroFormAuthenticationFilter extends FormAuthenticationFilter {

	Logger logger = LoggerFactory.getLogger(ShiroFormAuthenticationFilter.class);

	@Override
	protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
		// logger.info("onAccessDenied():");
		if (isLoginRequest(request, response)) {
			if (isLoginSubmission(request, response)) {
				// if (logger.isTraceEnabled()) {
				logger.info("Login submission detected.  Attempting to execute login.");
				// }
				//return executeLogin(request, response);
				return true;
			} else {
				if (logger.isTraceEnabled()) {
					logger.info("Login page view.");
				}
				// allow them to see the login page ;)
				return true;
			}
		} else {
			HttpServletRequest req = (HttpServletRequest) request;
			HttpServletResponse resp = (HttpServletResponse) response;
			if (req.getMethod().equals(RequestMethod.OPTIONS.name())) {
				resp.setStatus(HttpStatus.OK.value());
				return true;
			}

			if (logger.isTraceEnabled()) {
				logger.info("Attempting to access a path which requires authentication.  Forwarding to the "
						+ "Authentication url [" + getLoginUrl() + "]");
			}
			// 前端Ajax请求时requestHeader里面带一些参数，用于判断是否是前端的请求; // 前端Ajax请求，则不会重定向
			String token = req.getHeader("token");
			if (null != token && !token.isEmpty()) {
				logger.info("token:" + token);
				if ("client_login" == token|| token.equals("client_token")) {
					return true;
				}
				String auth = TokenUtils.checkToken(token);
				if (auth.length() > 1) {
					return true;
				} else {
					// saveRequestAndRedirectToLogin(request, response);
					unauthorized(req, resp, auth);
				}
			} else {
			    logger.info("token:" + token);
			    return true;
				//unauthorized(req, resp, null);
			}
			return false;
		}
	}

	private void unauthorized(HttpServletRequest req, HttpServletResponse resp, String auth) throws IOException {
		logger.info("unauthorized() check token result:" + auth);
		resp.setHeader("Access-Control-Allow-Origin", req.getHeader("Origin"));
		resp.setHeader("Access-Control-Allow-Credentials", "true");
		resp.setContentType("application/json; charset=utf-8");
		resp.setCharacterEncoding("UTF-8");
		PrintWriter out = resp.getWriter();
		JSONObject result = new JSONObject();
		if (null == auth) {
			result.put("msg", "toke is null");
			result.put("code", "1003");
		} else if (TokenUtils.TOKEN_ERROR == auth) {
			result.put("msg", "toke is eroor!");
			result.put("code", "1002");
		} else if (TokenUtils.TOKEN_FAILURE == auth) {
			result.put("msg", "fail to authorize!");
			result.put("code", "1001");
		} else if (TokenUtils.TOKEN_OVERDUE == auth) {
			result.put("msg", "token expire!");
			result.put("code", "1000");
		}
		out.println(result);
		out.flush();
		out.close();
	}
}