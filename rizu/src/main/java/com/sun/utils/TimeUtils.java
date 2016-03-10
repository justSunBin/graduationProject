package com.sun.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 时间换算工具类
 */
public class TimeUtils {

    // 长日期格式
    public static String TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";

	/**
	 * 获得当前UNIX时间戳（自1970-01-01 到现在的秒数）
	 * @return
	 */
	public static long time()
	{
		long t =  System.currentTimeMillis()/1000; //当前UNIX时间戳
		return t;
	}

	/**
	 * 获得当前时间，格式：yyyy-MM-dd HH:mm:ss
	 * @return
	 */
	public static String date()
	{
		SimpleDateFormat dateFormater = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date =new Date();
		return dateFormater.format(date);
	}

	/**
	 * 格式化当前时间，格式：yyyy-MM-dd HH:mm:ss
	 * @return
	 */
	public static String date(String format)
	{
		if (format=="")
			format="yyyy-MM-dd HH:mm:ss";
		SimpleDateFormat dateFormater = new SimpleDateFormat(format);
		Date date =new Date();
		return dateFormater.format(date);
	}

	/**
	 * 格式化时间，格式：yyyy-MM-dd HH:mm:ss
	 * @param format 格式化字符串
	 * @param unix_time 数字化的UNIX时间戳
	 * @return
	 */
	public static String date(String format,long unix_time)
	{
		SimpleDateFormat dateFormater = new SimpleDateFormat(format);
		Date date =new Date(unix_time*1000); //转化成微秒
		return dateFormater.format(date);
	}

	/**
	 * 获得当前时间，格式：yyyy-MM-dd HH:mm:ss
	 * @return
	 */
	public static String dateIng()
	{
		SimpleDateFormat dateFormater =new SimpleDateFormat("yyyyMMddHHmmss");
		Date date =new Date();
		return dateFormater.format(date);
	}

	/**
	 * 获得时间字符串，格式：yyyyMMddHHmmss
	 * @return
	 */
	public static String date(String format,String strtime)
	{
		if (format == null || strtime == null || format.equals("") || strtime.equals("")) {
			return "";
		}

		SimpleDateFormat dateFormater = new SimpleDateFormat(format);
		Date date =new Date(TimeUtils.strtotime(strtime));
		return dateFormater.format(date);
	}

	/**
	 * 转化时间
	 * @param strtime
	 * @return
	 */
	public static long strtotime(String strtime)
	{
        try {
            SimpleDateFormat format = new SimpleDateFormat(TIME_FORMAT);
            return format.parse(strtime).getTime();
        }catch (ParseException e) {
            e.printStackTrace();
        }

        return 0;
	}
	public static String longdatetostr(long big,long small){
		if (big-small<0)
			return "00:00:00";
		long shijiancha=big-small;//时间差
		String hour;
		String min;
		String second;

		if (shijiancha/1000/60/60<10){hour="0"+shijiancha/1000/60/60;}else{hour=new Long(shijiancha/1000/60/60).toString();}
		if (shijiancha/1000/60%60<10){min="0"+shijiancha/1000/60%60;}else{min=new Long(shijiancha/1000/60%60).toString();}
		if (shijiancha/1000%60<10){second="0"+shijiancha/1000%60;}else{second=new Long(shijiancha/1000%60).toString();}

		return hour+":"+min+":"+second;
	}
	public static String shortDate(){
		SimpleDateFormat dateFormater =new SimpleDateFormat("yyyy-MM-dd");
		Date date =new Date();
		return dateFormater.format(date);
	}
}

