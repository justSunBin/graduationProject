package com.sun.comm.base;

import net.sf.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * BaseDao,Dao需继承此Dao
 *
 * @author qxh since 2015-11-4 下午10:52:36
 */
public class BaseJsonController extends BaseController
{
    /**
     * 返回错误的MAP，此时字段r=0
     * @param msg
     * @return
     */
    public Map<String,Object> rt(String msg)
    {
        HashMap<String,Object> m = new HashMap<String, Object>();
        m.put("r",0);
        m.put("msg",msg);

        return m;
    }

    /**
     * 返回自定义CODE的MAP，此时字段r,msg从参数传入
     * @param r 错误代码
     * @param msg 错误信息
     * @return
     */
    public Map<String,Object> rr(int r,String msg)
    {
        HashMap<String,Object> m = new HashMap<String, Object>();
        m.put("r",r);
        m.put("msg",msg);

        return m;
    }


    /**
     * 返回正确提示的的MAP，此时字段r=1
     * @param m
     * @return
     */
    public Map<String,Object> rr(Map<String,Object> m)
    {
        if (m == null) {
            m = new HashMap<String, Object>();
        }
        m.put("r",1);
        m.put("msg","");

        return m;
    }

    /**
     * 返回正确提示的的MAP，此时字段r=1
     * @param m
     * @return
     */
    public Map<String,Object> rr(Map<String,Object> m, String msg)
    {
        if (m == null) {
            m = new HashMap<String, Object>();
        }
        m.put("r",1);
        m.put("msg",msg);

        return m;
    }

    /**
     * 返回正确提示的的MAP，此时字段r=1,rows=[],total=0
     * @param msg
     * @return
     */
    public Map<String,Object> rl(String msg)
    {

        Map<String, Object>    m = new HashMap<String, Object>();

        m.put("rows","");
        m.put("total",0);
        m.put("r",1);
        m.put("msg","");

        return m;
    }


    /**
     * 读取远程POST/GET发送过来的JSon字符串
     * @return
     */
    public String readJsonString()
    {
        StringBuffer json = new StringBuffer();
        String line = null;
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new InputStreamReader(req.getInputStream(), "utf-8"));//读取字节流
            while((line = reader.readLine()) != null) {
                json.append(line);
            }

        } catch(IOException e) {
            e.printStackTrace();

        }finally {
            try {
                if (reader != null) {
                    reader.close();
                }
            }catch (Exception e) {
                e.printStackTrace();
            }
        }

        return json.toString();
    }

    /**
     * 读取远程POST/GET发送过来的字符串,然后转成MAP
     * @return
     */
    public Map<String,Object> readJsonToMap()
    {
        String json = readJsonString();
        if (json.equals("")) {
            return null;
        }

        Map<String,Object> m = new HashMap<String, Object>();
        try {
            JSONObject jsonObj = JSONObject.fromObject(json);
            Iterator it = jsonObj.keys();
            // 遍历jsonObject数据，添加到Map对象
            while (it.hasNext()) {
                String key = String.valueOf(it.next());
                Object val =  jsonObj.get(key);
                m.put(key, val);
            }
            return m;
        }catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }
}
