package com.ylb.util.Redis;

import java.util.ArrayList;
import java.util.List;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * Redis ： 159.138.10.104
 * @author wxh
 *
 */
public class RedisUtil1 {
	 //Redis服务器IP
    private static String HOST = "159.138.10.104";  //公网

    //private static String HOST = "192.168.1.147"; //私网
    
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
    private static int REDIS_KEY_TIME_OUT = 60*60*12; 
    
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

	/*** <p>Description: 得到值</p>
	* @author wenquan
	* @date  2017年1月5日
	* @param key
	*/
	public static String get(String key){  
	    String value = null;  
	    Jedis jedis = null;
	    try {
	        jedis= getJedis();
	        value = jedis.get(key); 
	    } catch(Exception e){
	        if(jedis != null){
	            jedis.close();
	        }
	        e.printStackTrace();
	    } finally {
	        returnResource(pool, jedis);  
	    }
	    return value;  
	}  

	/*** <p>Description: 设置键值</p>
	* @author wenquan
	* @date  2017年1月5日
	* @param key value
	*/
	public static String set(String key,String value){
	     Jedis jedis = null;
	     String ans = null;
	     try {  
	         jedis = getJedis();
	         if(!exists(key)) {
		        	setexpire(key,REDIS_KEY_TIME_OUT);//设置过去时间
		     }
	         ans = jedis.set(key,value);  
	     } catch (Exception e) {  
	         //释放redis对象  
	         if(jedis != null){
	             jedis.close();
	         }
	         e.printStackTrace();  
	     } finally {  
	         //返还到连接池  
	         returnResource(pool, jedis);  
	     }  
	     return ans;
	}


	/*** <p>Description: 设置键值 并同时设置有效期</p>
	* @author wenquan
	* @date  2017年1月5日
	* @param key seconds秒数 value
	*/
	public static String setex(String key,int seconds,String value){
	     Jedis jedis = null;
	     String ans = null;
	     try {  

	         String.valueOf(100);
	         jedis = getJedis(); 
	         if(!exists(key)) {
		        	setexpire(key,REDIS_KEY_TIME_OUT);//设置过去时间
		     }
	         ans = jedis.setex(key,seconds,value);  
	     } catch (Exception e) {  
	         if(jedis != null){
	            jedis.close();
	         }
	        e.printStackTrace();  
	     } finally {  
	         //返还到连接池  
	         returnResource(pool, jedis);  
	     }  
	     return ans;
	}

	/*** <p>Description: </p>
	 * <p>通过key 和offset 从指定的位置开始将原先value替换</p>
	 * <p>下标从0开始,offset表示从offset下标开始替换</p>
	 * <p>如果替换的字符串长度过小则会这样</p>
	 * <p>example:</p>
	 * <p>value : bigsea@zto.cn</p>
	 * <p>str : abc </p>
	 * <P>从下标7开始替换  则结果为</p>
	 * <p>RES : bigsea.abc.cn</p>
	* @author wenquan
	* @date  2017年1月6日
	* @param 
	* @return 返回替换后  value 的长度
	*/
	public static Long setrange(String key,String str,int offset){
	     Jedis jedis = null;  

	     try {  
	         jedis = getJedis();  
	         if(!exists(key)) {
		        	setexpire(key,REDIS_KEY_TIME_OUT);//设置过去时间
		     }
	         return jedis.setrange(key, offset, str);  
	     } catch (Exception e) {  
	        if(jedis != null){
	            jedis.close();
	        }
	        e.printStackTrace();  
	        return 0L;
	     } finally {  
	         //返还到连接池  
	         returnResource(pool, jedis);  
	     }
	}

	/*** <p>Description: 通过批量的key获取批量的value</p>
	* @author wenquan
	* @date  2017年1月6日
	* @param 
	* @return 成功返回value的集合, 失败返回null的集合 ,异常返回空
	*/
	public static List<String> mget(String...keys){
	    Jedis jedis = null;  
	    List<String> values = null;
	    try {  
	        jedis = getJedis();  
	        values = jedis.mget(keys);  
	    } catch (Exception e) {  
	    if(jedis != null){
	            jedis.close();
	        }
	        e.printStackTrace();  
	    } finally {  
	        //返还到连接池  
	        returnResource(pool, jedis);  
	    }
	    return values;
	}

	/*** <p>Description: 批量的设置key:value,可以一个</p>
	 * <p>obj.mset(new String[]{"key2","value1","key2","value2"})</p>
	* @author wenquan
	* @date  2017年1月6日
	* @param 
	* @return
	*/
	public static String mset(String...keysvalues){
	    Jedis jedis = null;
	    String ans = null;
	    try{
	        jedis = getJedis();
	        ans = jedis.mset(keysvalues);
	    }catch (Exception e) {  
	        if(jedis != null){
	            jedis.close();
	        }
	        e.printStackTrace();  
	    } finally {  
	        //返还到连接池  
	        returnResource(pool, jedis);  
	    }
	    return ans;
	}

