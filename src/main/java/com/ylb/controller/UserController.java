package com.ylb.controller;

import java.io.InputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.IOUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.ylb.dao.ExSmsDao;
import com.ylb.dao.OfPropertyDao;
import com.ylb.entity.ExSms;
import com.ylb.entity.User;
import com.ylb.entity.UserManageOaRole;
import com.ylb.service.ExSmsService;
import com.ylb.service.OfPropertyService;
import com.ylb.service.UserManageOaRoleService;
import com.ylb.service.UserService;
import com.ylb.util.BaseUtil;
import com.ylb.util.JsonUtil;
import com.ylb.util.SmsSender;
import com.ylb.util.SmsUtil;
import com.ylb.util.TokenUtils;
import com.ylb.util.openfire.client.auth.AuthFactory;

@Controller
@RequestMapping("/user")
public class UserController extends BaseUtil {
	private static final String MYOPENFIRE = "myopenfire";
	private final static Logger logger = LoggerFactory.getLogger(UserController.class);
	// @Autowired
	// private UserModelService userModelService;
	@Autowired
	private UserService userService;
	@Autowired
	private UserManageOaRoleService userManageOaRoleService;
	@Autowired
	private OfPropertyService ofPropertyService;
	@Autowired
	private ExSmsService exSmsService;

	private static Map<String, JSONObject> verifyCodeMap = new HashMap<String, JSONObject>();
	public static String passwordKey = "passwordKey";

	@RequestMapping(value = "/checkConnect", method = RequestMethod.GET)
	public void checkConnect(HttpServletRequest request, HttpServletResponse response) {
		output(response, JsonUtil.buildObjectJson("200", ""));
	}

	/**
	 * 登录
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public void login(HttpSession session, HttpServletRequest request, HttpServletResponse response) {
		logger.info("login");
		try {
			InputStream is = request.getInputStream();
			String message = IOUtils.toString(is, "UTF-8");
			System.out.println("message:" + message);
			Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").serializeNulls().create();
			User User = gson.fromJson(message, User.class);
			User user = null;
			String username = User.getUsername();
			String password = User.getPassword();
			if (null != User.getPhone() && !User.getPhone().isEmpty()) {
				user = userService.getUserByPhone(User.getPhone());
				if (null == user) {
					output(response, JsonUtil.buildObjectJson("1", "手机号码用户不存在"));
					return;
				}
				username = user.getUsername();
			} else if (null != User.getName() && !User.getName().isEmpty()) {
				user = userService.getUserByName(User.getName());
				if (null == user) {
					output(response, JsonUtil.buildObjectJson("2", "利宝号不存在"));
					return;
				}
				username = user.getUsername();
			} else if (null != username && !username.isEmpty()) {
				System.out.println("dont need to query user");
			} else {
				output(response, JsonUtil.buildObjectJson("3", "输入错误，手机、利宝号或者用户名"));
				return;
			}
			Date dateTime = new Date();
			String token = username + password + dateTime.getTime();
			token = TokenUtils.getToken(token);
			response.setHeader("x-auth-token", token);
			output(response, userService.shiroLogin(session, username, password));
		} catch (Exception e) {
			e.printStackTrace();
			output(response, JsonUtil.buildObjectJson("4", "用户名名或密码错误"));
		}
	}

	/**
	 * 退出系统
	 * 
	 * @param request
	 * @param response
	 */
	@ResponseBody
	@RequestMapping(value = "/logout", method = RequestMethod.POST)
	public void logout(HttpSession session, HttpServletResponse response, HttpServletRequest request) {
		logger.info("logout()");
		try {
			InputStream is = request.getInputStream();
			String message = IOUtils.toString(is, "UTF-8");
			System.out.println("message:" + message);
			Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").serializeNulls().create();
			User User = gson.fromJson(message, User.class);
			User user = null;
			String username = User.getUsername();
			String password = User.getPassword();
		} catch (Exception e) {
			e.printStackTrace();
			output(response, JsonUtil.buildObjectJson("1", "退出异常"));
		}
		output(response, JsonUtil.buildObjectJson("0", "成功退出"));
	}

