package com.lianjiao.core.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.widget.ImageView;

import com.lianjiao.core.R;
import com.nineoldandroids.animation.Animator;
import com.nineoldandroids.animation.AnimatorListenerAdapter;
import com.nineoldandroids.animation.ValueAnimator;

public class RoundScoreView extends ImageView {

	private float strokeWidth = 36;//36dp
	
	private int backColor = Color.rgb(204, 204, 204); // 
	private int floatColor = Color.rgb(36, 195, 217); //
	
	private float scoreValue = 0.0f; 
	private float topValue  = 100.0f;
	private float scoreAngle = 0.0f; 
	
	private float orgin_angle = 90.0f;
	
	private float arc_y = 0.0f;
	
	private float padding  = 0.0f;//10dp
	
	private Paint backPaint, floatPaint;
	private RectF rectf;
	
	
	public RoundScoreView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		init(context, attrs);
	}

	public RoundScoreView(Context context, AttributeSet attrs) { 
        super(context, attrs);
        init(context, attrs);
    } 

	public RoundScoreView(Context context) {
		super(context);		
		init(context, null);
	}

	public void init(Context context, AttributeSet attrs){
		
		strokeWidth = getResources().getDimension(R.dimen.score_stroke_width);
		padding = getResources().getDimension(R.dimen.app_page_padding);
		
		backPaint = new Paint();
		backPaint.setAntiAlias(true);
		backPaint.setColor(backColor);		
		backPaint.setStrokeWidth(strokeWidth);		
		backPaint.setStyle(Style.STROKE);
		
		floatPaint = new Paint();
		floatPaint.setAntiAlias(true);
		floatPaint.setColor(floatColor);		
		floatPaint.setStrokeWidth(strokeWidth);		
		floatPaint.setStyle(Style.STROKE);	
		
		
	}
	
	
	@Override
	protected void onDraw(Canvas c) {	
		super.onDraw(c);
		
		backPaint.setColor(backColor);
		floatPaint.setColor(floatColor);
		
		float w = getWidth() > getHeight() ? getHeight() : getWidth();
		
		float padW = padding + strokeWidth/2;
		
		if(rectf == null){
			rectf = new RectF(padW, padW, w-padW, w-padW);
		}
		
		c.drawArc(rectf, orgin_angle, 360, false, backPaint);
		c.drawArc(rectf, orgin_angle, arc_y, false, floatPaint);		
		
	}
	
	boolean isAmion = false;
	public void setScoreValue(float value, final float total){
		if(value<0){
			value = 0;
		}
		if(value>total){
			value = total;
		}
		if(isAmion){
			return;
		}else{
			if(Math.abs(value-this.scoreValue)<2){//变化小于2g不使用动画，提高app和饭盒通讯相应速度
				changeScore(value, total);
				return;
			}
		}
		ValueAnimator animator;//高度变化
		int animatorDuration = 1500;//动画耗时
		
		
		animator = ValueAnimator.ofFloat(this.scoreValue, value).setDuration(animatorDuration);
		
		animator.start();
		isAmion = true;
		
		animator.addListener(new AnimatorListenerAdapter() {
			@Override
			public void onAnimationEnd(Animator animation) {
				isAmion = false;
			}
		});
		
		animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
			@Override
			public void onAnimationUpdate(ValueAnimator valueAnimator) {
				
				float stmp = Float.parseFloat(valueAnimator.getAnimatedValue().toString()) ;
				changeScore(stmp, total);
			}
		});
	}
	
	public void changeScore(float value, float total){
		if(total > 0 && total > value){
			scoreValue = value;
			topValue = total;
			scoreAngle = 360 * (value / total);
			arc_y = scoreAngle;
			postInvalidate();
		}else if(total > 0 && total <= value){
			scoreValue = value;
			topValue = total;
			scoreAngle = 360 ;
			arc_y = scoreAngle;
			postInvalidate();
		}else{
			scoreValue = 0.0f;			
			scoreAngle = 0 ;
			arc_y = scoreAngle;
			postInvalidate();
		}
	}
	
	public void setScoreValue(float value){
		if(value >= 0.0f && value <= 1.0f){
			scoreValue = value;
			topValue = 1.0f;
			scoreAngle = 360 * value;
			arc_y = scoreAngle;			
			postInvalidate();
		}else if(value < 0.0f){
			scoreValue = 0.0f;
			topValue = 1.0f;
			scoreAngle = 0.0f;
			arc_y = scoreAngle;			
			postInvalidate();
		}else{
			scoreValue = value;
			topValue = 1.0f;
			scoreAngle = 360 ;
			arc_y = scoreAngle;			
			postInvalidate();
		}
	}
	
	public void setCircleStrokeScale(float scale){
		if(scale > 0 ){
			strokeWidth = 36;
			strokeWidth = strokeWidth * scale;	
		}		
		
		if(backPaint != null && floatPaint != null){
			backPaint.setStrokeWidth(strokeWidth);				
			floatPaint.setStrokeWidth(strokeWidth);	
		}
	}
	
	public void setFloatColor(int color){
		this.floatColor = color;
	}
	
	public void setBackColor(int color){
		this.backColor = color;
	}

	public float getScoreValue() {
		return scoreValue;
	}

	public float getOrgin_angle() {
		return orgin_angle;
	}

	public void setOrgin_angle(float orgin_angle) {
		this.orgin_angle = orgin_angle;
	}
	
	
}
