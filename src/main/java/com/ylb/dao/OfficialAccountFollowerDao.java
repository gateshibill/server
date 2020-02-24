package com.ylb.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.ylb.entity.OfficialAccountFollower;

@Mapper
public interface OfficialAccountFollowerDao {

	void addOfficialAccountFollower(OfficialAccountFollower officialAccountFollower);

    void updateOfficialAccountFollower(OfficialAccountFollower officialAccountFollower);
	
	List<OfficialAccountFollower> getOfficialAccountFollower(OfficialAccountFollower oaf);
	
}
