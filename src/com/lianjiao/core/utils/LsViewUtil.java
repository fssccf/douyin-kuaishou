/**
 * 项目名称：Leshi_Android_Client_1.0 
 * 所属包名: com.leshi.happytime.widget.util
 * 文件名		：LsViewUtil.java
 * 创建日期	：2014年6月12日
 * Copyright (c)  西安西安乐食
 * All rights reserved.
 */
package com.lianjiao.core.utils;

import android.content.Context;
import android.graphics.Typeface;
import android.text.TextPaint;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.ListAdapter;
import android.widget.ListView;

/**
 * 类名称：LsViewUtil   
 * 类描述：   View 相关工具类
 * @创建者：zhangyao     
 * @创建时间：2014年6月12日
 * @变更记录：2014年6月12日上午9:25:11 by 记录变更人
 */
public class LsViewUtil {

	private static Typeface appTypefaceZh;
	private static Typeface appTypefaceEn;
	
	private static Typeface appTypeface33;
	private static Typeface appTypeface35;
	private static Typeface appTypeface53;
	
	public static int ScreenWidth,ScreenHidth;
	
	public static Typeface getTypefaceZh(Context context){
		
		if(appTypefaceZh == null){
			appTypefaceZh = Typeface.createFromAsset (context.getAssets() , "fonts/en53.otf");;//Typeface.createFromAsset (context.getAssets() , "fonts/zh.otf");
		}
		return appTypefaceZh;
	}
	
	public static Typeface getTypefaceEn(Context context){
		
		if(appTypefaceEn == null){
			appTypefaceEn = Typeface.createFromAsset (context.getAssets() , "fonts/en_arial.ttf");
		}
		return appTypefaceEn;
	}
	
	public static Typeface getTypeface53(Context context){
		
		if(appTypeface53 == null){
			appTypeface53 = Typeface.createFromAsset (context.getAssets() , "fonts/en53.otf");
		}
		return appTypeface53;
	}
	
	public static Typeface getTypeface33(Context context){
		
		if(appTypeface33 == null){
			appTypeface33 = Typeface.createFromAsset (context.getAssets() , "fonts/en33.otf");
		}
		return appTypeface33;
	}

	public static Typeface getTypeface35(Context context){
		
		if(appTypeface35 == null){
			appTypeface35 = Typeface.createFromAsset (context.getAssets() , "fonts/en35.otf");
		}
		return appTypeface35;
	}
	
	/** 
	 * 方法名：getTypeface
	 * 描述：字体样式
	 * @参数：<br/>  
	 *  zh 中文<br/>
	 *  en53 英文，数字，粗字体使用 英文53<br/>
	 * 	en35 英文，数字，细字体使用英文35<br/>
	 * 	en33 日历字体使用 英文33 / 时间和日期使用 英文33<br/> 
	 * @返回值类型： Typeface   
	 */
	public static Typeface getTypeface(Context context,String type){
		if(type == null){
			return  getDefaultTypeface(context);	
		}else if(type.equals("zh")){
			return  getTypefaceZh(context);
		}else if(type.equals("en")){
			return  getTypefaceEn(context);
		}else if(type.equals("en53")){
			return  getTypeface53(context);
		}else if(type.equals("en35")){
			return  getTypeface35(context);
		}else if(type.equals("en33")){
			return  getTypeface33(context);
		}else{
			return  getDefaultTypeface(context);	
		}
		
	}
	
	public static Typeface getDefaultTypeface(Context context){
		//String lcal = Util.getLocalLanguage(context);
//		if("en".equals(lcal)){
//			return  getTypefaceEn(context);
//		}else{
//			return  getTypefaceZh(context);
//		}	
		return  getTypefaceZh(context);
	}
	
	/** 
	 * 方法名：getTypeface
	 * 描述：字体样式
	 * @参数：<br/>
	 * 		0  中文<br/>
	 * 		3  英文，数字，粗字体使用 英文53<br/>
	 * 		2  英文，数字，细字体使用英文35<br/>
	 * 		1  日历字体使用 英文33 / 时间和日期使用 英文33<br/> 
	 * @返回值类型： Typeface   
	 */
	public static Typeface getTypeface1(Context context,int type){
		if(type == 0){
			return  getDefaultTypeface(context);			
		}else if(type == 3){
			return  getTypeface53(context);
		}else if(type == 2){
			return  getTypeface35(context);
		}else if(type == 1){
			return  getTypeface33(context);
		}else{
			return  getTypefaceZh(context);
		}
		
	}
	
	/**
	 * 测量这个view，最后通过getMeasuredWidth()获取宽度和高度.
	 *
	 * @param v 要测量的view
	 * @return 测量过的view
	 */
	public static void measureView(View v){
		if(v == null){
			return;
		}
		int w = View.MeasureSpec.makeMeasureSpec(0,View.MeasureSpec.UNSPECIFIED); 
	    int h = View.MeasureSpec.makeMeasureSpec(0,View.MeasureSpec.UNSPECIFIED);
	    v.measure(w, h);
	}
	
	/**
	 * 描述：根据分辨率获得字体大小.
	 *
	 * @param screenWidth the screen width
	 * @param screenHeight the screen height
	 * @param textSize the text size
	 * @return the int
	 */
	public static int resizeTextSize(int screenWidth,int screenHeight,int textSize){
		float ratio =  1;
		try {
			float ratioWidth = (float)screenWidth / 480; 
			float ratioHeight = (float)screenHeight / 800; 
			ratio = Math.min(ratioWidth, ratioHeight); 
		} catch (Exception e) {
		}
		return Math.round(textSize * ratio);
	}
	
	/**
	 * 
	 * 描述：dip转换为px
	 * @param context
	 * @param dipValue
	 * @return
	 * @throws 
	 */
	public static int dip2px(Context context, float dipValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (dipValue * scale + 0.5f);
	}

	/**
	 * 
	 * 描述：px转换为dip
	 * @param context
	 * @param pxValue
	 * @return
	 * @throws 
	 */
	public static int px2dip(Context context, float pxValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (pxValue / scale + 0.5f);
	}
	
	 
    /**
     * 描述：获取文字的像素宽.
     *
     * @param str the str
     * @param paint the paint
     * @return the string width
     */
    public static float getStringWidth(String str,TextPaint paint) {
   	 float strWidth  = paint.measureText(str);
        return strWidth;
    }
    
    public static void setListViewHeightBasedOnChildren(ListView listView) {
      ListAdapter listAdapter = listView.getAdapter();
      if (listAdapter == null) {
      // pre-condition
            return;
      }

      int totalHeight = listView.getPaddingTop() + listView.getPaddingBottom();
      for (int i = 0; i < listAdapter.getCount(); i++) {
           View listItem = listAdapter.getView(i, null, listView);
           if (listItem instanceof ViewGroup) {
              listItem.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
           }
           listItem.measure(0, 0);
           totalHeight += listItem.getMeasuredHeight();
      }

      ViewGroup.LayoutParams params = listView.getLayoutParams();
      params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
                listView.setLayoutParams(params);
  }
	
}
