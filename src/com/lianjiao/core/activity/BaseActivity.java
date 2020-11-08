package com.lianjiao.core.activity;

import roboguice.activity.RoboActivity;
import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;

import com.android.http.LoadControler;
import com.lianjiao.core.R;
import com.lianjiao.core.app.CoreAppManager;
import com.lianjiao.core.app.CoreApplication;
import com.lianjiao.core.dialog.LsProgressDialog;
import com.lianjiao.core.utils.LsViewUtil;
import com.lianjiao.core.widget.LsTextView;

/**
 * 类名称：BaseActivity   
 * 类描述：所有Activity的基类，必须调用super.onCreate()方法
 * @创建者：韩创    
 * @创建时间：2015年12月16日   
 * @变更记录：2015年12月16日上午10:43:11 by 记录变更人
 */
public abstract  class BaseActivity extends RoboActivity{

	public LoadControler mLoadControler = null;
	
	public Context mContext;
	
	public LsProgressDialog progressDialog;
	
	public LsTextView title;
	
	public LsTextView titleLeft;
	
	public LsTextView titleRight;
	
    @SuppressLint("NewApi")
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        CoreAppManager.addActivity(this);
        //初始化设备屏幕信息
        if(CoreApplication.dm == null){
        	CoreApplication.dm=new DisplayMetrics();		 
    	    getWindowManager().getDefaultDisplay().getMetrics(CoreApplication.dm);  
    	    LsViewUtil.ScreenWidth = CoreApplication.dm.widthPixels;
    	    LsViewUtil.ScreenHidth = CoreApplication.dm.heightPixels;
        }
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
    	if (mLoadControler != null) {
			mLoadControler.cancel();
		}
        super.onDestroy();
        CoreAppManager.finishActivity(this);
    }
    
	/**
	 * 方法名：showProgressDialog
	 * 描述：打开加载中对话框
	 * @参数：   参数名  参数类型   参数描述
	 * @返回值类型： void   
	 * @创建时间：  2014年6月30日 
	 * @创建者：zhangyao
	 * @变更记录：2014年6月30日上午11:31:19 by
	 */
	public void showProgressDialog(){
		
		if(progressDialog == null){			
			progressDialog = new LsProgressDialog(this);
			progressDialog.setCancelable(false);
		}
		
		progressDialog.show();
	}
	
	/**
	 * 方法名：closeProgressDialog
	 * 描述：关闭加载中对话框
	 * @参数：   参数名  参数类型   参数描述
	 * @返回值类型： void   
	 * @创建时间：  2014年6月30日 
	 * @创建者：zhangyao
	 * @变更记录：2014年6月30日上午11:31:23 by
	 */
	public void closeProgressDialog(){
		if(progressDialog != null && progressDialog.isShowing()){
			progressDialog.dismiss();
		}
	}
	
	@Override
	protected void onResume() {
		super.onResume();
	}
	
	@Override
	protected void onPause() {
		super.onPause();
	}
	
	public void setTitle(String str) {
		if(this.title!=null){
			this.title.setText(str);
		}else{
			this.title = (LsTextView) findViewById(R.id.app_title);
			if(this.title!=null)
			this.title.setText(str);
		}
		
	}
	
	public void setTitleLeftVisiable(boolean isVisiable,String str){
		if(this.titleLeft!=null){
			this.titleLeft.setVisibility(isVisiable?View.VISIBLE:View.INVISIBLE);
		}else{
			this.titleLeft = (LsTextView) findViewById(R.id.app_left);
			if(this.titleLeft!=null){
				titleLeft.setText(str);
				this.titleLeft.setVisibility(isVisiable?View.VISIBLE:View.INVISIBLE);
			}
		}
		
	}
	
	public void setTitleRightVisiable(boolean isVisiable,String str){
		if(this.titleRight!=null){
			this.titleRight.setVisibility(isVisiable?View.VISIBLE:View.INVISIBLE);
		}else{
			this.titleRight = (LsTextView)findViewById(R.id.app_right);
			if(this.titleRight!=null){
				titleRight.setText(str);				
				this.titleRight.setVisibility(isVisiable?View.VISIBLE:View.INVISIBLE);
			}
		}
	}
	
	public void setTitleLeftVisiable(boolean isVisiable,String str,Drawable drawable){
		setTitleLeftVisiable(isVisiable,str);
		if(drawable!=null){
			drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight()); //设置边界
			this.titleLeft.setCompoundDrawables(drawable,null,null,null);  
		}else{
			this.titleLeft.setCompoundDrawables(null,null,null,null);
		}
	}
	
	public void setTitleRightVisiable(boolean isVisiable,String str,Drawable drawable){
		setTitleRightVisiable(isVisiable,str);
		if(drawable!=null){
			drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight()); //设置边界
			this.titleRight.setCompoundDrawables(null,null,drawable,null);
		}else{
			this.titleRight.setCompoundDrawables(null,null,null,null);
		}
	}
    

}
