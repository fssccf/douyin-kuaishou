package com.lianjiao.core.activity;

import java.util.Stack;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.FrameLayout;

import com.lianjiao.core.R;
import com.lianjiao.core.app.CoreAppManager;
import com.lianjiao.core.fragment.BaseFragment;
import com.lianjiao.core.fragment.FragMain;
import com.lianjiao.core.frame.AppFrame;
import com.lianjiao.core.utils.LsToast;

public class MainActivity extends BaseFragmentActivity implements OnClickListener{
	
	/**
	 * fragment管理员
	 */
	private FragmentManager fragmentManager;
	
	private FragMain fragMain;
	
	private BaseFragment currentFragment;
	
	public static final String FRAG_MAIN_TAG = "main_frag";
	
	public static final String FRAG_OTHER_TAG = "other_frag";
	
	/**
	 * 回退是否退出程序
	 */
	private boolean isBack2Finlish = false;
	
	private FrameLayout appRootView;
	
	private Stack<BaseFragment> stackFrag = new Stack<BaseFragment>();
	
	public interface AppExitListener{
		/**
		 * 方法名：appExit
		 * 描述：主页面双击返回键退出应用监听
		 * @参数：   参数名  参数类型   参数描述
		 * @返回值类型： boolean 是否同意退出应用
		 * @创建时间：  2016年1月5日 
		 * @创建者：韩创
		 * @变更记录：2016年1月5日下午5:46:38 by
		 */
		public boolean appExit();
	}
	
