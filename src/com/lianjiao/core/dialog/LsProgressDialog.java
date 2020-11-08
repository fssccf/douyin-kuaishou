package com.lianjiao.core.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Window;
import android.widget.ImageView;

import com.lianjiao.core.R;

/**
 * 描述:加载中 对话框
 * 
 * @version 1.00
 * @author zhangyao
 * 
 */
public class LsProgressDialog extends Dialog {

	private int showTime = 30; // 显示时间（秒）
	
	/**
	 * 
	 */
	public LsProgressDialog(Context context) {
		super(context ,R.style.progressdialogStyle);
	}
	
	private Handler mHandler = new Handler(){
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 0:
				dismiss();
				break;
			default:
				break;
			}
		}
		
	};	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getWindow().requestFeature(Window.FEATURE_NO_TITLE);		
		setContentView(R.layout.dialog_progress);
		
	/*	Window window = getWindow();
		window.setLayout(WindowManager.LayoutParams.WRAP_CONTENT,
				WindowManager.LayoutParams.WRAP_CONTENT);
		window.setGravity(Gravity.CENTER);*/
		
		setOnCancelListener(new OnCancelListener() {
			
			@Override
			public void onCancel(DialogInterface dialog) {
				mHandler.removeMessages(0);
			}
		});
		
		setOnDismissListener(new OnDismissListener() {
			
			@Override
			public void onDismiss(DialogInterface dialog) {
				mHandler.removeMessages(0);
			}
		});
		
	}
	
	@Override
	public void show() {
		super.show();
		startDialogTimer();
	}
	
	/**
	 * 方法名：startDialogTimer
	 * 描述：设置对话框的自动关闭功能
	 * @参数：   参数名  参数类型   参数描述
	 * @返回值类型： void   
	 */
	private void startDialogTimer(){
		mHandler.removeMessages(0);
		mHandler.sendEmptyMessageDelayed(0, showTime * 1000);
	}
	
	@Override
	public void onBackPressed() {
		//super.onBackPressed();
		// TODO 判断访问网络连接池中是否存在连接
		//LsApplication.getInstance().cancelPendingRequests(LsApplication.TAG);
		dismiss();
	}
	
}

