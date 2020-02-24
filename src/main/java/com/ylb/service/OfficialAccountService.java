package com.ylb.service;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.github.pagehelper.Page;
import com.ylb.entity.OfficialAccount;
import com.ylb.entity.User;

public interface OfficialAccountService {

	int addOfficialAccount(OfficialAccount officialAccount);

	void updateOfficialAccount(OfficialAccount officialAccount);

	OfficialAccount getOfficialAccountById(Integer id);

	List<OfficialAccount> getUserOfficialAccountListByUserId(String userId);

	List<OfficialAccount> searchOfficialAccountListByKeyword(@Param("keyword") String keyword,
			@Param("page") Integer page, @Param("limit") Integer limit);

	/**
	 * 获取用户关注的公众号
	 * 
	 * @param officialAccountId
	 * @param page
	 * @param limit
	 * @return
	 */
	List<OfficialAccount> getFollowOfficialAccountListByUserId(@Param("userId") String userId,
			@Param("page") Integer page, @Param("limit") Integer limit);

	/**
	 * 获取公众号粉丝
	 * 
	 * @param officialAccountId
	 * @param page
	 * @param limit
	 * @return
	 */
	List<User> getFollowerListByOfficialAccountId(@Param("officialAccountId") String officialAccountId,
			@Param("page") Integer page, @Param("limit") Integer limit);


	/**
	 * 获取公众号列表
	 * 
	 * @param page
	 * @param limit
	 * @return
	 */
	List<OfficialAccount> getAllOfficialAccountList(@Param("page") Integer page, @Param("limit") Integer limit);

	/**
	 * 关注公众号
	 * 
	 * @param page
	 * @param limit
	 * @return
	 */
	void followOfficialAccount(Integer officialAccountId, String userId,int status);
	
	Page<OfficialAccount> getManageUserOfficialAccountList(Map<String, Object> param);

}
