package com.ylb.util.Redis;

import java.util.ArrayList;
import java.util.List;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * 存储周k /  月k
 * Redis3 : 159.138.2.160
 * @author wxh
 *
 */
public class RedisUtil4 {
	 //Redis服务器IP
    private static String HOST = "173.248.241.149";
    
    //Redis的端口号
    private static int PORT = 6379;
    
    //访问密码,若你的redis服务器没有设置密码，就不需要用密码去连接
    private static String AUTH = "sjy168999";
    
    //可用连接实例的最大数目，默认值为8；
    private static int MAX_TOTAL = 512;
    
    //控制一个pool最多有多少个状态为idle(空闲的)的jedis实例，默认值也是8。
    private static int MAX_IDLE = 50;
    
    //等待可用连接的最大时间，单位毫秒，默认值为-1，表示永不超时。
    private static long MAX_WAIT = 10000;
    
    private static int TIMEOUT = 600000;
    
    //在borrow一个jedis实例时，是否提前进行validate操作；如果为true，则得到的jedis实例均是可用的；
    private static boolean TEST_ON_BORROW = true;
    
   //Redis键值超时时间 :12小时
    //private static int REDIS_KEY_TIME_OUT = 60*60*12; 
    
    private static int REDIS_KEY_TIME_OUT = -1; //永不过期
    
	private static JedisPool pool = null;


	/*** <p>Description: 获取jedispool 连接池 </p>
	* @author wenquan
	* @date  2017年1月5日
	* @param 
	*/
	private static JedisPool getPool() {  
	    if (pool == null) {  
	       JedisPoolConfig config = new JedisPoolConfig();
	        //控制一个pool可分配多少个jedis实例，通过pool.getResource()来获取；  
	       //如果赋值为-1，则表示不限制；如果pool已经分配了maxActive个jedis实例，则此时pool的状态为exhausted(耗尽)。 
	       config.setMaxTotal(MAX_TOTAL);
	       //控制一个pool最多有多少个状态为idle(空闲的)的jedis实例。  
	       config.setMaxIdle(MAX_IDLE);
	        //表示当borrow(引入)一个jedis实例时，最大的等待时间，如果超过等待时间，则直接抛出JedisConnectionException；  
	       config.setMaxWaitMillis(MAX_WAIT);
	       //在borrow一个jedis实例时，是否提前进行validate操作；如果为true，则得到的jedis实例均是可用的；  
	       config.setTestOnBorrow(TEST_ON_BORROW);
	       pool = new JedisPool(config, HOST, PORT, TIMEOUT,AUTH);
	    }  
	    return pool;  
	}  

	/*** <p>Description: 返回资源 </p>
	* @author wenquan
	* @date  2017年1月5日
	* @param 
	*/
	public static void returnResource(JedisPool pool, Jedis jedis) {
	    if (jedis != null) {  
	         jedis.close();
	    }  
	}  



	/*** <p>Description: 获取jedis 实例</p>
	* @author wenquan
	* @date  2017年1月5日
	* @param 
	*/
	public static Jedis getJedis() throws Exception {  
	    Jedis jedis = null; 
	    pool = getPool();  
	    jedis = pool.getResource(); 
	    return jedis;
	}  

	/**
	 * 获取数据 : 返回列表中指定区间内的元素，区间以偏移量 START 和 END 指定。 其中 0 表示列表的第一个元素， 1 表示列表的第二个元素，以此类推。 你也可以使用负数下标，以 -1 表示列表的最后一个元素， -2 表示列表的倒数第二个元素，以此类推
	 * @param key
	 * @param start   0
	 * @param stop   -1
	 * @return
     *   ------------------获取证券代码集合------------------
     *
	 */
	public static List<String> lrange(Integer dbIndex,String key,long start,long stop){
		 Jedis jedis = null;
		 List<String> lists = new ArrayList<String>();
		    try{
		        jedis = getJedis();
		        jedis.select(dbIndex); //选择区域
		        lists = jedis.lrange(key, start, stop);
		    }catch (Exception e) {  
		        if(jedis != null){
		            jedis.close();
		        }
		        e.printStackTrace();  
		    } finally {  
		        //返还到连接池  
		        returnResource(pool, jedis);  
		    }
		    return lists;
	}
}