	/**
	 * 未授权
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/unauthorized", method = RequestMethod.POST)
	public void unauthorized(HttpServletRequest request, HttpServletResponse response) {
		logger.info("unauthorized");
		output(response, JsonUtil.buildObjectJson("1", "授权失败"));
	}

	// 编辑用户资料
	@ResponseBody
	@RequestMapping(value = "/editUser", method = RequestMethod.POST)
	public void editUser(HttpServletRequest request, HttpServletResponse response) {
		logger.info("editUser");
		try {
			InputStream is = request.getInputStream();
			String message = IOUtils.toString(is, "UTF-8");
			logger.info("message:" + message);
			Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").serializeNulls().create();
			User User = gson.fromJson(message, User.class);
			logger.info("User:" + User.getUsername() + "/" + User.getPassword());
			System.out.println("User:" + User.getUsername() + "/" + User.getName() + "/" + User.getSex());

			// 手机号码不能重
			if (null != User.getPhone() && !User.getPhone().isEmpty()) {
				User u = userService.getUserByPhone(User.getPhone());
				if (null != u) {
					output(response, JsonUtil.buildObjectJson("2", "手机号码已经存在"));
					return;
				}
			}
			// name不能重
			if (null != User.getName() && !User.getName().isEmpty()) {
				User u = userService.getUserByName(User.getName());
				if (null != u) {
					output(response, JsonUtil.buildObjectJson("3", "利宝号已经存在"));
					return;
				}
			}

			userService.updateUser(User);
			output(response, JsonUtil.buildObjectJson("0", "修改成功"));
		} catch (Exception e) {
			e.printStackTrace();
			output(response, JsonUtil.buildObjectJson("1", "修改失败"));
		}
	}

	/**
	 * 添加管理用户
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/addManageUser", method = RequestMethod.GET)
	public void addManageUser(HttpServletRequest request, HttpServletResponse response, String username,
			Integer officialAccountId, int roleId) {
		try {
			System.out.println("addManageUser() username:" + username + "|officialAccountId:" + officialAccountId);
			
			if(1==roleId) {
				output(response, JsonUtil.buildObjectJson("3", "不能增加创建者"));
				return;
			}
			
			User user=userService.getUserByUsername(username);
			if(null==user) {
				output(response, JsonUtil.buildObjectJson("4", "用户不存在"));
				return;
			}

			UserManageOaRole userManageOaRole = userManageOaRoleService.getManageUser(username, officialAccountId);
			if (null != userManageOaRole) {
				output(response, JsonUtil.buildObjectJson("2", "改管理员已经存在"));
				return;
			}

			userManageOaRole = new UserManageOaRole();
			userManageOaRole.setUsername(username);
			userManageOaRole.setOfficialAccountId(officialAccountId);
			userManageOaRole.setRoleId(roleId);
			userManageOaRole.setStatus(0);
			userManageOaRole.setCreateTime(new Date());
			userManageOaRole.setUpdateTime(new Date());
			userManageOaRoleService.addManageUser(userManageOaRole);
			output(response, JsonUtil.buildObjectJson("0", "添加成功"));
		} catch (Exception e) {
			e.printStackTrace();
			output(response, JsonUtil.buildObjectJson("1", "添加失败"));
		}
	}

	/**
	 * 修改管理用户
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/editManageUser", method = RequestMethod.GET)
	public void editManageUser(HttpServletRequest request, HttpServletResponse response, String username,
			Integer officialAccountId, Integer roleId, Integer status) {
		try {
			logger.info("editManageUser()");
			UserManageOaRole userManageOaRole = new UserManageOaRole();
			userManageOaRole.setUsername(username);
			userManageOaRole.setOfficialAccountId(officialAccountId);
			userManageOaRole.setRoleId(roleId);
			userManageOaRole.setStatus(status);
			userManageOaRole.setUpdateTime(new Date());
			userManageOaRoleService.updateManageUser(userManageOaRole);
			output(response, JsonUtil.buildObjectJson("0", "修改成功"));
		} catch (Exception e) {
			e.printStackTrace();
			output(response, JsonUtil.buildObjectJson("1", "修改失败"));
		}
	}

	/**
	 * 删除管理用户
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/delManageUser", method = RequestMethod.GET)
	public void delManageUser(HttpServletRequest request, HttpServletResponse response, String username,
			Integer officialAccountId) {
		try {
			logger.info("delManageUser()");
			UserManageOaRole userManageOaRole = userManageOaRoleService.getManageUser(username, officialAccountId);
			if (null != userManageOaRole) {
				int roleId = userManageOaRole.getRoleId();
				if (1 == roleId) {
					output(response, JsonUtil.buildObjectJson("2", "创建者不能删除"));
					return;
				}
			} else {
				output(response, JsonUtil.buildObjectJson("3", " 用户不存在"));
				return;
			}
			userManageOaRoleService.delManageUser(username, officialAccountId);
			output(response, JsonUtil.buildObjectJson("0", "删除成功"));
		} catch (Exception e) {
			e.printStackTrace();
			output(response, JsonUtil.buildObjectJson("1", "删除失败"));
		}
	}

	/**
	 * 搜索用戶
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/searchUser", method = RequestMethod.GET)
	public void searchUser(HttpServletRequest request, HttpServletResponse response, String keyword, Integer page,
			Integer limit) {
		try {
			logger.info("searchUserModel");
			page = (null == page || page < 0) ? 0 : page;
			limit = (null == limit || limit < 0) ? 10 : limit;
			List<User> list = userService.searchUserByKeyword(keyword, page, limit);
			output(response, JsonUtil.buildCustomJson("0", "success", list));
		} catch (Exception e) {
			e.printStackTrace();
			output(response, JsonUtil.buildObjectJson("1", "搜索失败"));
		}
	}

	/**
	 * 搜索管理员
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/searchManageUsers", method = RequestMethod.GET)
	public void searchManageUsers(HttpServletRequest request, HttpServletResponse response, String officialAccountId,
			Integer roleId, Integer page, Integer limit) {
		System.out.println("searchManageUsers():" + officialAccountId);
		try {
			if (null == officialAccountId) {
				output(response, JsonUtil.buildObjectJson("2", "公众号ID不能为空"));
				return;
			}
			page = (null == page || page < 0) ? 0 : page;
			limit = (null == limit || limit < 0) ? 10 : limit;
			Map<String, Object> param = new HashMap<String, Object>();
			param.put("officialAccountId", officialAccountId);
			param.put("roleId", roleId);
			param.put("page", page);
			param.put("limit", limit);
			System.out.println("searchManageUsers():" + limit);
			PageHelper.startPage(page, limit);
			Page<User> data = userService.searchManageUsers(param);
			int pageSize = data.getPageSize();
			long total = data.getTotal();
			List<User> list = data.getResult();
			System.out.println("searchManageUsers():" + list.size());
			output(response, JsonUtil.buildJsonForPage(list, total, pageSize));
		} catch (Exception e) {
			e.printStackTrace();
			output(response, JsonUtil.buildObjectJson("1", "搜索失败"));
		}
	}

	/**
	 * 发送短信验证码
	 * 
	 * @param number接收手机号码
	 */
	@RequestMapping(value = "/sendSms", method = RequestMethod.GET)
	@ResponseBody
	public void sendSms(HttpServletRequest request, HttpServletResponse response, String phone) {
		logger.info("sendSms():" + phone);
		try {
			// 生成6位验证码
			String verifyCode = String.valueOf(new Random().nextInt(899999) + 100000);
			logger.info("verifyCode:" + verifyCode);
			// 发送短信
//			String result = SmsSender.send(phone, verifyCode, 2);
//			JSONObject resultJson = JSONObject.parseObject(result);
//			logger.info("resultJson:" + resultJson);
//			if (!resultJson.getString("status").equals("3000001"))// 发送短信失败
//			{
//				output(response, JsonUtil.buildSuccessJson("2", "发送短信失败"));
//				return;
//			}
			String result=SmsUtil.sendSms(phone, verifyCode);
			if(!"0".equals(result)) {
				logger.error("send sms for reason:" + result);
				output(response, JsonUtil.buildSuccessJson(result, "发送短信失败"));
				return;	
			}
			
			JSONObject json = new JSONObject();
			json.put("verifyCode", verifyCode);
			json.put("createTime", System.currentTimeMillis());
			// 将认证码存入SESSION
			// HttpSession session = request.getSession();
			// request.getSession().setAttribute(phone, json);
			// 临时方案
			verifyCodeMap.put(phone, json);

			ExSms sms = new ExSms();
			sms.setCode(verifyCode);
			sms.setPhone(phone);
			sms.setUsed(0);
			sms.setCreateTime(new Date());
			exSmsService.addExSms(sms);// 入库保存
			String domain = ofPropertyService.getOfProperty("xmpp.domain").getPropValue();
			logger.info("domain:" + domain);
			// 测试环境返回短信
			if (!MYOPENFIRE.equals(domain)) {
				verifyCode = "";
			}
			output(response, JsonUtil.buildObjectJson(0, "success", verifyCode));
		} catch (Exception e) {
			e.printStackTrace();
			output(response, JsonUtil.buildSuccessJson("1", "发送短信异常"));
		}
	}

