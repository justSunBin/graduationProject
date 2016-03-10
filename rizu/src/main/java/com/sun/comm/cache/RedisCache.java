package com.sun.comm.cache;

import com.sun.comm.base.RedisConfig;
import com.sun.utils.StringUtils;
import org.apache.log4j.Logger;
import redis.clients.jedis.Jedis;

/**
 * Created by qxh on 2015/11/9.
 */
public class RedisCache implements IBaseCache{

    private Logger logger = Logger.getLogger(this.getClass().getName());
    //操作redis客户端
    private Jedis jedis = null;

    public Jedis getJedis() {
        if(jedis == null){
            jedis = new Jedis(RedisConfig.get("redis.host"), StringUtils.toInt(RedisConfig.get("redis.port")));
            jedis.auth(RedisConfig.get("redis.pass"));
        }

        return jedis;
    }

    public RedisCache() {
        if(jedis == null){
            jedis = new Jedis(RedisConfig.get("redis.host"), StringUtils.toInt(RedisConfig.get("redis.port")));
        }
    }

    public void set(String key,String val,int cacheTime)
    {
        try {
            getJedis().set(key, val);
            getJedis().expire(key, cacheTime);
        }catch (Exception e) {
            jedis.close();
            jedis = null;
        }
    }

    public void set(String key,String val)
    {
        try {
            getJedis().set(key, val);
        }catch (Exception e) {
            jedis.close();
            jedis = null;
        }
    }

    public void del(String key)
    {
        try {
            getJedis().del(key);
        }catch (Exception e) {
            jedis.close();
            jedis = null;
        }
    }

    public String get(String key)
    {

        try {

            String value = getJedis().get(key);
            logger.warn("key="+key+",val="+value);

            if (value.equals("OK")) {
                jedis.close();
                jedis = null;
                value = getJedis().get(key);
            }

            return value;
        }catch (Exception e) {
            jedis.close();
            jedis = null;
            return "";
        }
    }
}
