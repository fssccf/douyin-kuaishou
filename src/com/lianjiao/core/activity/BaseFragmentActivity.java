package com.lianjiao.core.activity;

import roboguice.activity.RoboFragmentActivity;
import android.annotation.SuppressLint;
import android.os.Bundle;

import com.lianjiao.core.app.CoreAppManager;
import com.lianjiao.core.dialog.LsProgressDialog;
import com.lianjiao.core.fragment.BaseFragment;

public class BaseFragmentActivity extends RoboFragmentActivity{
	
	public LsProgressDialog progressDialog;
	
	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		CoreAppManager.addActivity(this);
		 //防止 没有找到方法异常
        try{
        	if(getActionBar() != null){
    			getActionBar().hide();	
    		}
        }catch(Exception e){
        	e.printStackTrace();
        }
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		CoreAppManager.finishActivity(this);
	}
	
	
	public void showProgressDialog(){
		if(progressDialog == null){			
			progressDialog = new LsProgressDialog(this);
			progressDialog.setCancelable(false);			
		}
		
		progressDialog.show();
	}
	
	
	public void closeProgressDialog(){
		if(progressDialog != null && progressDialog.isShowing()){
			progressDialog.dismiss();
		}
	}
	
	/**
	 * 方法名：back2LastFrag
	 * 描述：回退到上一级fragment
	 * @参数：   参数名  参数类型   参数描述
	 * @返回值类型： void   
	 * @创建时间：  2014年11月11日 
	 * @创建者：Administrator
	 * @变更记录：2014年11月11日上午11:24:13 by
	 */
	public void back2LastFrag(Bundle bundle){
		
	}
	
	/**
	 * 方法名：showFragPage
	 * 描述：跳转页面
	 * @参数：   参数名  参数类型   参数描述
	 * @返回值类型： void   
	 * @创建时间：  2014年11月11日 
	 * @创建者：Administrator
	 * @变更记录：2014年11月11日下午1:48:16 by
	 */
	public void showFragPage(BaseFragment frag){
		
	}
	
	@Override
	protected void onResume() {
		super.onResume();
	}
	
	@Override
	protected void onPause() {
		super.onPause();
	}

}