	/*** <p>Description: 通过key向指定的value值追加值</p>
	* @author wenquan
	* @date  2017年1月6日
	* @param key str 追加字符串
	*/
	public static Long append(String key,String str){
	     Jedis jedis = null;  
	     try {  
	         jedis = getJedis();  
	         return jedis.append(key, str);  
	     } catch (Exception e) {  
	        if(jedis != null){
	            jedis.close();
	        }
	        e.printStackTrace();  
	        return 0L;
	     } finally {  
	         //返还到连接池  
	         returnResource(pool, jedis);  
	     }  
	}

	/*** <p>Description: 判断key是否存在</p>
	* @author wenquan
	* @date  2017年1月6日
	* @param 
	*/
	public static Boolean exists(String key){
	     Jedis jedis = null;  
	     try {  
	         jedis = getJedis();  
	         return jedis.exists(key);  
	     } catch (Exception e) {  
	         if(jedis != null){
	             jedis.close();
	         }
	         e.printStackTrace();  
	         return false;
	     } finally {  
	        //返还到连接池  
	         returnResource(pool, jedis);  
	     }  
	}

	/*** <p>设置key value,如果key已经存在则返回0,nx==> not exist</p>
	* @author wenquan
	* @date  2017年1月6日
	* @param 
	* @return 成功返回1 如果存在 和 发生异常 返回 0
	*/
	public static Long setnx(String key,String value){
	 Jedis jedis = null;  
	    try {  
	        jedis = getJedis();  
	        if(!exists(key)) {
	        	setexpire(key,REDIS_KEY_TIME_OUT);//设置过去时间
	        }
	        return jedis.setnx(key,value);  
	    } catch (Exception e) {  
	        if(jedis != null){
	             jedis.close();
	        }
	        e.printStackTrace();  
	        return 0l;
	    } finally {  
	        //返还到连接池  
	        returnResource(pool, jedis);  
	    }  
	}
	/**
	 * 将一个或多个值插入到列表头部
	 * @param key
	 * @param value
	 * @return
	 */
	public static Long lpush(String key,String value){
		 Jedis jedis = null;  
		    try {  
		        jedis = getJedis();  
		        if(!exists(key)) {
		        	setexpire(key,REDIS_KEY_TIME_OUT);//设置过去时间
		        }
		        return jedis.lpush(key,value);  
		    } catch (Exception e) {  
		        if(jedis != null){
		             jedis.close();
		        }
		        e.printStackTrace();  
		        return 0l;
		    } finally {  
		        //返还到连接池  
		        returnResource(pool, jedis);  
		    }  
	}
	/**
	 * 将一个或多个值插入到列表头部
	 * @param key
	 * @param value
	 * @return
	 */
	public static Long rpush(String key,String value){
		 Jedis jedis = null;  
		    try {  
		        jedis = getJedis();  
		        if(!exists(key)) {
		        	setexpire(key,REDIS_KEY_TIME_OUT);//设置过去时间
		        }
		        return jedis.rpush(key,value);  
		    } catch (Exception e) {  
		        if(jedis != null){
		             jedis.close();
		        }
		        e.printStackTrace();  
		        return 0l;
		    } finally {  
		        //返还到连接池  
		        returnResource(pool, jedis);  
		    }  
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
	public static List<String> lrange(String key,long start,long stop){
		 Jedis jedis = null;
		 List<String> lists = new ArrayList<String>();
		    try{
		    	System.out.println("con start time:"+System.currentTimeMillis());
		        jedis = getJedis();	
		    	System.out.println("con end time:"+System.currentTimeMillis());

		        lists = jedis.lrange(key, start, stop);
		    	System.out.println("deal end time:"+System.currentTimeMillis());

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
	/**
	 * 获取执行index     ------------------获取详情【最新一条】--------------------------
	 * @param key
	 * @param index : -1:最后一条  0 ：第一条
	 * @return
	 */
	public static String lindex(String key,long index) {
		Jedis jedis = null;
	    String ans = null;
	    try{
	        jedis = getJedis();
	        ans = jedis.lindex(key, index);
	    }catch (Exception e) {  
	        if(jedis != null){
	            jedis.close();
	        }
	        e.printStackTrace();  
	    } finally {  
	        //返还到连接池  
	        returnResource(pool, jedis);  
	    }
	    return ans;
	}
	/**
	 * 设置键过期时间
	 * @param key
	 * @param seconds
	 * @return
	 */
	public static Long setexpire(String key,int seconds){
		 Jedis jedis = null;  
		    try {  
		        jedis = getJedis();  
		        return jedis.expire(key, seconds);
		    } catch (Exception e) {  
		        if(jedis != null){
		             jedis.close();
		        }
		        e.printStackTrace();  
		        return 0l;
		    } finally {  
		        //返还到连接池  
		        returnResource(pool, jedis);  
		    }  
	}
	/**
	 * 获取键值长度 [list]
	 * @param key
	 * @return
	 */
	public static Long llen(String key){
		 Jedis jedis = null;  
		    try {  
		        jedis = getJedis();  
		        return jedis.llen(key);
		    } catch (Exception e) {  
		        if(jedis != null){
		             jedis.close();
		        }
		        e.printStackTrace();  
		        return 0l;
		    } finally {  
		        //返还到连接池  
		        returnResource(pool, jedis);  
		    }  
	}
}
