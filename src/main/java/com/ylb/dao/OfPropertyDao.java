package com.ylb.dao;

import org.apache.ibatis.annotations.Mapper;

import com.ylb.entity.OfProperty;

@Mapper
public interface OfPropertyDao {
	OfProperty getOfProperty(String name);
}
