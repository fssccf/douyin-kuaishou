package com.lianjiao.core.frame;

import java.util.List;

import android.support.v4.widget.DrawerLayout;
import android.view.View;
import android.widget.LinearLayout;

import com.lianjiao.core.R;
import com.lianjiao.core.activity.MainActivity.AppExitListener;
import com.lianjiao.core.fragment.BaseFragment;
import com.lianjiao.core.fragment.FragMain;
import com.lianjiao.core.fragment.FragMain.ChangePageListener;
import com.lianjiao.core.utils.LogUtils;
import com.lianjiao.core.widget.LsTextView;

public class AppFrame implements ChangePageListener,AppExitListener{
	private String TAG = this.getClass().getSimpleName();
	private static AppFrame appFrame;
	/**
	 * 应用菜单
	 */
	public List<AppMenu> menus;
	/**
	 * 应用界面
	 */
	public List<AppPage> pages;
	/**
	 * 页面底部布局参数，当菜单选择在下边或者上边的时候
	 */
	public LinearLayout.LayoutParams footLayoutParams;
	/**
	 * 页面侧面布局参数,当选择菜单在左边或者右边的时候
	 */
	public DrawerLayout.LayoutParams leftLayoutParams;
	/**
	 * 是否显示底部线条
	 */
	public boolean isShowSplitLine = true;
	/**
	 * 主界面切换监听
	 */
	public ChangePageListener changePageListener;
	/**
	 * 应用主界面双击返回键退出应用监听
	 */
	public AppExitListener appExitListener;
	/**
	 * 私有化构成方法
	 * 构造器名: AppFrame
	 * 描述: 不允许外部调用
	 * @参数：
	 */
	private AppFrame(){
		appFrame = this;
	}
	/**
	 * 方法名：getAppFrame
	 * 描述：单例模式
	 * @参数：   参数名  参数类型   参数描述
	 * @返回值类型： AppFrame   
	 * @创建时间：  2015年12月17日 
	 * @创建者：韩创
	 * @变更记录：2015年12月17日上午10:43:12 by
	 */
	public static AppFrame getAppFrame(){
		if(appFrame == null){
			appFrame = new AppFrame();
		}
		return appFrame;
	}
	/**
	 * 方法名：createAppFrame
	 * 描述：一次性创建应用框架的各个界面
	 * @参数：   参数名  参数类型   参数描述
	 * @返回值类型： void   
	 * @创建时间：  2015年12月18日 
	 * @创建者：韩创
	 * @变更记录：2015年12月18日下午2:01:08 by
	 */
	public void createAppFrame(List<AppMenu> menus,List<AppPage> pages){
		//校验应用初始化的合法性
		if(menus==null || pages==null || menus.size()!=pages.size() || menus.size()<1){
			try {
				throw new Exception("应用界面初始化失败，menus菜单和pages页面不能为null，并且他们的size要大与0，并且他们两个的size要相等");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		this.menus = menus;
		this.pages = pages;
	}
	
	/**
	 * 方法名：nextPage
	 * 描述：跳转到下一个页面（循环制度）
	 * @参数：   参数名  参数类型   参数描述
	 * @返回值类型： void   
	 * @创建时间：  2015年12月17日 
	 * @创建者：韩创
	 * @变更记录：2015年12月17日下午3:01:24 by
	 */
	public void nextPage(){
		if(FragMain.main!=null){
			FragMain.main.nextPage();
		}else{
			LogUtils.e(TAG, "Frag_Main is null");
		}
	}
	/**
	 * 方法名：previousPage
	 * 描述：跳转到上一个页面（循环制度）
	 * @参数：   参数名  参数类型   参数描述
	 * @返回值类型： void   
	 * @创建时间：  2015年12月17日 
	 * @创建者：韩创
	 * @变更记录：2015年12月17日下午3:01:36 by
	 */
	public void previousPage(){
		if(FragMain.main!=null){
			FragMain.main.previousPage();
		}else{
			LogUtils.e(TAG, "Frag_Main is null");
		}
	}
	
	/**
	 * 方法名：whichPage
	 * 描述：某个页面
	 * @参数：   pos在listPage中的位置
	 * @返回值类型： void   
	 * @创建时间：  2015年12月17日 
	 * @创建者：韩创
	 * @变更记录：2015年12月17日下午3:01:46 by
	 */
	public void whichPage(int pos){
		if(FragMain.main!=null){
			FragMain.main.whichPage(pos);
		}else{
			LogUtils.e(TAG, "Frag_Main is null");
		}
	}
	/**
	 * 方法名：whichPage
	 * 描述：页面类型根据页面类型去自己判断哪个界面
	 * @参数：   参数名  参数类型   参数描述
	 * @返回值类型： void   
	 * @创建时间：  2015年12月17日 
	 * @创建者：韩创
	 * @变更记录：2015年12月17日下午3:02:46 by
	 */
	public void whichPage(Class<?> clazz){
		if(FragMain.main!=null){
			FragMain.main.whichPage(clazz);
		}else{
			LogUtils.e(TAG, "Frag_Main is null");
		}
	}
	
	/**
	 * 方法名：setMessageCount
	 * 描述：设置小红点；设置后默认就会显示小红点，不用再去调用isShowTip方法
	 * @参数：    what：第几个菜单的小红点<br>count：显示内容
	 * @返回值类型： void   
	 * @创建时间：  2015年12月24日 
	 * @创建者：韩创
	 * @变更记录：2015年12月24日下午4:01:03 by
	 */
	public void setMessageCount(int what,String count){
		if(FragMain.main!=null){
			FragMain.main.setMessageCount(what,count);
		}else{
			LogUtils.e(TAG, "Frag_Main is null");
		}
	}
	/**
	 * 方法名：setMessageCount
	 * 描述：也可以通过主界面的类型反射其位置设置
	 * @参数：   参数名  参数类型   参数描述
	 * @返回值类型： void   
	 * @创建时间：  2015年12月24日 
	 * @创建者：韩创
	 * @变更记录：2015年12月24日下午4:18:19 by
	 */
	public void setMessageCount(Class<?> clazz,String count){
		if(FragMain.main!=null){
			FragMain.main.setMessageCount(clazz,count);
		}else{
			LogUtils.e(TAG, "Frag_Main is null");
		}
	}
	/**
	 * 方法名：isShowMsgTip
	 * 描述：是否显示小红点
	 * @参数：   what：第几个菜单的小红点<br>isShowTip：是否显示
	 * @返回值类型： void   
	 * @创建时间：  2015年12月24日 
	 * @创建者：韩创
	 * @变更记录：2015年12月24日下午4:06:54 by
	 */
	public void isShowMsgTip(int what,boolean isShowTip){
		if(FragMain.main!=null){
			FragMain.main.isShowMsgTip(what,isShowTip);
		}else{
			LogUtils.e(TAG, "Frag_Main is null");
		}
	}
	
	
	
	@Override
	public void changePage(int what, BaseFragment frag) {
		if(changePageListener!=null){
			changePageListener.changePage(what, frag);
		}
	}
	@Override
	public boolean appExit() {
		if(appExitListener!=null){
			return appExitListener.appExit();
		}else{
			return true;
		}
	}
	
}
