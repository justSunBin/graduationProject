package com.sun.utils;


import net.sf.json.JSONArray;
import net.sf.json.JSONException;
import net.sf.json.JSONObject;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;


/**
 * JSON操作工具类
 * Created by qxh on 2015/11/3.
 */
public class JsonUtils {

    /**
     * 字符串转json对象
     *
     * @param str
     * @param split
     * @return
     */
    public static JSONObject decode(String str, String split)
    {
        JSONObject json = new JSONObject();
        try {
            String[] arrStr = str.split(split);
            for (int i = 0; i < arrStr.length; i++) {
                String[] arrKeyValue = arrStr[i].split("=");
                json.put(arrKeyValue[0], arrStr[i].substring(arrKeyValue[0].length() + 1));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return json;
    }

    /**
     * 字符串转对象
     * @param str
     * @return
     */
    public static JSONObject decode(String str)
    {
        try {
            return JSONObject.fromObject(str);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return new JSONObject();
    }

    /**
     *
     * @param obj
     * @return
     */
    public static String encode(Object obj)
    {
        try {
            if (obj instanceof ArrayList) {
                JSONArray jsonarr = JSONArray.fromObject(obj);
                return jsonarr.toString();
            }else{
                JSONObject jsonobj = JSONObject.fromObject(obj);
                return jsonobj.toString();
            }
        }catch (Exception e) {
            e.printStackTrace();
        }


        return "";
    }

    /**
     * 将bean转换成键值对列表(可以http请求发送)
     *
     * @param bean
     * @return
     */
    public static List<NameValuePair> bean2Parameters(Object bean)
    {
        if (bean == null) {
            return null;
        }
        List<NameValuePair> parameters = new ArrayList<NameValuePair>();

        // 取得bean所有public 方法
        Method[] Methods = bean.getClass().getMethods();
        for (Method method : Methods)
        {
            if (method != null && method.getName().startsWith("get")
                    && !method.getName().startsWith("getClass")) {
                // 得到属性的类名
                String value = "";
                // 得到属性值
                try {
                    String className = method.getReturnType().getSimpleName();
                    if (className.equalsIgnoreCase("int")) {
                        int val = 0;
                        try {
                            val = (Integer) method.invoke(bean);
                        } catch (InvocationTargetException e) {
                            e.printStackTrace();
                        }
                        value = String.valueOf(val);
                    } else if (className.equalsIgnoreCase("String"))  {
                        try {
                            value = (String) method.invoke(bean);
                        } catch (InvocationTargetException e) {
                           e.printStackTrace();
                        }
                    }
                    if (value != null && value != "") {
                        // 添加参数
                        // 将方法名称转化为id，去除get，将方法首字母改为小写
                        String param = method.getName().replaceFirst("get", "");
                        if (param.length() > 0) {
                            String first = String.valueOf(param.charAt(0))
                                    .toLowerCase();
                            param = first + param.substring(1);
                        }
                        parameters.add(new BasicNameValuePair(param, value));
                    }
                } catch (IllegalArgumentException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
        return parameters;
    }


    /**
     * 将json格式的字符串解析成Map对象 <li>
     * json格式：{"name":"admin","retries":"3fff","testname"
     * :"ddd","testretries":"fffffffff"}
     */
    public static Map<String, Object> jsonToMap(Object object)
    {
        Map<String, Object> data = new HashMap<String, Object>();
        // 将json字符串转换成jsonObject
        JSONObject jsonObject = JSONObject.fromObject(object);
        Iterator it = jsonObject.keys();
        // 遍历jsonObject数据，添加到Map对象
        while (it.hasNext())
        {
            String key = String.valueOf(it.next());
            Object value = jsonObject.get(key);
            data.put(key, value);
        }
        return data;
    }


}
