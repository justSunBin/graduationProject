package com.sun.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Pattern;

public class StringUtils {

	private final static Pattern emailer = Pattern.compile("\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*");
	private final static SimpleDateFormat dateFormater = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	private final static SimpleDateFormat dateFormater2 = new SimpleDateFormat("yyyy-MM-dd");
	private final static Pattern Regex_Phone = Pattern.compile("^0{0,1}[0-9]{12}");
	private final static Pattern Regex_QQ = Pattern.compile("[1-9]{5,10}");
	private final static Pattern Regex_Mobile = Pattern.compile("[0-9]{11}");

	/**
	 * 将字符串转位日期类型
	 * 
	 * @param sdate
	 * @return
	 */
	public static Date toDate(String sdate) {
		try {
			return dateFormater.parse(sdate);
		} catch (ParseException e) {
			return null;
		}
	}

	/**
	 * 以友好的方式显示时间
	 * 
	 * @param sdate
	 * @return
	 */
	public static String friendly_time(String sdate) {
		Date time = toDate(sdate);
		if (time == null) {
			return "Unknown";
		}
		String ftime = "";
		Calendar cal = Calendar.getInstance();

		// 判断是否是同一天
		String curDate = dateFormater2.format(cal.getTime());
		String paramDate = dateFormater2.format(time);
		if (curDate.equals(paramDate)) {
			int hour = (int) ((cal.getTimeInMillis() - time.getTime()) / 3600000);
			if (hour == 0)
				ftime = Math.max(
						(cal.getTimeInMillis() - time.getTime()) / 60000, 1)
						+ "分钟前";
			else
				ftime = hour + "小时前";
			return ftime;
		}

		long lt = time.getTime() / 86400000;
		long ct = cal.getTimeInMillis() / 86400000;
		int days = (int) (ct - lt);
		if (days == 0) {
			int hour = (int) ((cal.getTimeInMillis() - time.getTime()) / 3600000);
			if (hour == 0)
				ftime = Math.max(
						(cal.getTimeInMillis() - time.getTime()) / 60000, 1)
						+ "分钟前";
			else
				ftime = hour + "小时前";
		} else if (days == 1) {
			ftime = "昨天";
		} else if (days == 2) {
			ftime = "前天";
		} else if (days > 2 && days <= 10) {
			ftime = days + "天前";
		} else if (days > 10) {
			ftime = dateFormater2.format(time);
		}
		return ftime;
	}

	/**
	 * 判断给定字符串时间是否为今日
	 * 
	 * @param sdate
	 * @return boolean
	 */
	public static boolean isToday(String sdate) {
		boolean b = false;
		Date time = toDate(sdate);
		Date today = new Date();
		if (time != null) {
			String nowDate = dateFormater2.format(today);
			String timeDate = dateFormater2.format(time);
			if (nowDate.equals(timeDate)) {
				b = true;
			}
		}
		return b;
	}

	/**
	 * 判断给定字符串是否空白串。 空白串是指由空格、制表符、回车符、换行符组成的字符串 若输入字符串为null或空字符串，返回true
	 * 
	 * @param input
	 * @return boolean
	 */
	public static boolean empty(String input) {
		if (input == null || "".equals(input))
			return true;
		for (int i = 0; i < input.length(); i++) {
			char c = input.charAt(i);
			if (c != ' ' && c != '\t' && c != '\r' && c != '\n') {
				return false;
			}
		}
		return true;
	}

	/**
	 * 判断给定字符串是否空白串
	 * @param string
	 * @return
	 */
	public static boolean isEmpty(String string){
		return string==null || string.length()==0?true:false;
	}
	/**
	 * 判断给定集合是否为空
	 * @param list
	 * @return
	 */
	public static boolean isEmpty(List<?> list){
		return list==null || list.size()==0?true:false;
	}
	
	public static boolean isEmpty(Object... obj) {
		return obj == null || obj.length == 0 ? true : false;
	}
	
	/**
	 * 判断是不是一个合法的电子邮件地址
	 * 
	 * @param email
	 * @return
	 */
	public static boolean isEmailer(String email) {
		if (email == null || email.trim().length() == 0)
			return false;
		return emailer.matcher(email).matches();
	}


	/**
	 * 判断是不是一个合法的手机号码
	 *
	 * @param mobile
	 * @return
	 */
	public static boolean isMobile(String mobile) {
		if (mobile == null || mobile.trim().length() != 11)
			return false;
		return Regex_Mobile.matcher(mobile).matches();
	}
	
	public static boolean isQQ(String qq) {
		if(qq==null || qq.trim().length()==0){
			return false;
		}
		return Regex_QQ.matcher(qq).matches();
	}
	
	
	public static boolean isPhone(String phone) {
		if(phone==null || phone.trim().length()==0){
			return false;
		}
		return Regex_Phone.matcher(phone).matches();
	}

