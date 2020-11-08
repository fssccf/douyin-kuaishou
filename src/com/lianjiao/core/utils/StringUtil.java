/**
* 文件名：StringUtil.java
* 创建日期：
* Copyright (c)  西安西安乐食
* All rights reserved.
*/
package com.lianjiao.core.utils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.text.TextUtils;
import android.widget.EditText;
/**
 * 
 * 类名称：StringUtil   
 * 类描述：文字处理工具类  
 * @version 1.00
 * @创建者：zhangyao
 * @修改者：韩创    
 * @创建时间：2014年5月30日   
 * @变更记录：2014年5月30日上午11:00:37 by 记录变更人
 */
public class StringUtil {
	
	private final static ThreadLocal<SimpleDateFormat> dateFormater = new ThreadLocal<SimpleDateFormat>() {
		@Override
		protected SimpleDateFormat initialValue() {
			return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		}
	};

	private final static ThreadLocal<SimpleDateFormat> dateFormater2 = new ThreadLocal<SimpleDateFormat>() {
		@Override
		protected SimpleDateFormat initialValue() {
			return new SimpleDateFormat("yyyy-MM-dd");
		}
	};
	/**
	 * 方法名：convert
	 * 描述：将Unicode编码转换为汉字 
	 * @参数：   参数名  参数类型   参数描述
	 * @返回值类型： String   
	 * @创建时间：  2015年9月6日 
	 * @创建者：韩创
	 * @变更记录：2015年9月6日下午2:27:31 by
	 */
	public static String convert(String ori) {
		char aChar;
		int len = ori.length();
		StringBuffer outBuffer = new StringBuffer(len);
		for (int x = 0; x < len;) {
			aChar = ori.charAt(x++);
			if (aChar == '\\') {
				aChar = ori.charAt(x++);
				if (aChar == 'u') {
					// Read the xxxx
					int value = 0;
					for (int i = 0; i < 4; i++) {
						aChar = ori.charAt(x++);
						switch (aChar) {
						case '0':
						case '1':
						case '2':
						case '3':
						case '4':
						case '5':
						case '6':
						case '7':
						case '8':
						case '9':
							value = (value << 4) + aChar - '0';
							break;
						case 'a':
						case 'b':
						case 'c':
						case 'd':
						case 'e':
						case 'f':
							value = (value << 4) + 10 + aChar - 'a';
							break;
						case 'A':
						case 'B':
						case 'C':
						case 'D':
						case 'E':
						case 'F':
							value = (value << 4) + 10 + aChar - 'A';
							break;
						default:
							throw new IllegalArgumentException(
									"Malformed   \\uxxxx   encoding.");
						}
					}
					outBuffer.append((char) value);
				} else {
					if (aChar == 't')
						aChar = '\t';
					else if (aChar == 'r')
						aChar = '\r';
					else if (aChar == 'n')
						aChar = '\n';
					else if (aChar == 'f')
						aChar = '\f';
					outBuffer.append(aChar);
				}
			} else
				outBuffer.append(aChar);

		}
		return outBuffer.toString();
	}
	
	/**
	 * 判断是否是用户Id (正整数)
	 * @param id
	 * @return
	 */
	public static boolean isUserServiceId(String id){
		
		if(TextUtils.isEmpty(id)){
			return false;
		}		
		Pattern p = Pattern
				.compile("^(0|[1-9][0-9]*)$");
		Matcher m = p.matcher(id);
		return m.matches();
	}
	

	/**
	 * 判断是否是电话号码
	 * @param mobiles
	 * @return
	 */
	public static boolean isMobliePhoneNum(String mobiles){
		if(TextUtils.isEmpty(mobiles)){
			return false;
		}
		
		if(mobiles.length() !=11){
			return false;
		}
		
		//Pattern p = Pattern.compile("^((13[0-9])|(15[^4,\\D])|(18[0,0-9]))\\d{8}$");
		Pattern p = Pattern.compile("^(1)\\d{10}$");
		
		Matcher m = p.matcher(mobiles);
		return m.matches();
	}
	
	public static String getSafeMobileNum(String mobiles){
		if(!isMobliePhoneNum(mobiles)){
			return null;
		}		
		return mobiles.substring(0, 3) + "*****" +mobiles.substring(8, mobiles.length());
	}
	
