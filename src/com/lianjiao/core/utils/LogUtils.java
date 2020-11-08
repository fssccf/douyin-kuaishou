package com.lianjiao.core.utils;

import java.io.File;
import java.io.FileOutputStream;

import com.lianjiao.core.CoreConfig;

import android.os.Environment;
import android.util.Log;

/**
 * 日志输出类
 * Created by GA on 15-8-4.
 */
public class LogUtils {
    public static final String TAG = "TANG";

    private static boolean showDebug = true;
    
    public static String logfileFolder = Environment.getExternalStorageDirectory().getAbsolutePath() 
			+ File.separator + CoreConfig.folderRoot;
    
    public static String logfileName = logfileFolder
			+ File.separator + "log.txt";

    public static void v(String tag,String str){
        if(showDebug){
            Log.v(TAG, tag + "--Debug--" + str);
        }
    }

    public static void i(String tag,String str){
        if(showDebug){
            Log.i(tag, str);
        }
    }

    public static void i(String str){
        if(showDebug){
            Log.i("",str);
        }
    }

    public static void d(String tag,String str){
        if(showDebug){
            Log.d(TAG, tag+"--Debug--"+str);
        }
    }

    public static void e(String tag,String str){
        if(showDebug){
            Log.v(TAG, tag+"--Error--"+str);
        }

    }
    
    /** 
	 * 方法名：write2Log
	 * 描述： 向日志文件添加信息
	 * @参数：   参数名  参数类型   参数描述
	 * @返回值类型： void   
	 * @创建时间：  2014年6月13日 
	 * @创建者：zhangyao
	 * @变更记录：2014年6月13日下午5:32:30 by
	 */
	public static void write2LogFile(String message) {
		
		String content = "";		
		//TODO 创建文件夹，以及文件
		if(FileUtils.getFileSize(logfileName) > 1048576) {
			FileUtils.deleteFile(logfileName);
		}else{
			content = FileUtils.readFile(logfileName);
		}
		
		content += message;
		
		 File file = new File(logfileName); 
         try { 
             FileOutputStream fos = new FileOutputStream(file,true); 
             fos.write(content.getBytes());             
             fos.flush(); 
             fos.close(); 
         } catch (Exception e) { 
         } 
	}

}
