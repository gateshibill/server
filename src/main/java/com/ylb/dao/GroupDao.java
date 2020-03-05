package com.ylb.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.github.pagehelper.Page;
import com.ylb.entity.Group;
import com.ylb.entity.Message;

@Mapper
public interface GroupDao {
	public void updateGroup(Group group);


	public List<Message> getGroupMessageListBySender(@Param("sender") String sender, @Param("page") Integer page,
			@Param("limit") Integer limit);

	public Page<Group> searchUserGroup(Map<String, Object> param);

	public Page<Group> searchPublicGroup(Map<String, Object> param);
	
	public Group getGroupByName(@Param("name") String name);
	
	public Page<Group> getGroupListByJid(Map<String, Object> param);

}