	public AppExitListener appExitListener;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		appRootView = new FrameLayout(this);
		appRootView.setId(R.id.app_root_view);
		setContentView(appRootView);
		fragmentManager = getSupportFragmentManager();
		if(savedInstanceState == null){
			init();	
		}else{
			Fragment frag = fragmentManager.getFragment(savedInstanceState, FRAG_MAIN_TAG);
			Fragment ortherfrag = fragmentManager.getFragment(savedInstanceState, FRAG_OTHER_TAG);		
			if(frag != null){
				fragMain = (FragMain)frag;
			}
			
			if(ortherfrag != null){
				currentFragment = (BaseFragment)ortherfrag;
			}else{
				currentFragment = fragMain;
			}
		}		
	}
	
	private void init(){
		if(fragMain == null){
			fragMain = FragMain.getFragMain();
			addFrag(fragMain, FRAG_MAIN_TAG);
		}
	}

	/**
	 * 方法名：addFrag
	 * 描述：添加一个Fragenment到rootView
	 * @参数：   参数名  参数类型   参数描述
	 * @返回值类型： void   
	 * @创建时间：  2014年6月18日 
	 * @创建者：韩创
	 * @变更记录：2014年6月18日上午9:38:34 by
	 */
	public void addFrag(BaseFragment frag, String tag){
		if(frag == null){
			return;
		}
		FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.add(appRootView.getId(), frag , tag);
        transaction.commit();
        currentFragment = frag;
	}
	
	/**
	 * 方法名：showFrag
	 * 描述：显示一个fragment 到rootView
	 * @参数：   参数名  参数类型   参数描述
	 * @返回值类型： void   
	 * @创建时间：  2014年6月18日 
	 * @创建者：韩创
	 * @变更记录：2014年6月18日上午9:39:18 by
	 */
	@Override
	public void showFragPage(BaseFragment frag ){
		if(frag == null){
			return;
		}
		FragmentTransaction transaction = fragmentManager.beginTransaction();
		if(currentFragment == fragMain && fragMain != null){
			transaction.hide(fragMain);	
		}else if(currentFragment != null){//移除弹出层的Fragment
			//transaction.remove(currentFragment);
			transaction.hide(currentFragment);//TODO 原本这里是需要remove的，但是由于需要保存临时状态这里就需要隐藏了
		}
		if(frag == fragMain){//如果显示已经存在在栈中的界面，则把该界面所在位置之后的对象，全部弹出
			for(BaseFragment f:stackFrag){
				transaction.remove(f);
			}
			transaction.show(frag);
			stackFrag.clear();
		}else if(stackFrag.contains(frag)){
			transaction.show(frag);
			int pos = stackFrag.indexOf(frag);
			for(int i=pos+1;i<stackFrag.size();i++){
				stackFrag.remove(i);
			}
			//stackFrag.add(frag);//TODO 添加到回退栈里
		}else{
			if(frag != null){
				transaction.add(appRootView.getId(), frag , FRAG_OTHER_TAG);
				//TODO 添加到回退栈里
				stackFrag.add(frag);
			}
		}
		transaction.commit();
		currentFragment = frag;
	}
	
	@Override
	public void onBackPressed() {
		if(currentFragment != null && currentFragment.isAdded() && currentFragment.onBack()){
			//此处 什么都不要做，具体处理在fragment中进行
		}else{
			onBack();
		}
	}
	
	private void onBack() {
		if (isBack2Finlish) {
			// 退出应用
			exitApplication();
		} else {
			isBack2Finlish = true;
			new Thread() {
				public void run() {
					try {
						Thread.sleep(3000);
						isBack2Finlish = false;
					} catch (Exception e) {
					}
				}
			}.start();
			LsToast.show(getApplicationContext(), R.string.notice_exit_app);
		}
	}
	
	private void exitApplication(){
		if(AppFrame.getAppFrame().appExit()){
			CoreAppManager.AppExit(getApplicationContext());
		}else{
			//不退出应用
		}
	}
	
	@Override
	public void back2LastFrag(Bundle bundle) {
		back2MainFrag(bundle);
	}
	
	
	/** 
	 * 方法名：back2MainFrag
	 * 描述：返回 main fragment 
	 * @参数：   参数名  参数类型   参数描述
	 * @返回值类型： void   
	 * @创建时间：  2014年6月18日 
	 * @创建者：韩创
	 * @变更记录：2014年6月18日上午10:24:38 by
	 */
	public void back2MainFrag(Bundle info) {
		if(currentFragment != fragMain) {
			if(fragMain == null){
				fragMain = FragMain.getFragMain();	
				FragmentTransaction transaction = fragmentManager.beginTransaction();
				//transaction.setCustomAnimations(R.anim.leftin, R.anim.rightout);
				if(currentFragment != null){
					transaction.remove(currentFragment);
				}
		        transaction.add(appRootView.getId(),fragMain , FRAG_MAIN_TAG);
		        transaction.commit();
		        currentFragment = fragMain;
		        
			}else {
				//TODO 这里需要根据回退栈来控制回退
				BaseFragment laseFrag = currentFragment;
				FragmentTransaction transaction = fragmentManager.beginTransaction();
				//transaction.setCustomAnimations(R.anim.leftin, R.anim.rightout);
				transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE);
				if(laseFrag != null){
					transaction.remove(laseFrag);
					//TODO 移除栈中的对象
					stackFrag.remove(laseFrag);
				}
				//TODO 这里判断栈中是否为空
				if(stackFrag.size()<1){
					if(fragMain != null){
						transaction.show(fragMain);
						transaction.commit();
						currentFragment = fragMain;		        
						fragMain.refreshView();
					}
				}else{
					BaseFragment frag = stackFrag.lastElement();
					//transaction.add(appRootView.getId(), frag , FRAG_OTHER_TAG);
					transaction.show(frag);//TODO 这里是把上一个隐藏的show出来这样能保证最好的显示性能，但是比较耗费内存
					transaction.commit();
					currentFragment = frag;		        
					frag.refreshView();
				}
				
			}
		}else{
			onBack();
		}
	}

	@Override
	public void onClick(View v) {
		if(currentFragment!=null){
			currentFragment.onClick(v);
		}
		
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		if(currentFragment!=null){
			currentFragment.refreshView();
		}
	}
	
	public BaseFragment getCurrentFragment() {
		return currentFragment;
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if(currentFragment!=null){
			currentFragment.onActivityResult(requestCode, resultCode, data);
		}
	}
	
}
