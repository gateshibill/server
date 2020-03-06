package com.ylb.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.google.gson.Gson;
import com.ylb.dao.GroupDao;
import com.ylb.dao.MessageDao;
import com.ylb.dao.UserDao;
import com.ylb.entity.Group;
import com.ylb.entity.Message;
import com.ylb.entity.User;
import com.ylb.service.GroupService;
import com.ylb.util.Contants;
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
	
	public Page<User> getGroupOwnerListByGroupId(Map<String,Object> param){
		return userDao.getGroupOwnerListByGroupId(param);
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
		Group group= groupDao.getGroupByName(name);
		if(null!=group) {
			Map<String, Object> param = new HashMap<String, Object>();
			param.put("groupId", name);
			PageHelper.startPage(0, 100);
			Page<User> data = userDao.getGroupAdminListByGroupId(param);
			List<User> admins = data.getResult();
		
			Map<String, Object> param1 = new HashMap<String, Object>();
			param1.put("groupId", name);
			PageHelper.startPage(0, 1000);
			Page<User> data1 = userDao.getGroupMemberListByGroupId(param1);
			List<User> members = data1.getResult();

			//临时
			group.setAdmins(admins);
			group.setMembers(members);
			group.setMaxUsers(Contants.DEFALT_GROUP_MEMBER_NUM);
		}
		return group;
	}
	
	@Override
	public Group getGroupDetailByName(String name) {
		Group group= groupDao.getGroupByName(name);
		if(null!=group) {
			Map<String, Object> param = new HashMap<String, Object>();
			param.put("groupId", name);
			PageHelper.startPage(0, 100);
			Page<User> data = userDao.getGroupAdminListByGroupId(param);
			List<User> admins = data.getResult();
		
			Map<String, Object> param1 = new HashMap<String, Object>();
			param1.put("groupId", name);
			PageHelper.startPage(0, 1000);
			Page<User> data1 = userDao.getGroupMemberListByGroupId(param1);
			List<User> members = data1.getResult();
			
			Map<String, Object> param2 = new HashMap<String, Object>();
			param2.put("groupId", name);
			PageHelper.startPage(0, 1000);
			Page<User> data2 = userDao.getGroupOwnerListByGroupId(param1);
			List<User> owners = data2.getResult();

			//临时
			group.setAdmins(admins);
			group.setMembers(members);
			group.setOwners(owners);
			group.setMaxUsers(Contants.DEFALT_GROUP_MEMBER_NUM);
		}
		return group;
	}
	
	public Page<Group> getGroupListByJid(Map<String, Object> param){
		//Group group= groupDao.getGroupListByJid(param);
		Page<Group> list= new Page();
		Page<Group> data1 = groupDao.getGroupListByJid(param);
		//List<Group> members = data1.getResult();	
		System.out.println("data1=" +data1.size() );
		Page<Group> data2 = groupDao.searchUserGroup(param);
		//List<Group> admins = data2.getResult();
		System.out.println("data2=" +data2.size() );
		list.addAll(data1);
		list.addAll(data2);
		list.setTotal(list.size());
		return list;
	}
}
