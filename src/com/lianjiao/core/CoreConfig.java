package com.lianjiao.core;

import com.lianjiao.core.frame.MenusPosition;

/**
 * 类名称：CoreConfig   
 * 类描述：   
 * @创建者：韩创    
 * @创建时间：2015年12月14日   
 * @变更记录：2015年12月14日下午4:53:14 by 记录变更人
 * 
 *  <br>
	//基础配置<br>
	CoreConfig.DBName = "tcg.db";//数据库名称<br>
	CoreConfig.DBVersion = 1;//数据库版本号<br>
	CoreConfig.FolderRoot = "testConfig";//SD存储根文件夹名称<br>
	<br>
	//创建数据库，并且建立表<br>
	DatabaseHelper dataHelper = DatabaseHelper.getHelper(context);<br>
	dataHelper.createTable(User.class);<br>
 */
public class CoreConfig {
	/**
	 * 数据库名字
	 */
	public static String DBName = "core.db";
	/**
	 * 数据库版本号
	 */
	public static int DBVersion = 1;
	/**
	 * 文件夹根目录
	 */
	public static String folderRoot = "core";
	
	/**
	 * 错误日志
	 */
	public static String log = "log.txt";
	
	/**
     * 是否为调试模式
     */
    public static boolean deBug = false;

    /**
     * 是否显示logcat的输出内容
     */
    public static boolean showDebug = true;
	/**
	 * 首页界面切换动画
	 */
    public static boolean changePageAnim = true;
    /**
     * 菜单位置默认底部
     */
    public static MenusPosition menuPos = MenusPosition.BOTTOM;
    /**
     * 不需要菜单的文字
     */
    public static boolean menusWithoutText = false;
    /**
     * 不需要菜单的图片
     */
    public static boolean menusWithoutImg = false;
    /**
     * 不需要菜单
     */
    public static boolean menusWithoutAll = false;
    

}









