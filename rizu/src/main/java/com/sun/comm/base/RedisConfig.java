package com.sun.comm.base;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;

/**
 * 配置文件，读取:WEB-INFO/conf/webapp.properties
 * Created by qxh on 2015/11/7.
 */
public class RedisConfig {

    static Properties props = null;
    static String filePath = null;

    static void init()
    {

        String dir = RedisConfig.class.getResource("").getPath().toString() ;
        //System.out.print("dir=" + dir);
        int pos = dir.indexOf("classes");
        dir = dir.substring(0,pos);
        RedisConfig.filePath = dir + "conf/redis.properties";
        //System.out.print("filePath=" + RedisConfig.filePath);
        if (RedisConfig.props == null) {
            RedisConfig.props = new Properties();
            InputStream in=null;
            try {
                in = new BufferedInputStream(new FileInputStream(RedisConfig.filePath));
                RedisConfig.props.load(in);
            }catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    //配置选项，读取:WEB-INFO/conf/webapp.properties
    public static String get(String key)
    {
        RedisConfig.init();
        return RedisConfig.props.getProperty(key).trim();
    }


}
