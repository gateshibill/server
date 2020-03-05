package com.ylb.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

import com.github.pagehelper.Page;
import com.ylb.dao.OfficialAccountDao;
import com.ylb.dao.OfficialAccountFollowerDao;
import com.ylb.dao.TopicDao;
import com.ylb.dao.UserDao;
import com.ylb.entity.OfficialAccount;
import com.ylb.entity.OfficialAccountFollower;
import com.ylb.entity.OfficialAccountLevel;
import com.ylb.entity.User;
import com.ylb.service.OfficialAccountService;

@Service
public class OfficialAccountServiceImpl implements OfficialAccountService {
	@Resource
	private OfficialAccountDao officialAccountDao;
	@Resource
	private OfficialAccountFollowerDao officialAccountFollowerDao;
	@Resource
	private UserDao userOfDao;
	@Resource
	private TopicDao topicDao;

	@Override
	public int addOfficialAccount(OfficialAccount officialAccount) {
		return officialAccountDao.addOfficialAccount(officialAccount);
	}

	@Override
	public void updateOfficialAccount(OfficialAccount officialAccount) {
		officialAccountDao.updateOfficialAccount(officialAccount);
	}

	@Override
	public OfficialAccount getOfficialAccountById(Integer id) {
		return officialAccountDao.getOfficialAccountById(id);
	}

	@Override
	public List<OfficialAccount> getUserOfficialAccountListByUserId(String userId) {
		List<OfficialAccount> listTmp = officialAccountDao.getUserOfficialAccountListByUserId(userId);
		List<OfficialAccount> list = new ArrayList<OfficialAccount>();
		for (OfficialAccount oa : listTmp) {
			OfficialAccount officialAccount = officialAccountDao.getOfficialAccountById(oa.getId());
			OfficialAccountLevel officialAccountLevel= new OfficialAccountLevel();
			officialAccount.setOfficialAccountLevel(officialAccountLevel);
			list.add(officialAccount);
		}
		return list;
	}

	@Override
	public List<OfficialAccount> searchOfficialAccountListByKeyword(@Param("keyword") String keyword,
			@Param("page") Integer page, @Param("limit") Integer limit) {
		return officialAccountDao.searchOfficialAccountListByKeyword(keyword, page, limit);
	}

	@Override
	public List<OfficialAccount> getFollowOfficialAccountListByUserId(String userId, Integer page, Integer limit) {
		return officialAccountDao.getFollowOfficialAccountListByUserId(userId, page, limit);
	}

	@Override
	public List<User> getFollowerListByOfficialAccountId(String officialAccountId, Integer page, Integer limit) {
		return userOfDao.getFollowerListByOfficialAccountId(officialAccountId, page, limit);
	}

	/**
	 * 获取公众号列表
	 * 
	 * @param page
	 * @param limit
	 * @return
	 */
	public List<OfficialAccount> getAllOfficialAccountList(@Param("page") Integer page, @Param("limit") Integer limit) {

		return officialAccountDao.getAllOfficialAccountList(page, limit);
	}

	@Override
	public void followOfficialAccount(Integer officialAccountId, String userId, int status) {
		System.out.println("server followOfficialAccount() " + officialAccountId + "/" + userId);
		OfficialAccountFollower oaf = new OfficialAccountFollower(officialAccountId, userId, status);
		List<OfficialAccountFollower> list = officialAccountFollowerDao.getOfficialAccountFollower(oaf);
		System.out.println("1 followOfficialAccount() " + officialAccountId + "/" + userId);
		// 临时
		if (null == list || list.size() == 0) {
			officialAccountFollowerDao.addOfficialAccountFollower(oaf);
		} else {
			officialAccountFollowerDao.updateOfficialAccountFollower(oaf);
		}
		System.out.println("2 followOfficialAccount() " + officialAccountId + "/" + userId);
	}

	@Override
	public Page<OfficialAccount> getManageUserOfficialAccountList(Map<String, Object> param) {
		return officialAccountDao.getManageUserOfficialAccountList(param);
	}
}
