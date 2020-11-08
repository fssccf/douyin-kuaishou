package com.lianjiao.core.utils;

import java.util.Calendar;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.lianjiao.core.app.CoreApplication;

public class LsUtil {
	
	/**
	 * 网络是否可用
	 * 
	 * @param context
	 * @return
	 */
	public static boolean isNetworkAvailable(Context context)
	{
		ConnectivityManager mgr = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo[] info = mgr.getAllNetworkInfo();
		if (info != null)
		{
			for (int i = 0; i < info.length; i++)
			{
				if (info[i].getState() == NetworkInfo.State.CONNECTED)
				{
					return true;
				}
			}
		}
		return false;
	}
	/**
	 * 方法名：getBloodSugarColor
	 * 描述：获取对应的血糖值该显示的颜色
	 * @参数：   参数名  参数类型   参数描述
	 * @返回值类型： int   
	 * @创建时间：  2015年9月18日 
	 * @创建者：韩创
	 * @变更记录：2015年9月18日下午12:00:07 by
	 */
	public static int getBloodSugarColor(float value,boolean isDishBefore){
		int color = 0;
		if(isDishBefore){
			if(value<3.9f){//偏低
				color = Color.rgb(93, 235, 123);
			}else if(value>=3.9f && value<=6.1f){//正常
				color = Color.rgb(120, 171, 255);
			}else{//偏高
				color = Color.rgb(245, 68, 68);
			}
		}else{
			if(value<=7.8f){//正常
				color = Color.rgb(120, 171, 255);
			}else{//偏高
				color = Color.rgb(245, 68, 68);
			}
		}
		return color;
	}
	
	 /**
     * 方法名：getMealTypeByTime
     * 描述：早餐时间：6-11点
			午餐时间：11-16点
			晚餐时间：16-23点

     * @参数：   参数名  参数类型   参数描述
     * @返回值类型： int   
     * @创建时间：  2014年11月11日 
     * @创建者：Administrator
     * @变更记录：2014年11月11日下午3:16:26 by
     */
    public static int getMealTypeByTime(Calendar time){    	
    	int hour = time.get(Calendar.HOUR_OF_DAY);
    	
    	int type = LsConstants.MEAL_TYPE_SUPPER;//LsConstants.MEAL_TYPE_ADDSELF;
    
    	if(hour >= 6 && hour < 11){
    		type = LsConstants.MEAL_TYPE_BREAKFIRST;
    	}else if(hour >= 11 && hour < 16){
    		type = LsConstants.MEAL_TYPE_LUNCH;
    	}else if(hour >= 16 && hour <= 23){
    		type = LsConstants.MEAL_TYPE_SUPPER;
    	}
    	
    	return type;
    }
    /**
     * 获取血糖记录时间段，时间对应的时间段
     * 方法名：getBloodSugarTime
     * 描述：
     * @参数：   参数名  参数类型   参数描述
     * @返回值类型： int   
     * @创建时间：  2015年9月28日 
     * @创建者：韩创
     * @变更记录：2015年9月28日上午10:57:09 by
     */
    public static int getBloodSugarTime(Calendar time){
    	int hour = time.get(Calendar.HOUR_OF_DAY);
    	int type = LsConstants.BLOODSUGAR_TYPE_BREAKFAST_BEFORE;
    	if(hour >= 1 && hour < 9){
        	type = LsConstants.BLOODSUGAR_TYPE_BREAKFAST_BEFORE;
    	}else if(hour >= 9 && hour < 11){
    		type = LsConstants.BLOODSUGAR_TYPE_BREAKFAST_AFTER;
    	}else if(hour >= 11 && hour < 12){
    		type = LsConstants.BLOODSUGAR_TYPE_LUNCH_BEFORE;
    	}else if(hour >= 12 && hour < 16){
    		type = LsConstants.BLOODSUGAR_TYPE_LUNCH_AFTER;
    	}else if(hour >= 16 && hour < 19){
    		type = LsConstants.BLOODSUGAR_TYPE_SUPPER_BEFORE;
    	}else if(hour >= 19 && hour < 21){
    		type = LsConstants.BLOODSUGAR_TYPE_SUPPER_AFTER;
    	}else if(hour >= 21 && hour < 24){
    		type = LsConstants.BLOODSUGAR_TYPE_SLEEP_BEFORE;
    	}
    	return type;
    }
    
