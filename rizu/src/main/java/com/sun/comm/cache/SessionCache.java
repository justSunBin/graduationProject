package com.sun.comm.cache;

import com.sun.rizu.constant.CacheConstant;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * 这个类是用来模拟session的一个类，目的：支持APP客户端没有COOKIE的情况，
 * 先判断客户端是否传过来sid,否则使用SESSION产生sid，数据保存到redis服务器中，所以扩展CacheHandler
 * Created by qxh on 2015/11/9.
 */
public class SessionCache extends CacheHandler {

    protected HttpServletRequest req = null;

    protected HttpSession session;

    protected int cacheTime = 30*60; //30分钟

    private String sid = "";

    public String getSid()
    {
        try {
            req = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
            sid = req.getParameter("sid");
        }catch (Exception e) {
            e.printStackTrace();
        }

        if (sid == null || sid.equals("") == true) {
            session = req.getSession();
            sid = session.getId();
        }

        return sid;
    }
    /**
     * 给key加上前缀，格式:SID_KEY_[sid]_[key],如:SID_KEY_96EF441A56DBB1ADEF77FC4428BFA051_cd
     * 先判断客户端是否传过来sid,否则使用SESSION产生sid
     * @param key
     * @return
     */
    public String k(String key)
    {
        String sid = getSid();
        return CacheConstant.SID_KEY_PREFIX + "_" +  sid + "_" + key;
    }

    /**
     * 把值保存到缓存服务器上
     * @param key
     * @param val
     */
    public void set(String key,String val)
    {
        String skey = k(key);
        logger.trace("set:key=" + skey + ",val=" + val);
        super.set(skey,val,cacheTime);
    }

    /**
     * 把值保存到缓存服务器上
     * @param key
     * @param val
     */
    public void set(String key,Object val)
    {
        String skey = k(key);
        super.set(skey,val,cacheTime);
    }


    /**
     * 数据从redis缓存服务器上读取
     * @param key
     * @return
     */
    public String get(String key)
    {
        return super.get(k(key));
    }
}
