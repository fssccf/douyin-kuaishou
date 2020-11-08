package com.lianjiao.core.utils;
public enum LsConnectStatus{
	//正常，未连接状态
	NORMAL(0),
	//请求蓝牙打开
	REQUEST_BLUETOOTHOPEN(1),
	//蓝牙未打开
	BLUETOOTH_NOTOPEN(2),
	//已连接状态
	CONNECTED(10),
	//命令已发送，等待连接
	CONNECTING_WAITORDERDATA(11);
	
	int index;
	LsConnectStatus(int index){
		this.index=index;
	}
	public int getIndex(){
		return index;
	}
}