	/**
	 * 字符串转整数
	 * 
	 * @param str
	 * @param defValue
	 * @return
	 */
	public static int toInt(String str, int defValue) {
		try {
			return Integer.parseInt(str);
		} catch (Exception e) {
		}
		return defValue;
	}

	/**
	 * 对象转整数
	 * 
	 * @param obj
	 * @return 转换异常返回 0
	 */
	public static int toInt(String obj) {
		if (obj == null)
			return 0;
		return toInt(obj, 0);
	}

	/**
	 * 对象转整数
	 * @param obj
	 * @return 转换异常返回 0
	 */
	public static long toLong(String obj) {
		try {
			return Long.parseLong(obj);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return 0;
	}
	/**
	 * 对象转浮点数
	 * @param obj
	 * @return 转换异常返回 0.0
	 */
	public static float toFloat(String obj) {
		try {
			return Float.parseFloat(obj);
		} catch (Exception e) {
			//System.out.println(e.getMessage());
		}
		return 0.0f;
	}
	
	/**
	 * 
	 * @Description String转Double
	 * @Date 2013-6-25
	 * @param str
	 * @return double 转换异常返回 0.0
	 */
	public static double toDouble(String str) {
		try {
			return Double.parseDouble(str);
		} catch (Exception e) {
		}
		return 0.0d;
	}
	
	/**
	 * 字符串转布尔值
	 * 
	 * @param b
	 * @return 转换异常返回 false
	 */
	public static boolean toBool(String b) {
		try {
			return Boolean.parseBoolean(b);
		} catch (Exception e) {
		}
		return false;
	}
	
	/**
	 * @Description 截取字符串
	 * @Date 2014-1-6
	 * @param str
	 * @param byteLength
	 * @return String
	 */
	public static String splitString(String str, int byteLength){
		if(str==null)  return null;
		byte[] data=null;
		String encoding=null;
		try {
			data=str.getBytes("UTF-8");
			encoding="UTF-8";
		} catch (Exception e) {
			data=str.getBytes();
		}
		if(data!=null){
			if(byteLength>data.length){
				return str;
			}
			String tempString=null;
			while(tempString==null
					|| tempString.charAt(tempString.length()-1)!=str.charAt(tempString.length()-1)){
				if(encoding!=null){
					try {
						tempString=new String(data, 0, byteLength, encoding);
					} catch (Exception e) {
					}
				}else{
					tempString=new String(data, 0, byteLength);
				}
				byteLength--;
				if(byteLength<0){
					break;
				}
			}
			return tempString;
		}
		return null;
	}
	
	/**
	 * @Description 按字节计算字数
	 * @Date 2014-1-6
	 * @param text
	 * @return int
	 */
	public static int captureTextCount(String text){
		if(text==null) return 0;
	    int len=text.length();
	    int count=0;
	    for (int i=0; i<len; i++) {
	        int c=text.charAt(i);
	        if (c>255) {
	            count+=2;
	        }else {
	            count+=1;
	        }
	    }
	    return count;
	}

	/**
	 * @Description 按字节计算字数
	 * @Date 2014-1-6
	 * @param text
	 * @param maxCount
	 * @return String
	 */
	public static String cutTextWithText(String text,int maxCount){
		if(text==null) return null;
	    int len=text.length();
	    int currCount=0;
	    int subStringIndex=0;
	    for (int i=0; i<len; i++) {
	        int c=text.charAt(i);
	        if (c>255) {
	            currCount+=2;
	        }else {
	            currCount+=1;
	        }
	        if (currCount>maxCount) {
	            break;
	        }
	        subStringIndex++;
	    }

	    return text.substring(0, subStringIndex);
	}


	/**
	 * 生成随机字符串
	 * @param len
	 * @return
	 */
	public static String rand_str(int len)
	{
		UUID uuid = UUID.randomUUID();
		String guid = uuid.toString().replace("-", "").toUpperCase();
		Random random = new Random(System. currentTimeMillis());
		int k = random.nextInt();

		guid = Md5Utils.md5(guid+k);

		guid = guid.substring(0, len-1);

		return guid;
	}

	public static String rand_number(int n)
	{
		Random random = new Random(System. currentTimeMillis());

		Double min_d = new Double(Math.pow(10, n-1));
		Double max_d = new Double(Math.pow(10, n));
		int min = min_d.intValue();
		int max = max_d.intValue();

		int v = random.nextInt(max)%(max-min+1) + min;
		Integer i = new Integer(v);
		return i.toString();
	}


}

