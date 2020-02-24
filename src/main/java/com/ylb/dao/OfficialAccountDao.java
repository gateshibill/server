package com.ylb.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.github.pagehelper.Page;
import com.ylb.entity.OfficialAccount;
import com.ylb.entity.Topic;

import java.util.List;
import java.util.Map;

@Mapper
public interface OfficialAccountDao {

	int addOfficialAccount(OfficialAccount officialAccount);

	void updateOfficialAccount(OfficialAccount officialAccount);

	OfficialAccount getOfficialAccountById(Integer id);

	List<OfficialAccount> getUserOfficialAccountListByUserId(String userId);

	List<OfficialAccount> searchOfficialAccountListByKeyword(@Param("keyword") String keyword,
			@Param("page") Integer page, @Param("limit") Integer limit);

	List<OfficialAccount> getAllOfficialAccountList(@Param("page") Integer page, @Param("limit") Integer limit);

	/**
	 * 获取用户关注的公众号
	 * 
	 * @param officialAccountId
	 * @param page
	 * @param limit
	 * @return
	 */
	List<OfficialAccount> getFollowOfficialAccountListByUserId(@Param("userId") String officialAccountId,
			@Param("page") Integer page, @Param("limit") Integer limit);

	/**
	 * 获取用户关注公众号的文章
	 * 
	 * @param officialAccountId
	 * @param page
	 * @param limit
	 * @return
	 */
	List<Topic> getFollowOfficialAccountTopicListByUserId(@Param("userId") String officialAccountId,
			@Param("page") Integer page, @Param("limit") Integer limit);

	Page<OfficialAccount> getManageUserOfficialAccountList(Map<String, Object> param);

}
