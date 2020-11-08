package com.lianjiao.core.fragment;

import java.util.Stack;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;

import com.lianjiao.core.CoreConfig;
import com.lianjiao.core.R;
import com.lianjiao.core.app.CoreAppManager;
import com.lianjiao.core.frame.AppFrame;
import com.lianjiao.core.frame.AppMenu;
import com.lianjiao.core.frame.MenusPosition;
import com.lianjiao.core.utils.LogUtils;
import com.lianjiao.core.utils.StringUtil;
import com.lianjiao.core.widget.LsTextView;
/**
 * 类名称：Frag_Main   
 * 类描述：主Activity中的主Fragment
 * 特别声明：由于是library工程所以生成的R.id不是final类型所以不能使用注解绑定View
 * @创建者：韩创    
 * @创建时间：2015年12月17日   
 * @变更记录：2015年12月17日上午11:03:02 by 记录变更人
 */
public class FragMain extends BaseFragment implements OnClickListener{
	
	public static FragMain main;
	
	private BaseFragment currentFragment;
	
	private int curPage;
	
	private boolean rightIn = true;
	/**
	 * 底部菜单按钮容器
	 */
	private LinearLayout layoutFoot;
	private LayoutInflater inflater;
	private View spliteLineView;
	private DrawerLayout drawerLayout;//侧滑抽屉
	
	private Stack<BaseFragment> stackFrag = new Stack<BaseFragment>();
	
	public interface ChangePageListener {
		/**
		 * 方法名：changePage<br>
		 * 描述：主界面切换监听<br>
		 * @参数：   <br>what：切换到哪一个界面了<br>frag：切换后的界面对象<br>
		 * @返回值类型： void   
		 * @创建时间：  2015年12月24日 
		 * @创建者：韩创
		 * @变更记录：2015年12月24日下午4:30:29 by
		 */
		public void changePage(int what,BaseFragment frag);
	}
	
	/**
	 * 私有化FragMain构造函数
	* 构造器名: 
	* 描述: 
	* @参数：
	 */
	public FragMain(){}
	/**
	 * 单例模式
	 * 方法名：getFragMain
	 * 描述：
	 * @参数：   参数名  参数类型   参数描述
	 * @返回值类型： FragMain   
	 * @创建时间：  2015年12月21日 
	 * @创建者：韩创
	 * @变更记录：2015年12月21日上午10:50:29 by
	 */
	public static FragMain getFragMain(){
		if(main == null){
			main = new FragMain();
		}
		return main;
	}
	
	
	
