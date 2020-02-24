package com.ylb.dao;

import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.github.pagehelper.Page;
import com.ylb.entity.Message;

@Mapper
public interface MessageDao {
	Page<Message> getGroupMessageListBySender(@Param("sender") String sender, @Param("page") Integer page,
			@Param("limit") Integer limit);

	Page<Message> searchMessageList(Map param);
}
