package com.ylb.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.ylb.dao.BulletinDao;
import com.ylb.entity.Bulletin;
import com.ylb.service.BulletinService;

@Service
public class BulletinServiceImpl implements BulletinService {
	@Resource
	private BulletinDao bulletinDao;

	@Override
	public void addBulletin(Bulletin bulletin) {
		bulletinDao.addBulletin(bulletin);
	}

	@Override
	public Bulletin getLatestBulletinByGroupId(String groupId, Integer page, Integer limit) {
		Bulletin bulletin = null;
		List<Bulletin> list = bulletinDao.getBulletinListByGroupId(groupId, page, limit);
		if (null == list || list.size() == 0) {
			System.out.println(groupId + " bulletion is null");
			return null;
		}
		bulletin = list.get(list.size()-1);

		return bulletin;
	}
}