	/**
	 * 方法名：isUserNameorPassword
	 * 描述：判断是否是合法用户名（6~20数字字母及下划线组成）
	 * @参数：   参数名  参数类型   参数描述
	 * @返回值类型： boolean   
	 * @创建时间：  2014年6月23日 
	 * @创建者：韩创
	 * @变更记录：2014年6月23日下午5:29:01 by
	 */
	public static boolean isUserNameOrPasswd(String usermane){
		
		for (int i = 0; i < usermane.length(); i++)
        {
            int chr1 = (char) usermane.charAt(i);
            if(chr1>19968){
            	return false;
            }
        }
		
		Pattern p = Pattern.compile("^\\w{6,20}$");
		Matcher m = p.matcher(usermane);
		return m.matches();
	}
	/**
	 * 方法名：isAllNumber
	 * 描述：检测是否为纯数字
	 * @参数：   参数名  参数类型   参数描述
	 * @返回值类型： boolean   
	 * @创建时间：  2014年11月26日 
	 * @创建者：韩创
	 * @变更记录：2014年11月26日下午1:44:07 by
	 */
	public static boolean isAllNumber(String str){
		if(isEmpty(str)){
			return false;
		}
		String reg = "^\\d+$";
		return str.matches(reg);
	}
	
	/**
	 * 判断邮箱地址是否合法
	 * @param email
	 * @return
	 */
	public static boolean isEmailAdress(String email){
		
		if(TextUtils.isEmpty(email)){
			return false;
		}	
		
		if(email.startsWith("_") || email.contains("_@")){
			return false;
		}
		
		Pattern p = Pattern
				.compile("[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" +
			            "\\@" +
			            "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" +
			            "(" +
			                "\\." +
			                "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" +
			            ")+");
		Matcher m = p.matcher(email);
		return m.matches();
	}
	
	/**
	 * 去除字符串外围的<p>标签
	 * @param str
	 * @return
	 */
	public static String clearHtmlTab(String str){
		if(!TextUtils.isEmpty(str)){
		
			if(str.contains("<p") && str.contains("</p>")){
				str = str.substring(str.indexOf(">") + 1);	
				str = str.substring(0,str.lastIndexOf("</") );	
			}
			
		}		
		
		return str;
	}
	
	/**
	 * 判断给定字符串是否空白串。
	 * 空白串是指由空格、制表符、回车符、换行符组成的字符串
	 * 若输入字符串为null或空字符串，返回true
	 * @param input
	 * @return boolean
	 */
	public static boolean isEmpty( String input ) 
	{
		if ( input == null || "".equals( input ) || "null".equals(input.toLowerCase().trim()))
			return true;
		
		for ( int i = 0; i < input.length(); i++ ) 
		{
			char c = input.charAt( i );
			if ( c != ' ' && c != '\t' && c != '\r' && c != '\n' )
			{
				return false;
			}
		}
		return true;
	}
	/**
	 * 方法名：isEmpty
	 * 描述：验证输入框是否为空
	 * @参数：   参数名  参数类型   参数描述
	 * @返回值类型： boolean   
	 * @创建时间：  2015年9月7日 
	 * @创建者：韩创
	 * @变更记录：2015年9月7日上午11:40:06 by
	 */
	public static boolean isEmpty(EditText input){
		if(input == null){
			return true;
		}
		if(input.getText()!=null){
			return isEmpty(input.getText().toString());
		}else{
			return true;
		}
	}
	
	/**
	 * 以友好的方式显示时间
	 * @param sdate
	 * @return
	 */
	public static String friendly_time(String sdate) {
		if(StringUtil.isEmpty(sdate)){
			return "";
		}
		Date time = toDate(sdate);
		if(time == null) {
			return "Unknown";
		}
		String ftime = "";
		Calendar cal = Calendar.getInstance();
		
		//判断是否是同一天
		String curDate = dateFormater2.get().format(cal.getTime());
		String paramDate = dateFormater2.get().format(time);
		if(curDate.equals(paramDate)){
			int hour = (int)((cal.getTimeInMillis() - time.getTime())/3600000);
			if(hour == 0)
				ftime = Math.max((cal.getTimeInMillis() - time.getTime()) / 60000,1)+"分钟前";
			else 
				ftime = hour+"小时前";
			return ftime;
		}
		
		long lt = time.getTime()/86400000;
		long ct = cal.getTimeInMillis()/86400000;
		int days = (int)(ct - lt);		
		if(days == 0){
			int hour = (int)((cal.getTimeInMillis() - time.getTime())/3600000);
			if(hour == 0)
				ftime = Math.max((cal.getTimeInMillis() - time.getTime()) / 60000,1)+"分钟前";
			else 
				ftime = hour+"小时前";
		}
		else if(days == 1){
			ftime = "昨天";
		}
		else if(days == 2){
			ftime = "前天";
		}
		else if(days > 2 && days <= 10){ 
			ftime = days+"天前";			
		}
		else if(days > 10){			
			ftime = dateFormater2.get().format(time);
		}
		return ftime;
	}
	
