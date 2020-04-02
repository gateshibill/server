package com.ylb.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.github.pagehelper.Page;
import com.ylb.entity.Group;
import com.ylb.entity.Message;
import com.ylb.entity.Ofmucaffiliation;

@Mapper
public interface OfmucaffiliationDao {
	public void modifyAdminGroupNickname(Ofmucaffiliation ofmucaffiliation);
}
