package com.lianjiao.core.app;

import java.util.Stack;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;

/**
 * 描述:应用管理
 * @version 1.00
 * @author 韩创
 */
public class CoreAppManager {

    private static Stack<Activity> activityStack;

    /**
     * 添加Activity到堆栈
     */
    public static void addActivity(Activity activity){
        if(activityStack==null){
            activityStack=new Stack<Activity>();
        }
        activityStack.add(activity);
    }
    /**
     * 获取当前Activity（堆栈中最后一个压入的）
     */
    public static Activity currentActivity(){
    	try{
    		Activity activity=activityStack.lastElement();
    		return activity;
    	}catch(Exception e){
    		e.printStackTrace();
    		return null;
    	}
    }
    /**
     * 方法名：finishActivity
     * 描述：获取指定Activity，不存在栈中则返回null
     * @参数：   参数名  参数类型   参数描述
     * @返回值类型： void
     * @创建时间：  2015年4月22日
     * @创建者：韩创
     * @变更记录：2015年4月22日下午5:28:09 by
     */
    public static Activity getActivity(Class<?> cls){
        for (Activity activity : activityStack) {
            if(activity.getClass().equals(cls) ){
                return activity;
            }
        }
        return null;
    }
    /**
     * 结束当前Activity（堆栈中最后一个压入的）
     */
    public static void finishActivity(){
        Activity activity=activityStack.lastElement();
        finishActivity(activity);
    }
    /**
     * 结束指定的Activity
     */
    public static void finishActivity(Activity activity){
        if(activity!=null){
            activityStack.remove(activity);
            activity.finish();
            activity=null;
        }
    }
    /**
     * 结束指定类名的Activity
     */
    public static void finishActivity(Class<?> cls){
        for (Activity activity : activityStack) {
            if(activity.getClass().equals(cls) ){
                finishActivity(activity);
            }
        }
    }
    /**
     * 结束所有Activity
     */
    public static void finishAllActivity(){
        for (int i = 0, size = activityStack.size(); i < size; i++){
            if (null != activityStack.get(i)){
                activityStack.get(i).finish();
            }
        }
        activityStack.clear();
    }
    
    /**
     * 方法名：finishAllActivityExceptCurActivity
     * 描述：解除除了当前Activity之外的其他Activity
     * @参数：   参数名  参数类型   参数描述
     * @返回值类型： void   
     * @创建时间：  2015年9月17日 
     * @创建者：韩创
     * @变更记录：2015年9月17日下午6:15:08 by
     */
    public static void finishAllActivityExceptCurActivity(){
    	Activity curActivity = currentActivity();
    	for (Activity activity : activityStack) {
            if(!activity.getClass().equals(curActivity) ){
                finishActivity(activity);
            }
        }
    }
    
    /**
     * 退出应用程序
     */
    @SuppressWarnings("deprecation")
    public static void AppExit(Context context) {
        try {
            finishAllActivity();
            ActivityManager activityMgr= (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
            activityMgr.restartPackage(context.getPackageName());
            //android.os.Process.killProcess(android.os.Process.myPid());
            System.exit(0);
        } catch (Exception e) {	}
    }

    /**
     * 方法名：reStartApp
     * 描述：重启应用
     * @参数：   参数名  参数类型   参数描述
     * @返回值类型： void
     * @创建时间：  2015年6月18日
     * @创建者：韩创
     * @变更记录：2015年6月18日下午1:29:22 by
     */
    public static void reStartApp(Context context){
    	CoreAppManager.AppExit(context);
        Intent intent = context.getPackageManager().getLaunchIntentForPackage(context.getPackageName());
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent); 
    }
	public static Stack<Activity> getActivityStack() {
		return activityStack;
	}
	public static void setActivityStack(Stack<Activity> activityStack) {
		CoreAppManager.activityStack = activityStack;
	}
    
}
