package com.lianjiao.core.net;

import java.util.HashMap;
import java.util.Map;

import com.android.http.LoadControler;
import com.android.http.LoadListener;
import com.android.http.RequestManager;
import com.android.volley.Request.Method;
import com.google.gson.Gson;
import com.lianjiao.core.CoreConfig;
import com.lianjiao.core.app.CoreApplication;
import com.lianjiao.core.utils.LogUtils;
import com.lianjiao.core.utils.LsUtil;
import com.lianjiao.core.utils.StringUtil;

/**
 * 类名称：HttpUtil   
 * 类描述：  封装Volley的 post、put、get无缝替换的HttpUtil工具
 * @创建者：韩创    
 * @创建时间：2015年9月1日   
 * @变更记录：2015年9月1日下午12:00:42 by 记录变更人
 */
public class CoreHttpUtil {
	/**
	 * 方法名：put
	 * 描述：发送post请求
	 * @参数：   参数名  参数类型   参数描述
	 * @返回值类型： LoadControler   
	 * @创建时间：  2015年9月1日 
	 * @创建者：韩创
	 * @变更记录：2015年9月1日上午10:21:55 by
	 */
	public static LoadControler putMethod(CoreRequestParams params,String url,CoreBaseListener listener){
		int actionId = (int) (Math.random()*100);
		Gson gson = new Gson();
		String postJson = gson.toJson(params);
		return RequestManager.getInstance().post(url, postJson,listener, actionId);
	}
	
	/**
	 * 方法名：get
	 * 描述：get方式请求参数
	 * @参数：   参数名  参数类型   参数描述
	 * @返回值类型： LoadControler   
	 * @创建时间：  2015年9月1日 
	 * @创建者：韩创
	 * @变更记录：2015年9月1日上午11:00:20 by
	 */
	public static LoadControler getMethod(CoreRequestParams params,String url,CoreBaseListener listener){
		int actionId = (int) (Math.random()*100);
		url += "?";
		for (String key : params.keySet()) {
			System.out.println("key= " + key + " and value= " + params.get(key));
			url += key+"="+params.get(key);
			url += "&";
		}
		return RequestManager.getInstance().get(url,listener, actionId);
	}
	/**
	 * 方法名：get
	 * 描述：添加了网络是否连接成功判断
	 * @参数：   参数名  参数类型   参数描述
	 * @返回值类型： LoadControler   
	 * @创建时间：  2015年12月22日 
	 * @创建者：韩创
	 * @变更记录：2015年12月22日下午3:08:43 by
	 */
	public static LoadControler get(CoreRequestParams params,String url,CoreBaseListener listener){
		if(!LsUtil.isNetworkAvailable(CoreApplication.appContext) || CoreConfig.deBug){
			listener.onError(CoreConfig.deBug?"debug模式禁用了网络加载":"网络未连接");
			return null;
		}
		return getMethod(params,url, listener);
	}
	
	/**
	 * 方法名：post
	 * 描述：post方式请求参数
	 * @参数：   参数名  参数类型   参数描述
	 * @返回值类型： LoadControler   
	 * @创建时间：  2015年9月1日 
	 * @创建者：韩创
	 * @变更记录：2015年9月1日上午11:57:54 by
	 */
	public static LoadControler postMethod(final CoreRequestParams params,String url,final CoreBaseListener listener){
		Map<String, String> headers = new HashMap<String, String>();
		boolean shouldCache = false;
		int timeoutCount = 3*1000;//The current timeout in milliseconds
		int retryTimes = 2;
		int actionId = (int) (Math.random()*100);
		LoadListener requestListener = new LoadListener() {
			
			@Override
			public void onSuccess(byte[] data, Map<String, String> headers, final String url,
					int actionId) {
				try {
					String json = new String(data, "utf-8");
					json = StringUtil.convert(json);
					LogUtils.i("http","***********************网络访问返回数据["+actionId+"]************************");
					LogUtils.i("http",json);
					LogUtils.i("http","***********************网络访问返回数据["+actionId+"]************************");
					listener.onComplete(json);
				} catch (Exception e) {
					listener.onError(e.getLocalizedMessage());
					e.printStackTrace();
				}
				
			}
			
			@Override
			public void onStart() {
				listener.onStart();
			}
			
			@Override
			public void onError(String errorMsg, String url, int actionId) {
				listener.onError(errorMsg);
				LogUtils.i("http","***********************网络访问request数据["+actionId+"]************************");
				LogUtils.i("http",errorMsg);
				LogUtils.i("http","***********************网络访问request数据["+actionId+"]************************");
			}
		};
		LogUtils.i("http","***********************网络访问request数据["+actionId+"]************************");
		LogUtils.i("http",url+params.toString());
		LogUtils.i("http","***********************网络访问request数据["+actionId+"]************************");
		return RequestManager.getInstance().sendRequest(Method.POST, url, params, headers, requestListener, shouldCache, timeoutCount, retryTimes, actionId);
	}
	
	public static LoadControler post(final CoreRequestParams params,final String url,final CoreBaseListener listener){
		if(!LsUtil.isNetworkAvailable(CoreApplication.appContext) || CoreConfig.deBug){
			listener.onError(CoreConfig.deBug?"debug模式禁用了网络加载":"网络未连接");
			return null;
		}
		return postMethod(params,url,listener);
	}
	
}
