package com.lianjiao.core.app;

import android.app.Application;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.provider.ContactsContract.CommonDataKinds.Event;
import android.support.v4.content.LocalBroadcastManager;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.View;

import com.android.http.RequestManager;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.lianjiao.core.R;
import com.lianjiao.core.utils.LogUtils;
import com.lianjiao.core.utils.LruBitmapCache;
import com.lianjiao.core.utils.LsConstants;
import com.lianjiao.core.utils.PreferenceUtil;

/**
 * 描述:自定义 Application
 * <p/>
 * Created by GA on 15-8-4.
 *如果要使用SD卡缓存，使用APP下的MyVolley默认设置50MSD卡缓存容量
 * Volley默认使用的手机缓存，在/data/data/包名/Volley下默认5M
 * @author 韩创
 * @version 1.00
 */
public class CoreApplication extends Application {
    public static final String TAG = CoreApplication.class
            .getSimpleName();
    public static CoreApplication appContext = null;
    
    /**
     * 请求队列
     */
    private RequestQueue mRequestQueue;
    /**
     * 图片加载
     */
    private ImageLoader mImageLoader;
    /**
     * 设备屏幕信息
     */
    public static DisplayMetrics dm;
    
    @Override
    public void onCreate() {
        super.onCreate();
        Thread.setDefaultUncaughtExceptionHandler(CoreAppException.getAppExctptionCaught(appContext));
        appContext = this;
        RequestManager.getInstance().init(appContext);
    }
    

    /**
     * 单例模式
     **/
    public static synchronized CoreApplication getInstance() {
        return appContext;
    }

    /**
     * 获取请求队列的单例模式
     *
     * @return
     */
    public RequestQueue getRequestQueue() {
    	mRequestQueue = RequestManager.getInstance().getRequestQueue();
        return mRequestQueue;
    }

    /**
     * 请求图片加载的单例模式
     *
     * @return
     */
    public ImageLoader getImageLoader() {
        getRequestQueue();
        if (mImageLoader == null) {
            mImageLoader = new ImageLoader(this.mRequestQueue,
                    new LruBitmapCache());
        }
        return this.mImageLoader;
    }


    /**
     * 添加到请求队列
     *
     * @param req
     * @param tag
     * @param <T>
     */
    public <T> void addToRequestQueue(Request<T> req, String tag) {
        // set the default tag if tag is empty
        req.setTag(TextUtils.isEmpty(tag) ? TAG : tag);
        getRequestQueue().add(req);
    }

    /**
     * 添加到请求队列
     *
     * @param req
     * @param <T>
     */
    public <T> void addToRequestQueue(Request<T> req) {
        req.setTag(TAG);
        getRequestQueue().add(req);
    }

    /**
     * 取消请求
     *
     * @param tag
     */
    public void cancelPendingRequests(Object tag) {
        if (mRequestQueue != null) {
            mRequestQueue.cancelAll(tag);
        }
    }

}
