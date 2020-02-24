package com.ylb.service;

import java.util.Map;

import com.github.pagehelper.Page;
import com.ylb.entity.Message;

public interface MessageService {
	
	Page<Message> searchMessageList(Map<String,Object> param);

}
