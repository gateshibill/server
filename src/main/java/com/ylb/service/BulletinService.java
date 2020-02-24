package com.ylb.service;

import org.apache.ibatis.annotations.Param;

import com.ylb.entity.Bulletin;

public interface BulletinService {
	public void addBulletin(Bulletin bulletin);
	public Bulletin getLatestBulletinByGroupId(@Param("groupId") String groupId,@Param("page")Integer page,@Param("limit")Integer limit);
}