	public static String friendly_time(long ldate) {
		return friendly_time(getDatestrByLong(ldate));
	}
	
	/**
	 * 以友好的方式显示时间 天
	 * @param sdate
	 * @return
	 */
	public static String friendlyTime4Day(long time) {
	
		String ftime = "";
		Calendar cal = Calendar.getInstance();
		Calendar ltime = Calendar.getInstance();
		ltime.setTimeInMillis(time);
	
		int ct = cal.get(Calendar.DAY_OF_MONTH);
		int lt = ltime.get(Calendar.DAY_OF_MONTH);
		int days = (int)(ct - lt);		
		if(days == -2){
			ftime = "后天";
		}
		else if(days == -1){
			ftime = "明天";
		}
		else if(days == 0){
			ftime = "今天";
		}
		else if(days == 1){
			ftime = "昨天";
		}
		else if(days == 2){
			ftime = "前天";
		}
		else if(days > 2 && days <= 10){ 
			ftime = days+"天前";			
		}
		else {			
			ftime = dateFormater2.get().format(time);
		}
		return ftime;
	}
	
	/**
	 * 获取时间描述字符串 显示到 天
	 * 格式：yyyy-MM-dd
	 * @return
	 */
	public static String getDateStr2Day(long time){
		if(time<9999999999L){
			time*=1000;
		}
		return dateFormater2.get().format(time);
	}
	
	public static String getCalendar2Day(Calendar calendar){		
		return getDateStr2Day(calendar.getTimeInMillis());
	}
	
	public static String getCalendar2DayZh(Calendar calendar){
		String str = getDateStr2Day(calendar.getTimeInMillis());
		return str.replaceFirst("-", "年").replaceFirst("-", "月") + "日";
	}
	
	public static String getDateTitle2Month(Calendar calendar){
		String str = getDateStr2Day(calendar.getTimeInMillis());
		return str.substring(0, 7).replaceFirst("-", ".");
	}
	
	public static String getWeekZh(Calendar calendar){
		String str = "星期";
		int week = calendar.get(Calendar.DAY_OF_WEEK);
		switch(week){
		case 1:
			str+="日";
			break;
		case 2:
			str+="一";
			break;
		case 3:
			str+="二";
			break;
		case 4:
			str+="三";
			break;
		case 5:
			str+="四";
			break;
		case 6:
			str+="五";
			break;
		case 7:
			str+="六";
			break;
		}
		return str;
	}
	
	/**
	 * 获取时间描述字符串 显示到 秒数
	 * 格式：yyyy-MM-dd HH:mm:ss
	 * @return
	 */
	public static String getDateStr2Second(long time){
		if(time<9999999999L){
			time*=1000;
		}
		return dateFormater.get().format(time);
	}
	
	/** 
	 * 方法名：getCurrentDate
	 * 描述：获取当前时间的描述
	 * @参数：   参数名  参数类型   参数描述
	 * @返回值类型： String   
	 * @创建时间：  2014年6月12日 
	 * @创建者：zhangyao
	 * @变更记录：2014年6月12日上午10:03:11 by
	 */
	public static String getCurrentDate(){
		return getDateStr2Second(System.currentTimeMillis());
	}
	
	/**
	 * 方法名：getCurrentTimestamp
	 * 描述：获取当前系统时间戳，精确到秒
	 * @参数：   参数名  参数类型   参数描述
	 * @返回值类型： long   
	 * @创建时间：  2014年8月14日 
	 * @创建者：韩创
	 * @变更记录：2014年8月14日上午10:21:15 by
	 */
	public static long getCurrentTimestamp(){
		return System.currentTimeMillis()/1000;
	}
	
