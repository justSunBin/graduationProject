package com.sun.comm.cache;

/**
 * Created by Administrator on 2015/11/9.
 */
public interface IBaseCache {

    public void set(String key, String val);

    public void set(String key, String val, int cacheTime);

    public void del(String key);

    public String get(String key);


}
