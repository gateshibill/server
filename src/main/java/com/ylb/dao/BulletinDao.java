package com.ylb.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.ylb.entity.Bulletin;

import java.util.List;

@Mapper
public interface BulletinDao {
	Integer addBulletin(Bulletin bulletin);
	List<Bulletin> getBulletinListByGroupId(@Param("groupJid") String groupId,@Param("page")Integer page,@Param("limit")Integer limit);
}
