package com.ylb.service;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.github.pagehelper.Page;
import com.ylb.entity.Topic;

public interface TopicService {

	int addTopic(Topic topic);

	void updateTopic(Topic topic);

	Topic getTopicById(Integer id);
	
	List<Topic> getAllTopicList( @Param("page") Integer page,
			@Param("limit") Integer limit);

	/**
	 * 获取用户关注公众号的文章
	 * 
	 * @param topicId
	 * @param page
	 * @param limit
	 * @return
	 */
	List<Topic> getFollowOfficialAccountTopicListByUserId(@Param("userId") String username,
			@Param("page") Integer page, @Param("limit") Integer limit);
	
	Page<Topic>  searchOfficialAccountTopic(Map<String, Object> param);
}
