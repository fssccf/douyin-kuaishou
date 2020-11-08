package com.lianjiao.core.utils;

import android.widget.ImageView;

import com.android.volley.toolbox.ImageLoader;
import com.lianjiao.core.R;
import com.lianjiao.core.app.CoreApplication;

/**
 * @//图片加载工具 RCreated by 张东领
 */
public class ImageLoaderUtils {
    /**
     * 默认加载图片到ImageView中
     * @param url
     * @param view
     * @param defaultImageResId
     * @param errorImageResId
     */
    public static void loadImageView(String url, ImageView view, int defaultImageResId, int errorImageResId) {
        CoreApplication.getInstance().getImageLoader().get(url, ImageLoader.getImageListener(view, defaultImageResId, errorImageResId));
    }
    
    public static void loadImageView(String url, ImageView view){
    	CoreApplication.getInstance().getImageLoader().get(url, ImageLoader.getImageListener(view, R.drawable.ic_launcher, R.drawable.ic_launcher));
    }

    /**
     * 默认加载图片，根据不同需求自己改动图片
     * @param url
     * @param view
     * @param imageListener
     */

    public static void loadImageView(String url, ImageView view, ImageLoader.ImageListener imageListener) {
        CoreApplication.getInstance().getImageLoader().get(url, imageListener);
    }
}
