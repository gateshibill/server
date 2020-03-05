package com.ylb.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.github.pagehelper.Page;
import com.google.gson.Gson;
import com.ylb.dao.GroupDao;
import com.ylb.dao.MessageDao;
import com.ylb.dao.UserDao;
import com.ylb.entity.Group;
import com.ylb.entity.Message;
import com.ylb.entity.User;
import com.ylb.service.GroupService;
import com.ylb.util.JsonUtil;

@Service
public class GroupServiceImpl implements GroupService {
	@Resource
	private GroupDao groupDao;
	@Resource
	private MessageDao messageDao;
	@Resource
	private UserDao userDao;
	/**
	 * 获取群消息
	 */
	@Override
	public List<Message> getGroupMessageListBySender(String sender, Integer page, Integer limit) {
		return messageDao.getGroupMessageListBySender(sender, page, limit);
	}

	@Override
	public Page<User> getGroupAdminListByGroupId(Map<String, Object> param) {
		return userDao.getGroupAdminListByGroupId(param);
	}

	@Override
	public Page<User> getGroupMemberListByGroupId(Map<String, Object> param) {
		return userDao.getGroupMemberListByGroupId(param);
	}

	@Override
	public Page<Group> searchUserGroup(Map<String, Object> param) {
		return groupDao.searchUserGroup(param);
	}

	@Override
	public Page<Group> searchPublicGroup(Map<String, Object> param) {
		return groupDao.searchPublicGroup(param);
	}

	@Override
	public Group getGroupByName(String name) {
		return groupDao.getGroupByName(name);
	}
	
	public Page<Group> getGroupListByJid(Map<String, Object> param){
		return groupDao.getGroupListByJid(param);
	}
}
