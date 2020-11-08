package com.lianjiao.core.fragment;

import java.lang.reflect.Field;

import roboguice.fragment.RoboFragment;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;

import com.lianjiao.core.activity.BaseFragmentActivity;
import com.lianjiao.core.widget.LsTextView;

/**
 * @ //Fragment基类
 * RCreated by 韩创
 */
public abstract class BaseFragment extends RoboFragment implements OnClickListener{
	
	public final String TAG = this.getClass().getSimpleName(); 
	
	public Context mContext;
	
	public LsTextView title;
	
	public LsTextView titleLeft;
	
	public LsTextView titleRight;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mContext = getActivity();
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return onCoreCreateView(inflater,container,savedInstanceState);
	}
	
	public abstract View onCoreCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState);
	
	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		onCoreViewCreated(view, savedInstanceState);
	}
	
	public abstract void onCoreViewCreated(View view, Bundle savedInstanceState);
	
	public abstract void onClick(View v);
	
	public void showProgressDialog(){
		if(getActivity() != null ){
			if(getActivity() instanceof BaseFragmentActivity){
				((BaseFragmentActivity)getActivity()).showProgressDialog();
			}
		}
	}
	
	public void closeProgressDialog(){
		if(getActivity() != null ){
			if(getActivity() instanceof BaseFragmentActivity){
				((BaseFragmentActivity)getActivity()).closeProgressDialog();
			}
		}
	}
	
	/**
	 * 方法名：refreshView
	 * 描述：主动的刷新界面显示
	 * @参数：   参数名  参数类型   参数描述
	 * @返回值类型： void   
	 * @创建时间：  2014年11月11日 
	 * @创建者：Administrator
	 * @变更记录：2014年11月11日上午11:32:16 by
	 */
	public void refreshView(){
		
	}
	
	public boolean isAttached(){
    	return isAdded() && getActivity()!=null;
    }
	
	/**
	 * 方法名：<b>onBack</b><br>
	 * 描述：<b>在点击返回键（或者按钮）时要执行的操作</b><br>
	 * @返回值类型： <b>boolean   为true时事件处理到此方法结束，否则继续执行外部代码</b><br>
	 */
	public boolean onBack(){
		back2LastFrag(null);
		return true;
	}
	
	public void back2LastFrag(Bundle bundle){
		if(getActivity() != null && getActivity() instanceof BaseFragmentActivity){
			((BaseFragmentActivity)getActivity()).back2LastFrag(bundle);
		}
	}
	
	public void showFrag(BaseFragment frag){
		if(getActivity() != null && getActivity() instanceof BaseFragmentActivity){
			((BaseFragmentActivity)getActivity()).showFragPage(frag);	
		}
	}
	
	@Override  
    public void onDetach() {  
        super.onDetach();  
        try {  
            Field childFragmentManager = Fragment.class.getDeclaredField("mChildFragmentManager");  
            childFragmentManager.setAccessible(true);  
            childFragmentManager.set(this, null);  
  
        } catch (NoSuchFieldException e) {  
            throw new RuntimeException(e);  
        } catch (IllegalAccessException e) {  
            throw new RuntimeException(e);  
        } catch (Exception e) {
			e.printStackTrace();
		}
      
    }
	
	@Override
	public void onResume() {
		super.onResume();
	}
	
	@Override
	public void onPause() {
		super.onPause();
	}
	
	public void setTitle(String str) {
		if(this.title!=null){
			this.title.setText(str);
		}else{
			this.title = (LsTextView) getView().findViewWithTag("appTitle");
			if(this.title!=null)
			this.title.setText(str);
		}
		
	}
	
	public void setTitleLeftVisiable(boolean isVisiable,String str){
		if(this.titleLeft!=null){
			this.titleLeft.setVisibility(isVisiable?View.VISIBLE:View.INVISIBLE);
		}else{
			this.titleLeft = (LsTextView)getView().findViewWithTag("appTitleLeft");
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
			this.titleRight = (LsTextView)getView().findViewWithTag("appTitleRight");
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