	/**
	 * 验证手机短信
	 */
	@RequestMapping(value = "/verifySMS", method = RequestMethod.GET)
	@ResponseBody
	public void verifySMS(HttpServletRequest request, HttpServletResponse response, String phone, String verifyCode) {
		logger.info("verifySMS():" + phone + "/" + verifyCode);
		System.out.println("verifySMS():" + phone + "/" + verifyCode);
		JSONObject json = verifyCodeMap.get(phone);
		logger.info("json:" + json);
		if (null == json || !json.getString("verifyCode").equals(verifyCode)) {
			output(response, JsonUtil.buildSuccessJson("1", "短信错误"));
		}
		if ((System.currentTimeMillis() - json.getLong("createTime")) > 1000 * 60 * 3) {
			output(response, JsonUtil.buildSuccessJson("2", "短信超时"));
		}
		ExSms sms = new ExSms();
		sms.setCode(verifyCode);
		sms.setUsed(1);
		exSmsService.updateExSms(sms);// 更新状态
		output(response, JsonUtil.buildSuccessJson("0", "success"));
	}

	/**
	 * 重置密码
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/resetUserPassword", method = RequestMethod.POST)
	public void resetUserPassword(HttpServletRequest request, HttpServletResponse response) {
		logger.info("resetUserPassword");
		try {
			InputStream is = request.getInputStream();
			String message = IOUtils.toString(is, "UTF-8");
			logger.info("message:" + message);
			Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").serializeNulls().create();
			User User = gson.fromJson(message, User.class);
			System.out.println("User:" + User.getUsername() + "/" + User.getPassword());

			String propValue = ofPropertyService.getOfProperty(passwordKey).getPropValue();
			String username = User.getUsername();
			AuthFactory.setPasswordKey(propValue);

			AuthFactory.setPassword(User.getUsername(), User.getPassword());

			String encryptedPassword = AuthFactory.getPassword(username);
			String storedKey = AuthFactory.getStoredKey(username);
			String serverKey = AuthFactory.getServerKey(username);
			String salt = AuthFactory.getSalt(username);

			User.setEncrypted(encryptedPassword);
			User.setStoredKey(storedKey);
			User.setServerKey(serverKey);
			User.setSalt(salt);
			System.out.println("encryptedPassword:" + encryptedPassword);
			System.out.println("storedKey:" + storedKey);
			System.out.println("serverKey:" + serverKey);
			System.out.println("salt:" + salt);

			userService.updateUser(User);
			output(response, JsonUtil.buildSuccessJson("0", "success"));
		} catch (Exception e) {
			e.printStackTrace();
			output(response, JsonUtil.buildObjectJson("401", "重置异常"));
		}
	}

	/**
	 * 通过手机获取账号
	 */
	@RequestMapping(value = "/getUsernameByPhone", method = RequestMethod.GET)
	@ResponseBody
	public void getUsernameByPhone(HttpServletRequest request, HttpServletResponse response, String phone) {
		logger.info("getUsernameByPhone():" + phone);
		// UserModel user = userModelService.getUsernameByPhone(phone);
		User user = userService.getUserByPhone(phone);
		if (null != user) {
			output(response, JsonUtil.buildObjectJson(0, "success", user.getUsername()));
		} else {
			output(response, JsonUtil.buildObjectJson(1, "没有找到用户", user));
		}
	}

