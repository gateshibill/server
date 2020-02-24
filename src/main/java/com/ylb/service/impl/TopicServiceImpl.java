package com.ylb.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.github.pagehelper.Page;
import com.ylb.dao.TopicDao;
import com.ylb.entity.Topic;
import com.ylb.service.TopicService;

@Service
public class TopicServiceImpl implements TopicService {

	@Resource
	private TopicDao topicDao;

	@Override
	public int addTopic(Topic topic) {
		return topicDao.addTopic(topic);

	}

	@Override
	public void updateTopic(Topic topic) {
		topicDao.updateTopic(topic);

	}

	@Override
	public Topic getTopicById(Integer id) {
		// TODO Auto-generated method stub
		return topicDao.getTopicById(id);
	}

	@Override
	public List<Topic> getAllTopicList(Integer page, Integer limit) {
		// TODO Auto-generated method stub
		return topicDao.getAllTopicList(page, limit);
	}

	@Override
	public List<Topic> getFollowOfficialAccountTopicListByUserId(String userId, Integer page, Integer limit) {
		return topicDao.getFollowOfficialAccountTopicListByUserId(userId, page, limit);
	}

	@Override
	public Page<Topic> searchOfficialAccountTopic(Map<String, Object> param) {
		return topicDao.searchOfficialAccountTopic(param);
	};
}