	/** 
	 * 方法名：getCurrentDate
	 * 描述：获取当前时间的描述
	 * @参数：   参数名  参数类型   参数描述
	 * @返回值类型： String   
	 * @创建时间：  2014年6月12日 
	 * @创建者：zhangyao
	 * @变更记录：2014年6月12日上午10:03:11 by
	 */
	public static String getCurrentDate(String datetype){
		return getDateStr2Second(System.currentTimeMillis());
	}

	/**
	 * 
	 * 方法名：getTimeValues
	 * 描述： 将以“:” 间隔的时间信息字符串分解为相应的 int 值
	 * @参数：   参数名  参数类型   参数描述
	 * @返回值类型： int[]   
	 * @创建时间：  2014年6月12日 
	 * @创建者：zhangyao
	 * @变更记录：2014年6月12日下午4:27:57 by
	 */
	public static int[] getTimeValues(String timeStr,String splitStr){
		return getTimeValues(timeStr.replace(splitStr, ":"));
	}
	
	public static int[] getTimeValues(String timeStr){
		
		if(timeStr.contains(":")) {
			String[] ss = timeStr.split(":");
			int[] result = new int[ss.length];  
			for(int i = 0; i < ss.length; i++){
				String v = ss[i];
				if(v.length() > 1 && ( v.charAt(0) == '0')){
					v = v.substring(1);
				}
				result[i] = Integer.parseInt(v);
				
			}
			return result;
		}
		
		return null;
	}
	
	public static String getDateStr4Array(String timeStr){
		int[] time = getTimeValues(timeStr);
		if(time != null && time.length >= 3){
			StringBuilder sb = new StringBuilder();
			sb.append(time[0]).append("年");
			sb.append(time[1]).append("月");
			sb.append(time[2]).append("日");
			return sb.toString();
		}		
		return timeStr;
	}
	
	public static String getDateStr4Array(String timeStr,String splitStr){
		int[] time = getTimeValues(timeStr,splitStr);
		if(time != null && time.length >= 3){
			StringBuilder sb = new StringBuilder();
			sb.append(time[0]).append("年");
			sb.append(time[1]).append("月");
			sb.append(time[2]).append("日");
			return sb.toString();
		}		
		return timeStr;
	}
	
	
	/**
	 * 将字符串转位日期类型
	 * @param sdate
	 * @return
	 */
	public static Date toDate(String sdate) {
		try {
			return dateFormater.get().parse(sdate);
		} catch (ParseException e) {
			return null;
		}
	}
	/**
	 * 方法名：getTimestamp2Day
	 * 描述：获取Unix时间戳精确到天
	 * @参数：   参数名  参数类型   参数描述
	 * @返回值类型： long   
	 * @创建时间：  2014年7月10日 
	 * @创建者：韩创
	 * @变更记录：2014年7月10日下午5:53:50 by
	 */
	public static long getTimestamp2Day(int year,int month,int day){
		Calendar calendar = Calendar.getInstance();
		calendar.set(year, month-1, day);
		calendar.set(Calendar.HOUR_OF_DAY,0);
		calendar.set(Calendar.MINUTE,0);
		calendar.set(Calendar.SECOND,0);
		return calendar.getTimeInMillis()/1000;
	}
	/**
	 * 方法名：getTimestamp2Day
	 * 描述：获取Unix时间戳精确到天
	 * @参数：   参数名  参数类型   参数描述
	 * @返回值类型： long   
	 * @创建时间：  2014年7月10日 
	 * @创建者：韩创
	 * @变更记录：2014年7月10日下午7:10:06 by
	 */
	public static long getTimestamp2Day(Calendar calendar){
		//return getTimestamp2Day(calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH));
		calendar.set(Calendar.HOUR_OF_DAY,0);
		calendar.set(Calendar.MINUTE,0);
		calendar.set(Calendar.SECOND,0);
		return calendar.getTimeInMillis()/1000;
	}
	