    public static String getBloodSugarStrByType(int type){
    	String str = "";
    	switch (type) {
		case 0:
			str = "餐前血糖";
			break;
		case 1:
			str = "餐后血糖";		
			break;
		case 2:
			str = "餐前血糖";
			break;
		case 3:
			str = "餐后血糖";
			break;
		case 4:
			str = "餐前血糖";
			break;
		case 5:
			str = "餐后血糖";
			break;
		case 6:
			str = "睡前血糖";
			break;

		default:
			str = "睡前血糖";
			break;
		}
    	
    	return str;
    }
    
    /**
	 * 方法名：isSupportBluetooth
	 * 描述：判断设备是否支持蓝牙
	 * @参数：   参数名  参数类型   参数描述
	 * @返回值类型： boolean   
	 * @创建时间：  2014年10月9日 
	 * @创建者：Administrator
	 * @变更记录：2014年10月9日下午5:23:50 by
	 */
	public static boolean isSupportBluetooth(Context context){
		return LsBluetoothHelp.isSupportBluetooth(context);
	}
	
	/** 
	 * 方法名：isSupportBle
	 * 描述：检测当前方法是否支持BLE 技术
	 * @参数：   参数名  参数类型   参数描述
	 * @返回值类型： boolean   
	 * @创建时间：  2014年6月16日 
	 * @创建者：zhangyao
	 * @变更记录：2014年6月16日上午9:38:00 by
	 */
	public static boolean isSupportBle(Context context) {
		return LsBluetoothHelp.isSupportBle(context);
	}
	
	/**
	 * 方法名：webViewEnable
	 * 描述：webView中的js和Android交互支持 APILevel 17及以上，以下不支持，这里限制17+才支持
	 * @参数：   参数名  参数类型   参数描述
	 * @返回值类型： boolean   
	 * @创建时间：  2015年7月20日 
	 * @创建者：韩创
	 * @变更记录：2015年7月20日下午5:41:17 by
	 */
	public static boolean webViewEnable(){
		if(android.os.Build.VERSION.SDK_INT<=android.os.Build.VERSION_CODES.JELLY_BEAN_MR1){
			return false;
		}else{
			return true;
		}
	}
	
	/**
	 * 方法名：isUpdateApp
	 * 描述：判断当前版本是否更新
	 * @参数：   参数名  参数类型   参数描述
	 * @返回值类型： boolean   
	 * @创建时间：  2015年11月3日 
	 * @创建者：韩创
	 * @变更记录：2015年11月3日下午2:20:38 by
	 */
	public static boolean isUpdateApp(int curCode){
		boolean isUpdate = false;
		try{
			PackageManager manager = CoreApplication.getInstance().getPackageManager();
			PackageInfo info = manager.getPackageInfo(CoreApplication.getInstance().getPackageName(), 0);
			int code = info.versionCode;
			if(curCode>code){
				isUpdate = true; 
			}
		} catch (Exception e) {
			e.printStackTrace();
			isUpdate = false;
		}
		return isUpdate;
	}
	
	/** 
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素) 
     */  
    public static int dip2px(Context context, float dpValue) {  
        final float scale = context.getResources().getDisplayMetrics().density;  
        return (int) (dpValue * scale + 0.5f);  
    }  
  
    /** 
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp 
     */  
    public static int px2dip(Context context, float pxValue) {  
        final float scale = context.getResources().getDisplayMetrics().density;  
        return (int) (pxValue / scale + 0.5f);  
    } 

}
