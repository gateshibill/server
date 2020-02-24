package com.ylb.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.apache.ibatis.annotations.Param;

import com.github.pagehelper.Page;
import com.ylb.entity.User;

public interface UserService {
	//public User getUserByUsername(@Param("account") String account);

	public List<User> getUserListByGroupId(@Param("groupId") String groupId, @Param("page") Integer page,
			@Param("limit") Integer limit);

	public List<User> getFollowerListByOfficialAccountId(@Param("officialAccountId") String officialAccountId,
			@Param("page") Integer page, @Param("limit") Integer limit);

	public List<User> searchUserByKeyword(@Param("keyword") String keyword, @Param("page") Integer page,
			@Param("limit") Integer limit);

	public User getUserByPhone(@Param("phone") String phone);

	public User getUserByName(@Param("name") String name);
	
	public User getUserByUsername(@Param("username") String username);

	public void addUser(User User);

	public void updateUser(User User);

	public Page<User> searchOfficialAccountFollower(Map<String,Object> param);
	
	public String shiroLogin(HttpSession session, String username, String password) ;
	
	public Page<User> searchManageUsers(Map<String, Object> param);
}
