package com.sun.comm.base;

import com.sun.utils.StringUtils;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;

/**
 * 配置文件，读取:WEB-INFO/conf/webapp.properties
 * Created by qxh on 2015/11/7.
 */
public class AppConfig {

    private static Properties props = null;
    private static String filePath = null;
    private static boolean isInit = false;

    static void init()
    {
        if (AppConfig.isInit == true && AppConfig.props != null && AppConfig.filePath!=null && "".equals(AppConfig.filePath) != false) {
            return ;
        }

        String dir = AppConfig.class.getResource("").getPath().toString() ;
        //System.out.println("dir=" + dir);
        int pos = dir.indexOf("classes");
        dir = dir.substring(0,pos);
        AppConfig.filePath = dir + "conf/webapp.properties";
        //System.out.print("filePath=" + AppConfig.filePath);
        if (AppConfig.props == null) {
            AppConfig.props = new Properties();
            InputStream in=null;
            try {
                in = new BufferedInputStream(new FileInputStream(AppConfig.filePath));
                AppConfig.props.load(in);

                AppConfig.isInit = true;
            }catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    //配置选项，读取:WEB-INFO/conf/webapp.properties
    public static String get(String key)
    {
        AppConfig.init();
        String str = AppConfig.props.getProperty(key);
        if (str == null) {
            return "";
        }
        return str.trim();
    }


    //获得网站WEB的URL
    public static String getWwwUrl()
    {
        AppConfig.init();
        return AppConfig.get("application.debug").equals("true") ?
                AppConfig.get("application.debug.wwwurl") : AppConfig.get("application.product.wwwurl");
    }

    /**
     * 获得接口URL
     * @return
     */
    public static String getOpenUrl()
    {
        return AppConfig.get("application.debug").equals("true") ?
                AppConfig.get("application.debug.openurl") : AppConfig.get("application.product.openurl");
    }

    /**
     * 获得图片URL
     * @return
     */
    public static String getPhotoUrl()
    {

        return AppConfig.get("application.debug").equals("true") ?
                AppConfig.get("application.debug.photourl") : AppConfig.get("application.product.photourl");
    }

    public static int getInt(String key)
    {
        String str = AppConfig.get(key);
        if ( str == null || str.equals("")) {
            return  0;
        }
        return StringUtils.toInt(str);
    }

    //是否开启调试模式
    public static boolean debug()
    {
        if (AppConfig.get("application.debug").equals("true")) {
            return true;
        }
        return false;
    }

    public static String getPhotoRealDir()
    {
        if (AppConfig.debug() == true) {
            return AppConfig.get("application.debug.attachdir");
        }
        return AppConfig.get("application.product.attachdir");
    }


}
