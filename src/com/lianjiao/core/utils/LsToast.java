/**
 * 项目名称：Leshi_Android_Client_1.0 
 * 所属包名: com.leshi.happytime.utils
 * 文件名		：LsToast.java
 * 创建日期	：2014年6月11日
 * Copyright (c)  西安西安乐食
 * All rights reserved.
 */
package com.lianjiao.core.utils;

import android.content.Context;
import android.widget.Toast;

import com.lianjiao.core.app.CoreApplication;

/**
 * 类名称：LsToast   
 * 类描述：   自定义Toast, 项目中统一使用此类
 * @创建者：zhangyao     
 * @创建时间：2014年6月11日
 * @变更记录：2014年6月11日下午4:41:22 by 记录变更人
 */
public class LsToast {
	
	public static Toast toa;
	
	public static void show(Context context,CharSequence text){
		if(context==null)return;
		if(toa==null){
			toa=Toast.makeText(context, text, Toast.LENGTH_LONG);
			//toa.setGravity(Gravity.TOP|Gravity.CENTER_HORIZONTAL, 0, 50);
		}else
			toa.setText(text);
		
		toa.show();
	}

	public static void show(Context context,int resId){
		if(context!=null){
			show(context, context.getText(resId));
		}
	}
	
	public static void cancel(){
		if(toa!=null){
			toa.cancel();
		}
	}
	
	/**
	 * 方法名：t
	 * 描述：测试用的toast信息
	 * @参数：   参数名  参数类型   参数描述
	 * @返回值类型： void   
	 * @创建时间：  2015年6月29日 
	 * @创建者：韩创
	 * @变更记录：2015年6月29日下午3:42:57 by
	 */
	public static void t(String str){
		show(CoreApplication.getInstance(), str);
	}
}
