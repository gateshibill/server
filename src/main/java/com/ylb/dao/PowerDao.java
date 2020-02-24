package com.ylb.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.ylb.entity.Power;

@Mapper
public interface PowerDao {
	/**
	 * 查询权限
	 * @param power
	 * @param page
	 * @param limit
	 * @return
	 */
	List<Power> getPowerList(@Param("power")Power power,@Param("page")Integer page,@Param("limit")Integer limit);
}