	/**
	 * 方法名：getTimestamp2Seconds
	 * 描述 ：时间类型 yyyy-MM-dd HH:mm:ss
	 * @参数：   参数名  参数类型   参数描述
	 * @返回值类型： long   没有毫秒级
	 * @创建时间：  2014年8月6日 
	 * @创建者：韩创
	 * @变更记录：2014年8月6日上午10:13:47 by
	 */
	public static long getTimestamp2Seconds(String str){
		Date date1 = toDate(str);
	    Date date2 = toDate("1970-01-01 08:00:00");
	    if(date1==null){
	    	return 0;
	    }
	    long l = date1.getTime() - date2.getTime() > 0 ? date1.getTime() - date2.getTime() : date2.getTime() - date1.getTime();  
		return l/1000;
	}
	/**
	 * 方法名：getDatestrByLong
	 * 描述：long 转 str
	 * @参数：   参数名  参数类型   参数描述
	 * @返回值类型： String   
	 * @创建时间：  2014年8月6日 
	 * @创建者：韩创
	 * @变更记录：2014年8月6日上午10:29:12 by
	 */
	public static String getDatestrByLong(long time){
		
		if(time<9999999999L){
			time*=1000;
		}
		
	 	String date = dateFormater.get().format(new java.util.Date(time));
		return date;
	}
	
	/**
	 * 方法名：getTimestamp2Day
	 * 描述：字符串转换为Unix时间戳
	 * @参数：  str 2014-07-23
	 * @返回值类型： long   
	 * @创建时间：  2014年7月24日 
	 * @创建者：韩创
	 * @变更记录：2014年7月24日上午10:22:05 by
	 */
	public static long getTimestamp2Day(String str){
		int year = 1970,month = 1,day = 1;
		String[] data = str.split("-");
		year = Integer.parseInt(data[0]);
		month = Integer.parseInt(data[1]);
		day = Integer.parseInt(data[2]);
		return getTimestamp2Day(year,month,day);
	}
	
	public static Calendar getCalendarByStr(String str){
		
		
		try {
			SimpleDateFormat sdf= new SimpleDateFormat("yyyy-MM-dd");
			Date date = sdf.parse(str);
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(date);
			
	/*		int year = 1970,month = 1,day = 1;
			String[] data = str.split("-");
			
			if(data[1].startsWith("0")){
				data[1] = data[1].replace("0", "");
			}
			
			if(data[2].startsWith("0")){
				data[2] = data[2].replace("0", "");
			}
			
			year = Integer.parseInt(data[0]);
			month = Integer.parseInt(data[1]);
			day = Integer.parseInt(data[2]);
			
			Calendar calendar = Calendar.getInstance();
			calendar.set(year, month-1, day);
			calendar.set(Calendar.HOUR_OF_DAY,0);
			calendar.set(Calendar.MINUTE,0);
			calendar.set(Calendar.SECOND,0);*/
			
			return calendar;
		} catch (Exception e) {			
			e.printStackTrace();
			LogUtils.v("getCalendarByStr", " 日期转换出错");
			return Calendar.getInstance();
		}
	
	}
	/**
	 * 
	 * 方法名：foematInteger
	 * 描述：综合一下两种解决方案，互补一下
	 * @参数：   参数名  参数类型   参数描述
	 * @返回值类型： String   
	 * @创建时间：  2015年6月25日 
	 * @创建者：韩创
	 * @变更记录：2015年6月25日上午11:17:56 by
	 */
	public static String foematInteger(int num){
		if(num%10==0 || num<100){
			return foematInteger1(num);
		}else{
			return foematInteger2(num);
		}
	}
	
