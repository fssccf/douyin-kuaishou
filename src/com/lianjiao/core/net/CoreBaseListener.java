/**
 * 文件名		：BaseListener.java
 * 创建日期	：2014年6月10日
 * Copyright (c)  西安西安乐食
 * All rights reserved.
 */
package com.lianjiao.core.net;

import java.util.Map;

import com.android.http.RequestManager.RequestListener;

/**
 * 类名称：BaseListener   
 * 类描述：访问网络公共监听类
 * @创建者：韩创    
 * @创建时间：2014年6月12日   
 * @变更记录：2014年6月12日上午10:35:21 by 记录变更人
 */
public abstract class CoreBaseListener implements RequestListener{
	/**
	 * 方法名：onComplete
	 * 描述：成功
	 * @参数：   参数名  参数类型   参数描述
	 * @返回值类型： void   
	 * @创建时间：  2014年6月12日 
	 * @创建者：韩创
	 * @变更记录：2014年6月12日下午2:41:42 by
	 */
	public abstract void onComplete(String context);
	
	/**
	 * 方法名：onError
	 * 描述：失败
	 * @参数：   参数名  参数类型   参数描述
	 * @返回值类型： void   
	 * @创建时间：  2014年6月12日 
	 * @创建者：韩创
	 * @变更记录：2014年6月12日下午2:42:09 by
	 */
	public abstract void onError(String err);
	
	/**
	 * 方法名：onCancel
	 * 描述：用户取消操作
	 * @参数：   参数名  参数类型   参数描述
	 * @返回值类型： void   
	 * @创建时间：  2014年6月12日 
	 * @创建者：韩创
	 * @变更记录：2014年6月12日下午2:42:21 by
	 */
	public void onCancel(){};
	
	/**
	 * 方法名：onStart
	 * 描述：发起网络请求
	 * @参数：   参数名  参数类型   参数描述
	 * @返回值类型： void   
	 * @创建时间：  2015年9月1日 
	 * @创建者：韩创
	 * @变更记录：2015年9月1日下午12:02:13 by
	 */
	public void onStart(){};
	
	@Override
	public void onSuccess(String response, Map<String, String> headers,
			String url, int actionId) {
		onComplete(response);
	}
	
	@Override
	public void onRequest() {
		onStart();
	}
	
	@Override
	public void onError(String errorMsg, String url, int actionId) {
		onError(errorMsg);
	}
	

}
