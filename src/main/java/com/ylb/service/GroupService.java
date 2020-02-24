package com.ylb.service;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.github.pagehelper.Page;
import com.ylb.entity.Group;
import com.ylb.entity.Message;
import com.ylb.entity.User;

public interface GroupService {

	public Page<User> getGroupAdminListByGroupId(Map<String,Object> param);

	public Page<User> getGroupMemberListByGroupId(Map<String,Object> param);

	public List<Message> getGroupMessageListBySender(@Param("sender") String sender, @Param("page") Integer page,
			@Param("limit") Integer limit);
	
	public Page<Group> searchUserGroup(Map<String,Object> param);
	
	public Page<Group> searchPublicGroup(Map<String, Object> param);

	public Group getGroupByName(@Param("name") String name);
}
