package com.sun.comm.cache;

import com.sun.comm.base.AppConfig;
import com.sun.utils.JsonUtils;
import com.sun.utils.StringUtils;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.log4j.Logger;

import java.util.List;

/**
 * 缓存操作类，这个类是实际是个工厂类，可以支持各种CACHE服务器，目前仅仅支持redis
 * Created by qxh on 2015/11/9.
 */
public class CacheHandler {
    private IBaseCache cache = null;
    Logger logger = Logger.getLogger(this.getClass().getName());
    public CacheHandler() {

        //配置文件webapp.properties
        if (AppConfig.get("cache.type").equals("redis")) {
            cache = new RedisCache();
        }
    }

    /**
     * 设置缓存的值
     * @param key
     * @param val
     */
    public void set(String key,String val)
    {
        if (key == null || key.equals("") )  return ;
        cache.set(key, val);
    }

    public void set(String key,Object obj)
    {
        if (key == null || key.equals("") )  return ;
        if (obj==null )  return ;

        String json = JsonUtils.encode(obj);
        if (json == null) {
            return ;
        }
        set(key,json);
    }

    /**
     * 缓存数据
     * @param key
     * @param val
     * @param cacheTime
     */
    public void set(String key,String val,int cacheTime)
    {
        if (key == null || key.equals("") )  return ;
        cache.set(key,val,cacheTime);
    }

    /**
     * 缓存数据+缓存时间
     * @param key
     * @param obj
     * @param cacheTime
     */
    public void set(String key,Object obj,int cacheTime)
    {
        if (key == null || key.equals("") )  return ;
        if (obj==null )  return ;

        String json = JsonUtils.encode(obj);
        if (json == null) {
            return ;
        }
        cache.set(key,json,cacheTime);
    }

    /**
     * 获取值
     * @param key
     * @return
     */
    public String get(String key)
    {

        if (has(key) == false)    return null;
        return cache.get(key);
    }

    /**
     * 从缓存中使用key取到JSON字符串，然后转化成指定的class
     * @param key 缓存中的key
     * @param beanClass bean的class
     * @return
     */
    public Object getFromJson(String key,Class beanClass)
    {
        if (has(key) == false)
            return null;
        String json = get(key);
        if (json == null || json.equals("")) {
            return null;
        }

        try{

            JSONObject jsonObj = JsonUtils.decode(json);

            return JSONObject.toBean(jsonObj, beanClass);
        }catch (Exception e) {
            return null;
        }
    }

    /**
     * 从缓存中使用key取到JSON字符串，然后转化成指定的list
     * @param key 缓存中的key
     * @param beanClass bean的class
     * @return
     */
    public List listFromJson(String key,Class beanClass)
    {
        if (has(key) == false)    return null;

        String json = get(key);
        if (json == null || json.equals("")) {
            return null;
        }

        try{
            JSONArray jsonarr = JSONArray.fromObject(json);
            if (jsonarr == null) {
                return null;
            }

            return JSONArray.toList(jsonarr, beanClass);
        }catch (Exception e) {
            return null;
        }
    }

    /**
     * 获得int
     * @param key
     * @return
     */
    public int getInt(String key)
    {
        if (has(key) == false) {
            return 0;
        }

        return StringUtils.toInt(cache.get(key));
    }

    /**
     * 判断是否存在
     * @param key
     * @return
     */
    public boolean has(String key)
    {
        if (key == null || key.equals("") )
            return false;
        if (cache.get(key) == null) {
            return false;
        }

        return true;
    }

    /**
     * 删除缓存数据
     * @param key
     */
    public void del(String key)
    {
        if (key == null || key.equals("") )  return ;
        cache.del(key);
    }

}
