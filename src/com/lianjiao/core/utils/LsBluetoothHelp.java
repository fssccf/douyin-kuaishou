package com.lianjiao.core.utils;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;

/**
 * 
 * 类名称：LsBluetoothHelp   
 * 类描述：   蓝牙相关操作辅助类
 * @创建者：linbo     
 * @创建时间：2015年6月5日
 * @变更记录：2015年6月5日下午2:52:57 by 记录变更人
 */
public class LsBluetoothHelp {

	//单例
	public static LsBluetoothHelp lsBluetoothHelp;
	private LsBluetoothHelp(){}
	public static LsBluetoothHelp getBluetoothHelp(){
		if(lsBluetoothHelp==null){
			lsBluetoothHelp=new LsBluetoothHelp();
		}
		return lsBluetoothHelp;
	}
	
	public final static int REQUESTCODE_BLUETOOTH_STATE=10;
	
	public static final int BLUETOOTH_OK = 1;
	public static final int BLUETOOTH_DISENABLE = 2;
	public static final int BLUETOOTH_TOOLOW = 3;
	
	/**
	 * 方法名：checkBluetoothOn
	 * 描述：检查蓝牙是否可用，并打开
	 */
	public int checkBluetoothOn(Activity context){
		if (!context.getPackageManager().hasSystemFeature(PackageManager.FEATURE_BLUETOOTH_LE)) {
		    LsToast.show(context, "您的版本过低，建议使用Android4.3以上版本");
		    return BLUETOOTH_TOOLOW;
		}
		boolean isOn=BluetoothAdapter.getDefaultAdapter().isEnabled();
		if(!isOn){
			requestBluetoothOpen(context);
		}
		return isOn?BLUETOOTH_OK:BLUETOOTH_DISENABLE;
	}
	
	/**
	 * 方法名：requestBluetoothOpen
	 * 描述：请求蓝牙打开
	 */
	public void requestBluetoothOpen(Activity activity) {
		Intent in=new Intent();
		in.setAction(BluetoothAdapter.ACTION_REQUEST_ENABLE);
		activity.startActivityForResult(in, REQUESTCODE_BLUETOOTH_STATE);
//		if(activity instanceof BaseBleFragmentActivity){
//			((BaseBleFragmentActivity)activity).setConnectStatus(LsConnectStatus.REQUEST_BLUETOOTHOPEN);
//		}
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
		if (context.getPackageManager().hasSystemFeature(PackageManager.FEATURE_BLUETOOTH)) {		 
			return true;
		}		
		return false;
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
		
		if (context.getPackageManager().hasSystemFeature(PackageManager.FEATURE_BLUETOOTH_LE)) {		 
			return true;
		}		
		return false;
	}
	
}
