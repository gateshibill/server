package com.ylb.service;


public interface HistoricalBillService {
	public String selectHobList(Integer userId,Integer page,Integer limit);	//对历史账单进行查询
}
