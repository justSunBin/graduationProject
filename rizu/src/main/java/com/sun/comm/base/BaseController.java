package com.sun.comm.base;

import com.sun.utils.StringUtils;
import org.apache.log4j.Logger;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.regex.Pattern;

/**
 * BaseDao,Dao需继承此Dao
 *
 * @author qxh since 2015-11-4 下午10:52:36
 */
public class BaseController
{
    @Resource
    protected HttpServletRequest req;

    @Resource
    protected HttpSession session;

    public Logger logger = Logger.getLogger(this.getClass().getName());



    /**
     * 获取参数
     * @param key
     * @return
     */
    public String getReq(String key)
    {
        try {
            return  req.getParameter(key);
        }catch (Exception e) {
            return "";
        }
    }

    /**
     * 获取参数
     * @param key 字段
     * @param defVal 默认值
     * @return
     */
    public String getReq(String key,String defVal)
    {
        try {
            String val = req.getParameter(key);

            if (val == null || val.equals("")) {
                return defVal;
            }
            return val;
        }catch (Exception e) {
            return defVal;
        }
    }

    /**
     * 获取安全参数，避免SQL注入
     * @param key 字段
     * @param defVal 默认值
     * @return
     */
    public String getSafeReq(String key,String defVal)
    {
        try {
            String val = getReq(key,defVal);

            if (validSql(val) == false) { //SQL检查，防SQL注入
                return defVal;
            }

            return val;
        }catch (Exception e) {
            return defVal;
        }
    }

    /**
     * 参数校验，防止SQL注入
     *
     * @param str
     */
    public boolean validSql(String str) {

        String reg = "(?:')|(?:--)|(/\\*(?:.|[\\n\\r])*?\\*/)|"
                + "(\\b(select|update|and|or|delete|insert|trancate|char|into|substr|ascii|declare|exec|count|master|into|drop|execute)\\b)";

        Pattern sqlPattern = Pattern.compile(reg, Pattern.CASE_INSENSITIVE);

        if (sqlPattern.matcher(str).find()) {
            return false;
        }
        return true;
    }

    /**
     * 获取参数
     * @param key 字段
     * @param defVal 默认值
     * @return
     */
    public int getReq(String key,int defVal)
    {
        try {
            int val = StringUtils.toInt(req.getParameter(key));

            if (val == 0) {
                return defVal;
            }

            return val;
        }catch (Exception e) {
            return defVal;
        }
    }

    /**
     * 获取参数
     * @param key 字段
     * @param defVal 默认值
     * @return
     */
    public long getReq(String key,long defVal)
    {
        try {
            long val = StringUtils.toLong(req.getParameter(key));

            if (val == 0) {
                return defVal;
            }

            return val;
        }catch (Exception e) {
            return defVal;
        }

    }

    /**
     * 获取参数
     * @param key 字段
     * @param defVal 默认值
     * @return
     */
    public float getReq(String key,float defVal)
    {
        try {
            float val = StringUtils.toFloat(req.getParameter(key));

            if (val == 0) {
                return defVal;
            }

            return val;
        }catch (Exception e) {
            return defVal;
        }
    }

    /**
     * 获取参数
     * @param key 字段
     * @param defVal 默认值
     * @return
     */
    public double getReq(String key,double defVal)
    {
        try {
            float val = StringUtils.toFloat(req.getParameter(key));

            if (val == 0) {
                return defVal;
            }

            return val;
        }catch (Exception e) {
            return defVal;
        }

    }
}