	/**
	 * 方法名：foematInteger
	 * 描述：阿拉伯数字转化为汉字，但是会出现一十零这种不符合常规的bug
	 * @参数：   参数名  参数类型   参数描述
	 * @返回值类型： String   
	 * @创建时间：  2015年4月8日 
	 * @创建者：韩创
	 * @变更记录：2015年4月8日下午4:59:55 by
	 */
	public static String foematInteger2(int num) {
		String[] units = { "", "十", "百", "千", "万", "十万", "百万", "千万", "亿",
			"十亿", "百亿", "千亿", "万亿" };
		char[] numArray = { '零', '一', '二', '三', '四', '五', '六', '七', '八', '九' };
		char[] val = String.valueOf(num).toCharArray();
		int len = val.length;
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < len; i++) {
			String m = val[i] + "";
			int n = Integer.valueOf(m);
			boolean isZero = n == 0;
			String unit = units[(len - 1) - i];
			if (isZero) {
				if ('0' == val[i - 1]) {
					// not need process if the last digital bits is 0
					continue;
				} else {
					// no unit for 0
					sb.append(numArray[n]);
				}
			} else {
				sb.append(numArray[n]);
				sb.append(unit);
			}
		}
		return sb.toString();
	}
	/**
	 * 方法名：foematInteger
	 * 描述：阿拉伯数字转换，虽然不会出现 一十零这种bug,但是会出现一百一这种bug
	 * @参数：   参数名  参数类型   参数描述
	 * @返回值类型： String   
	 * @创建时间：  2015年6月25日 
	 * @创建者：韩创
	 * @变更记录：2015年6月25日上午11:09:03 by
	 */
	public static String foematInteger1(int num) {
		String si = num+"";
		String rs = "";
		String[] aa = { "", "十", "百", "千", "万", "十万", "百万", "千万", "亿", "十亿" };
		String[] bb = { "一", "二", "三", "四", "五", "六", "七", "八", "九" };
		char[] ch = si.toCharArray();
		int maxindex = ch.length;
		// 字符的转换
		// 两位数的特殊转换
		if (maxindex == 2) {
			for (int i = maxindex - 1, j = 0; i >= 0; i--, j++) {
				if (ch[j] != 48) {
					if (j == 0 && ch[j] == 49) {
						rs += aa[i];
					} else {
						rs += bb[ch[j] - 49] + aa[i];
					}
				}
			}
			// 其他位数的特殊转换，使用的是int类型最大的位数为十亿
		} else {
			for (int i = maxindex - 1, j = 0; i >= 0; i--, j++) {
				if (ch[j] != 48) {
					rs += bb[ch[j] - 49] + aa[i];
				}
			}
		}
		return rs;
	}
	
	/**
	 * 方法名：getNumberStr
	 * 描述：获取float数据的按照有效位进行四舍五入后的字符串
	 * @参数：   参数名  参数类型   参数描述
	 * @返回值类型： String   
	 * @创建时间：  2014年9月1日 
	 * @创建者：zhangyao
	 * @变更记录：2014年9月1日下午2:37:41 by
	 */
	public static String getNumberStr(float v,int l){
		String regx = "";
		switch(l){
	     case 0:{ regx="#"; break; }
	     case 1:{ regx="#.0"; break; }
	     case 2:{ regx="#.00"; break; }
	     case 3:{ regx="#.000"; break; }
	     case 4:{ regx="#.0000"; break; }
	     default:{ regx="#.##"; break; }
		}
		
		DecimalFormat df = new DecimalFormat(regx);
		df.setRoundingMode(RoundingMode.HALF_UP);
		
	
		if(v == 0 && l <= 1){
			return "0";
		}else{
			BigDecimal decimal = new BigDecimal(String.valueOf(v));
			String str = df.format(decimal.doubleValue());
			if(str.startsWith(".") ){
				return "0"+str;	
			}else if(str.startsWith("-.") ){
				return str.replace("-.", "-0.");	
			}else {
				return str;	
			}
				
		}
		
	}
	/**
	 * 方法名：getNumberFloat
	 * 描述：
	 * @参数：   参数名  参数类型   参数描述
	 * @返回值类型： String   
	 * @创建时间：  2014年11月12日 
	 * @创建者：韩创
	 * @变更记录：2014年11月12日上午11:31:11 by
	 */
	public static float getNumberFloat(float v,int l){
		String regx = "";
		switch(l){
	     case 0:{ regx="#"; break; }
	     case 1:{ regx="#.0"; break; }
	     case 2:{ regx="#.00"; break; }
	     case 3:{ regx="#.000"; break; }
	     case 4:{ regx="#.0000"; break; }
	     default:{ regx="#.##"; break; }
		}
		
		DecimalFormat df = new DecimalFormat(regx);
		df.setRoundingMode(RoundingMode.HALF_UP);
		float rs = Float.parseFloat(df.format(v));
		return rs;
	}
	/**
	 * 方法名：getDate4BloodSugarLineChart
	 * 描述：2015-09-06 转换 9.6
	 * @参数：   参数名  参数类型   参数描述
	 * @返回值类型： String   
	 * @创建时间：  2015年9月18日 
	 * @创建者：韩创
	 * @变更记录：2015年9月18日下午3:26:09 by
	 */
	public static String getDate4BloodSugarLineChart(String dateStr){
		String s = "";
		try{
			String str[] = dateStr.split("-");
			s += ""+str[1]+"."+str[2];
		}catch(Exception e){
			e.printStackTrace();
		}
		return s;
	}
	
}
