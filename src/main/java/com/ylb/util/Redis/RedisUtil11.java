package com.ylb.util.Redis;

import java.util.List;

import redis.clients.jedis.Jedis;

public class RedisUtil11 {
	private static  Jedis jedis = new Jedis("173.248.241.149", 6379); //公网

	public static List<String> lrange(Integer dbIndex,String key,long start,long stop){
		jedis.auth("sjy168999");
		jedis.select(dbIndex);
		return jedis.lrange(key, start, stop);
	}
}