	@Override
	public View onCoreCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = null;
		switch(CoreConfig.menuPos){
		case TOP:
			view = inflater.inflate(R.layout.frag_main_top, null);
			layoutFoot = (LinearLayout) view.findViewById(R.id.frag_main_foot);
			spliteLineView = view.findViewById(R.id.frag_main_foot_splite);
			break;
		case BOTTOM:
			view = inflater.inflate(R.layout.frag_main, null);
			layoutFoot = (LinearLayout) view.findViewById(R.id.frag_main_foot);
			spliteLineView = view.findViewById(R.id.frag_main_foot_splite);
			break;
		case LEFT:
			view = inflater.inflate(R.layout.frag_main_left, null);
			layoutFoot = (LinearLayout) view.findViewById(R.id.frag_main_foot);
			drawerLayout = (DrawerLayout) view.findViewById(R.id.main_drawer_layout);
			break;
		case RIGHT:
			view = inflater.inflate(R.layout.frag_main_right, null);
			layoutFoot = (LinearLayout) view.findViewById(R.id.frag_main_foot);
			drawerLayout = (DrawerLayout) view.findViewById(R.id.main_drawer_layout);
			break;
		default:
			view = inflater.inflate(R.layout.frag_main, null);
			layoutFoot = (LinearLayout) view.findViewById(R.id.frag_main_foot);
			spliteLineView = view.findViewById(R.id.frag_main_foot_splite);
			break;
		}
		this.inflater = inflater;
		return view;
	}
	@Override
	public void onCoreViewCreated(View view, Bundle savedInstanceState) {
		try{
			initView();
		}catch(Exception e){
			e.printStackTrace();
			CoreAppManager.reStartApp(mContext);
		}
		main = this;
	}

	
	private void initView(){
		initPage();
		initMenus();
		//底部菜单布局参数设置
		if(AppFrame.getAppFrame().footLayoutParams!=null && (CoreConfig.menuPos==MenusPosition.TOP || CoreConfig.menuPos==MenusPosition.BOTTOM)){
			layoutFoot.setLayoutParams(AppFrame.getAppFrame().footLayoutParams);
		}
		//侧面菜单布局参数设置
		if(AppFrame.getAppFrame().leftLayoutParams!=null && (CoreConfig.menuPos==MenusPosition.LEFT || CoreConfig.menuPos==MenusPosition.RIGHT)){
			drawerLayout.setLayoutParams(AppFrame.getAppFrame().leftLayoutParams);
		}
		//控制底部菜单上面的分割线是否显示
		if(null!=spliteLineView && (CoreConfig.menuPos==MenusPosition.TOP || CoreConfig.menuPos==MenusPosition.BOTTOM)){
			if(AppFrame.getAppFrame().isShowSplitLine){
				spliteLineView.setVisibility(View.VISIBLE);
			}else{
				spliteLineView.setVisibility(View.GONE);
			}
		}
	}
	
	/**
	 * 方法名：initPage
	 * 描述：初始化界面
	 * @参数：   参数名  参数类型   参数描述
	 * @返回值类型： void   
	 * @创建时间：  2015年12月18日 
	 * @创建者：韩创
	 * @变更记录：2015年12月18日下午4:00:15 by
	 */
	private void initPage(){
		//初始化fragment首页界面
		curPage = 0;//默认显示第一个位置的page页面
		currentFragment = AppFrame.getAppFrame().pages.get(curPage).getFrag();
		changeCurFrag();
	}
	/**
	 * 方法名：initMenus
	 * 描述：初始化菜单
	 * @参数：   参数名  参数类型   参数描述
	 * @返回值类型： void   
	 * @创建时间：  2015年12月18日 
	 * @创建者：韩创
	 * @变更记录：2015年12月18日下午3:58:00 by
	 */
	private void initMenus(){
		//初始化菜单选项
		if(CoreConfig.menusWithoutAll){
			layoutFoot.setVisibility(View.GONE);
		}
		LayoutParams params = null;
		if(CoreConfig.menuPos == MenusPosition.LEFT||CoreConfig.menuPos == MenusPosition.RIGHT){
			params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT);
		}else{
			params = new LinearLayout.LayoutParams(0,LinearLayout.LayoutParams.MATCH_PARENT);
			params.weight = 1;
		}
		int which = 0;
		layoutFoot.removeAllViews();
		for(AppMenu menu:AppFrame.getAppFrame().menus){
			View view = null;
			if(CoreConfig.menuPos == MenusPosition.LEFT){
				view = inflater.inflate(R.layout.item_foot_menu_left, null);
			}else if(CoreConfig.menuPos == MenusPosition.RIGHT){
				view = inflater.inflate(R.layout.item_foot_menu_left, null);
			}else{
				view = inflater.inflate(R.layout.item_foot_menu, null);
			}
			ImageView img = (ImageView) view.findViewById(R.id.bottom_image);
			LsTextView text = (LsTextView) view.findViewById(R.id.bottom_text);
			LsTextView msg = (LsTextView) view.findViewById(R.id.bottom_msg);
			msg.setVisibility(View.INVISIBLE);
			text.setText(String.valueOf(menu.getName()));
			if(menu.getImgnormal()>0){
				img.setImageResource(menu.getImgnormal());
			}
			view.setTag(which++);
			layoutFoot.addView(view,params);
			//按钮单击事件
			view.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					int pos = Integer.parseInt(v.getTag().toString());
					whichPage(pos);
					changeMenuStatus(pos);
				}
			});
			if(CoreConfig.menusWithoutText){
				text.setVisibility(View.GONE);
			}
			if(CoreConfig.menusWithoutImg){
				img.setVisibility(View.GONE);
			}
		}
		changeMenuStatus(0);
	}
	
	@Override
	public void onClick(View v) {
		if(currentFragment!=null){
			currentFragment.onClick(v);
		}
		
	}
	
	@Override
	public void refreshView() {
		super.refreshView();
		if(currentFragment != null){
			currentFragment.refreshView();
		}
	}
	
	public BaseFragment getCurrentFragment() {
		return currentFragment;
	}
	/**
	 * 方法名：changeMenuStatus
	 * 描述：改变按钮选中状态，只改变图片的选中状态
	 * @参数：   参数名  参数类型   参数描述
	 * @返回值类型： void   
	 * @创建时间：  2015年12月18日 
	 * @创建者：韩创
	 * @变更记录：2015年12月18日上午11:01:16 by
	 * 
	 */
	private void changeMenuStatus(int pos){
		for(int i=0;i<layoutFoot.getChildCount();i++){
			View v = layoutFoot.getChildAt(i);
			ImageView img = (ImageView) v.findViewById(R.id.bottom_image);
			if(AppFrame.getAppFrame().menus.get(i).getImgnormal()>0){
				img.setImageResource(AppFrame.getAppFrame().menus.get(i).getImgnormal());
			}else{
				img.setImageResource(R.drawable.ic_launcher);
			}
		}
		ImageView img = (ImageView)layoutFoot.getChildAt(pos).findViewById(R.id.bottom_image);
		if(AppFrame.getAppFrame().menus.get(pos).getImgselected()>0){
			img.setImageResource(AppFrame.getAppFrame().menus.get(pos).getImgselected());
		}
		AppFrame.getAppFrame().changePage(pos, currentFragment);
	}
	
	/**
	 * 方法名：changeCurFrag
	 * 描述：改变当前页面;这里没有使用replace替换Fragment是因为要防止Fragment重新调用onCreateView方法带来的开发上的烦恼
	 * @参数：   参数名  参数类型   参数描述
	 * @返回值类型： void   
	 * @创建时间：  2015年12月18日 
	 * @创建者：韩创
	 * @变更记录：2015年12月18日上午11:00:47 by
	 */
	private void changeCurFrag(){
		FragmentManager fragmentManager = getChildFragmentManager();		
		FragmentTransaction  transation = fragmentManager.beginTransaction();
		if(CoreConfig.changePageAnim && stackFrag.size()>0){
			if(rightIn){
				transation.setCustomAnimations(R.anim.rightin,R.anim.leftout);
			}else{
				transation.setCustomAnimations(R.anim.leftin,R.anim.rightout);
			}
		}
		for(BaseFragment f:stackFrag){
			if(f!=currentFragment)
			transation.hide(f);
		}
		if(stackFrag.contains(currentFragment)){
			transation.show(currentFragment).commit();
			currentFragment.refreshView();
		}else{
			transation.add(R.id.frag_main_layout,currentFragment);
			transation.commit();
			stackFrag.add(currentFragment);
		}
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
		rightIn = true;
		curPage = ++curPage%AppFrame.getAppFrame().pages.size();
		currentFragment = AppFrame.getAppFrame().pages.get(curPage).getFrag();
		changeCurFrag();
		changeMenuStatus(curPage);
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
		rightIn = false;
		int pos = --curPage%AppFrame.getAppFrame().pages.size();
		pos = pos<0?AppFrame.getAppFrame().pages.size()-1:pos;
		curPage = pos;
		currentFragment = AppFrame.getAppFrame().pages.get(curPage).getFrag();
		changeCurFrag();
		changeMenuStatus(curPage);
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
		if(pos>curPage){
			rightIn = true;
		}else{
			rightIn = false;
		}
		curPage = pos;
		currentFragment = AppFrame.getAppFrame().pages.get(curPage%AppFrame.getAppFrame().pages.size()).getFrag();
		changeCurFrag();
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
		for(int i=0;i<AppFrame.getAppFrame().pages.size();i++){
			if(clazz == AppFrame.getAppFrame().pages.get(i).getFrag().getClass()){
				whichPage(i);
			}
		}
	}
	/**
	 * 方法名：getFootViewLayout
	 * 描述：获取底部视图
	 * @参数：   参数名  参数类型   参数描述
	 * @返回值类型： LinearLayout   
	 * @创建时间：  2015年12月21日 
	 * @创建者：韩创
	 * @变更记录：2015年12月21日下午2:07:35 by
	 */
	public LinearLayout getFootViewLayout(){
		return layoutFoot;
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
		if(what<layoutFoot.getChildCount() && !StringUtil.isEmpty(count)){
			LsTextView msg = (LsTextView) layoutFoot.getChildAt(what).findViewById(R.id.bottom_msg);
			msg.setText(String.valueOf(count));
			msg.setVisibility(View.VISIBLE);
		}else{
			LogUtils.e(TAG, "数组越界，消息设置参数错误");
		}
	}
	public void setMessageCount(Class<?> clazz,String count){
		int what = -1;
		for(int i=0;i<AppFrame.getAppFrame().pages.size();i++){
			if(clazz == AppFrame.getAppFrame().pages.get(i).getFrag().getClass()){
				what = i;
				break;
			}
		}
		if(what==-1){
			LogUtils.e(TAG, "对应界面未找到，消息设置参数错误");
		}else{
			setMessageCount(what,count);
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
		if(what<layoutFoot.getChildCount()){
			LsTextView msg = (LsTextView) layoutFoot.getChildAt(what).findViewById(R.id.bottom_msg);
			msg.setVisibility(isShowTip?View.VISIBLE:View.INVISIBLE);
		}else{
			LogUtils.e(TAG, "数组越界，消息设置参数错误");
		}
	}
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if(currentFragment!=null){
			currentFragment.onActivityResult(requestCode, resultCode, data);
		}
	}
//	public ChangePageListener getChangePageListener() {
//		return changePageListener;
//	}
//	public void setChangePageListener(ChangePageListener changePageListener) {
//		this.changePageListener = changePageListener;
//	}
	

}
