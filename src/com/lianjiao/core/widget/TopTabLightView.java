package com.lianjiao.core.widget;


import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.RelativeLayout;

import com.lianjiao.core.R;
import com.lianjiao.core.utils.LogUtils;
import com.lianjiao.core.utils.LsViewUtil;
import com.nineoldandroids.animation.Animator;
import com.nineoldandroids.animation.AnimatorListenerAdapter;
import com.nineoldandroids.view.ViewHelper;
import com.nineoldandroids.view.ViewPropertyAnimator;

public class TopTabLightView extends RelativeLayout {

	private boolean isShowAnimation = true;
	
	private int selectColor = Color.rgb(77, 144, 255);
	
	private int selectIndex = 0;
	
	private int tabcount  = 2;
	
	private int tabwidth = 0;
	
	private int margin = 0;
	
	private float dicator_Scale = 1.0f;
	
	private int defPos = 0;
	
	private View lightView;
	
	private Context context;
	public TopTabLightView(Context context) {
		super(context);
		init(context);
	}
	
	public TopTabLightView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context);
	}
	
	public TopTabLightView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init(context);
	}
	
	
	private void init(Context context){
		this.context = context;
		View centreLine = new View(context);
		RelativeLayout.LayoutParams lineParams = new RelativeLayout.LayoutParams(LayoutParams.MATCH_PARENT, LsViewUtil.dip2px(getContext(), 1));
		lineParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, RelativeLayout.TRUE);
		centreLine.setBackgroundColor(context.getResources().getColor(R.color.split_line_color01));
		addView(centreLine, lineParams);
		
		ViewTreeObserver vto = this.getViewTreeObserver();
        vto.addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            public boolean onPreDraw() {
            	if(lightView == null && getWidth() > 1){
            		initLightView(getContext());	
            	}            	
                return true;
            }
        });
		
		
	}
	
	
	private void initLightView(Context context){
		tabwidth = getWidth() / tabcount;		
		margin = (int)(tabwidth * (1 - dicator_Scale)/2);
		int lwidth = (int)(tabwidth * dicator_Scale);
		
		
//		if(isInEditMode())
		LogUtils.v("控件初始画", "tab宽度："+tabwidth +"   地标宽度 ："+ lwidth+"   margin："+ margin);
		
		lightView= new View(context);
		RelativeLayout.LayoutParams lightParams = new RelativeLayout.LayoutParams(lwidth, LayoutParams.MATCH_PARENT);
		
		LogUtils.v("TopTabLightView", "<<<<<<<  Light width:"+ lwidth +"    Margin Left:" + margin);
		lightParams.addRule(RelativeLayout.CENTER_VERTICAL, RelativeLayout.TRUE);
		lightView.setBackgroundColor(selectColor);
		addView(lightView, lightParams);
		lightView.setX(margin + defPos * tabwidth);
	}
	
	public void setTobTabCount(int count){
		tabcount = count;
		reSetLightView();
	}
	
	public void setCount(int count){
		setTobTabCount(count);
	}
	
	private void reSetLightView(){
		if(lightView != null){
			
			tabwidth = getWidth() / tabcount;
			margin = (int)(tabwidth * (1 - dicator_Scale)/2);
			int lwidth = (int)(tabwidth * dicator_Scale);	
			
//			if(isInEditMode())
//			LogUtils.v("控件初始刷新", "tab宽度："+tabwidth +"   地标宽度 ："+ lwidth+"   margin："+ margin);
			RelativeLayout.LayoutParams lightParams = new RelativeLayout.LayoutParams(lwidth, LayoutParams.MATCH_PARENT);
			
			lightView.setBackgroundColor(selectColor);
			lightView.setLayoutParams(lightParams);
			lightView.setX(margin + defPos * tabwidth);
			//LogUtils.v("TopTabLightView", ">>>>>>  Light width:"+ lwidth +"    Margin Left:" + margin);
		}        
	}
	
	public void setLightColor(int color){
		if(lightView != null){
			lightView.setBackgroundColor(color);	
		}
	}
	private boolean isAnimation = false;
	public void selectTopTab(int index){
		if(lightView == null){
			LogUtils.i("lightView 为空了");
			init(context);
			return ;
		}
		LogUtils.i("getWidth() =  "+getWidth()+" tabcount = "+tabcount);
		tabwidth = getWidth() / tabcount;
		defPos = index;
		if(!isAnimation){
			selectIndex = index;
			int move = margin + index * tabwidth;
			if(isShowAnimation){
				isAnimation = true;
				ViewPropertyAnimator.animate(lightView) 
		        .translationX(move)//X轴方向的移动距离 	        
		        .setDuration(240)  
		        .setListener(new AnimatorListenerAdapter() {  
		            @Override  
		            public void onAnimationEnd(Animator animation) {  
		            	isAnimation = false;
		            }  
		        });
			}else{
				ViewHelper.setTranslationX(lightView, move);
			}
		}	
	}
	
	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		//init(this.context);
	}
	
	public void setShowAnimation(boolean isshow){
		this.isShowAnimation = isshow;
	}
	
	public boolean isShowAnimation(){		
		return isShowAnimation;
	}

	public int getDefPos() {
		return defPos;
	}

	public void setDefPos(int defPos) {
		this.defPos = defPos;
	}
	

}