	/**
	 * 通过手机获取用户
	 */
	@RequestMapping(value = "/getUserByPhone", method = RequestMethod.GET)
	@ResponseBody
	public void getUserByPhone(HttpServletRequest request, HttpServletResponse response, String phone) {
		logger.info("getUsernameByPhone():" + phone);
		User user = userService.getUserByPhone(phone);
		if (null != user) {
			output(response, JsonUtil.buildObjectJson(0, "success", user));
		} else {
			output(response, JsonUtil.buildObjectJson(1, "没有找到用户", user));
		}
	}

	/**
	 * 通过账号(username)获取用户
	 */
	@RequestMapping(value = "/getUserByUsername", method = RequestMethod.GET)
	@ResponseBody
	public void getUserByUsername(HttpServletRequest request, HttpServletResponse response, String account) {
		System.out.println("getUserByUsername():" + account);
		User user = userService.getUserByUsername(account);
		if (null != user) {
			user.setEncrypted("");
			user.setSalt("");
			user.setServerKey("");
			user.setStoredKey("");
			System.out.println("getUserByUsername() user:" + user.toString());
			output(response, JsonUtil.buildObjectJson(0, "success", user));
		} else {
			output(response, JsonUtil.buildObjectJson(1, "用户名不存在", user));
		}
	}

	/**
	 * 根据用户手机生成ID号，保证用户信息；
	 */
	@RequestMapping(value = "/generateUsernameByPhone", method = RequestMethod.GET)
	@ResponseBody
	public void generateUsernameByPhone(HttpServletRequest request, HttpServletResponse response, String phone) {
		logger.info("generateUsernameByPhone():" + phone);

		User user = userService.getUserByPhone(phone);
		if (null != user) {// 需要两个条件判断，注册用户表里面存在；
			output(response, JsonUtil.buildObjectJson(1, "手机号码已经注册", ""));
			return;
		} else {
			String account = UUID.randomUUID().toString().substring(24);
			output(response, JsonUtil.buildObjectJson(0, "手机号码可以注册", account));
		}
	}

	public static String parse(String xml, String node) {
		String value = "";
		try {
			Document document = (Document) DocumentHelper.parseText(xml);

			Element root = document.getRootElement();// 指向根节点 <root>
			try {
				Element mark = root.element(node);
				value = mark.getTextTrim();
			} catch (Exception e) {
				e.printStackTrace();
			}

		} catch (DocumentException e) {
			e.printStackTrace();
		}
		return value;
	}
}
