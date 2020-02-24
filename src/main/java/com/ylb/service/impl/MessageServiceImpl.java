package com.ylb.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.Page;
import com.ylb.dao.MessageDao;
import com.ylb.entity.Message;
import com.ylb.service.MessageService;

@Service
public class MessageServiceImpl implements MessageService {
	@Autowired
	private MessageDao messageDao;

	public Page<Message> searchMessageList(Map param) {
		return messageDao.searchMessageList(param);
	}

}
