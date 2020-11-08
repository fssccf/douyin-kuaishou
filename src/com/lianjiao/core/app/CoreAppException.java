package com.lianjiao.core.app;

import java.lang.Thread.UncaughtExceptionHandler;

import android.content.Context;
import android.os.Looper;

import com.lianjiao.core.utils.LsToast;

/**
 *
 * 描述:自定义 Application
 *
 * Created by GA on 15-8-4.
 * @version 1.00
 * @author 韩创
 *
 */
public class CoreAppException implements UncaughtExceptionHandler {

    //private Context mContext;

    private static CoreAppException instance;
    
	/** 系统默认的UncaughtException处理类 */
	private Thread.UncaughtExceptionHandler mDefaultHandler;


    private CoreAppException(Context context){
        //this.mContext = context;
        this.mDefaultHandler = Thread.getDefaultUncaughtExceptionHandler();
    }

    public static CoreAppException getAppExctptionCaught(Context context){
        if(instance == null ){
            instance = new CoreAppException(context);
        }
        return instance;
    }

    @Override
    public void uncaughtException(Thread thread, Throwable ex) {
		if (!handleException(ex) && mDefaultHandler != null) {
			// 如果用户没有处理则让系统默认的异常处理器来处理
			mDefaultHandler.uncaughtException(thread, ex);
		} else {
			try {
				Thread.sleep(3000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			} finally {
				// 退出程序
				android.os.Process.killProcess(android.os.Process.myPid());
				System.exit(1);
			}
		}
		
    }
    
	/**
	 * 自定义异常处理:收集错误信息&发送错误报告
	 * @param ex
	 * @return true:处理了该异常信息;否则返回false
	 */
	private boolean handleException(Throwable ex) {
		if(ex == null) {
			return false;
		}
		Exception e = new Exception(ex);
		e.printStackTrace();
		final Context context = CoreAppManager.currentActivity();
		if(context == null) {
			return false;
		}
		//final Exception e = new Exception(ex);
		//显示异常信息&发送报告
		new Thread() {
			public void run() {
				Looper.prepare();
				LsToast.show(CoreApplication.getInstance(), "很抱歉,程序出现异常,即将退出.");
				//PgyCrashManager.reportCaughtException(LsApplication.getInstance(), e);//这里不用主动上传，蒲公英自己会捕获异常上传的
				Looper.loop();
			}

		}.start();
		return true;
	}
	


}

