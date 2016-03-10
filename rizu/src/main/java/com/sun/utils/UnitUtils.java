package com.sun.utils;

/**
 * 单位换算工具类
 * Created by qxh on 2015/11/12.
 */
public class UnitUtils {


    /**
     * 单位换算,算法:num/per + 单位，并转换成字符串
     * @param num 被除数
     * @param per 除数
     * @param u 换成的单位
     * @return
     */
    static public String unit(double num,double per,String u)
    {
        double ret = num / per ;

        String s =  String.format("%.2f",ret) + u;
        return s;
    }
}
