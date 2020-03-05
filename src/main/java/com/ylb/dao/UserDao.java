package com.ylb.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.github.pagehelper.Page;
import com.ylb.entity.User;

@Mapper
public interface UserDao {
	public User getUserByAccount(@Param("username") String username);

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

	public Page<User> searchManageUsers(Map<String, Object> param);
	
	public Page<User> getGroupAdminListByGroupId(Map<String, Object> param);

	public Page<User> getGroupMemberListByGroupId(Map<String, Object> param);

}
