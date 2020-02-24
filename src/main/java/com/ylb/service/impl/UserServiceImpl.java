package com.ylb.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.apache.ibatis.annotations.Param;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.github.pagehelper.Page;
import com.ylb.dao.UserDao;
import com.ylb.entity.User;
import com.ylb.service.UserService;
import com.ylb.util.JsonUtil;

@Service
public class UserServiceImpl implements UserService {
	Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
	@Resource
	private UserDao userDao;

	@Override
	public List<User> getUserListByGroupId(String groupId, Integer page, Integer limit) {
		// TODO Auto-generated method stub
		return userDao.getUserListByGroupId(groupId, page, limit);
	}

	@Override
	public List<User> getFollowerListByOfficialAccountId(String officialAccountId, Integer page, Integer limit) {
		return userDao.getFollowerListByOfficialAccountId(officialAccountId, page, limit);
	}

	@Override
	public List<User> searchUserByKeyword(String keyword, Integer page, Integer limit) {
		return userDao.searchUserByKeyword(keyword, page, limit);
	}

	@Override
	public User getUserByPhone(String phone) {
		return userDao.getUserByPhone(phone);
	}

	@Override
	public User getUserByName(String name) {
		return userDao.getUserByName(name);
	}

	public User getUserByUsername(@Param("username") String username) {
		return userDao.getUserByUsername(username);
	}

	@Override
	public void addUser(User User) {
		userDao.addUser(User);
	}

	@Override
	public void updateUser(User User) {
		userDao.updateUser(User);
	}

	@Override
	public Page<User> searchOfficialAccountFollower(Map<String,Object> param) {
		return userDao.searchOfficialAccountFollower(param);
	}

	public String shiroLogin(HttpSession session, String username, String password) {
		logger.info("shiroLogin():" + username + "/" + password);
		int code = 1;
		String msg = "登录失败";
		if (username.equals("") || username == null) {
			msg = "账号不能为空";
		} else {
			if (password.equals("") || password == null) {
				msg = "密码不能为空";
			} else {
				UsernamePasswordToken usernamePasswordToken = new UsernamePasswordToken(username, password);
				logger.info("shiroLogin() usernamePasswordToken:" + usernamePasswordToken.toString());
				Subject subject = SecurityUtils.getSubject();
				logger.info("shiroLogin() subject:" + subject.toString());
				try {
					subject.login(usernamePasswordToken); // 完成登录
					User user = (User) subject.getPrincipal();
					// 获取用户最新登录时间
					// admin.setLastLoginTime(new Date());
					// adminDao.updateAdmin(admin);
					logger.info("shiroLogin() user:" + user.getUsername() + "/ login success");
					session.setAttribute("yinglibao_user", user);
					code = 0;
					msg = "登录成功";
					return JsonUtil.buildObjectJson(code, msg, user);
				} catch (Exception e) {
					e.printStackTrace();
					msg = "该账号无权限登录或登录失败";
				}
			}
		}
		return JsonUtil.buildObjectJson(code, msg, "");
	}

	public Page<User> searchManageUsers(Map<String, Object> param) {
		return userDao.searchManageUsers(param);
	}

}
