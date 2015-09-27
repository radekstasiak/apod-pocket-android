package com.example.radek.apodpocket.network;

import android.app.Application;


import android.app.Application;
import android.graphics.Bitmap;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.example.radek.apodpocket.images.ImageCacheManager;
import com.example.radek.apodpocket.model.RequestManager;

/**
 * Created by Radek on 16/09/15.
 */
public class VolleyApplication extends Application {

    private static VolleyApplication sInstance;
    private static RequestQueue mRequestQueue;
    private static int DISK_IMAGECACHE_SIZE = 1024*1024*10;
    private static Bitmap.CompressFormat DISK_IMAGECACHE_COMPRESS_FORMAT = Bitmap.CompressFormat.PNG;
    private static int DISK_IMAGECACHE_QUALITY = 100;  //PNG is lossless so quality is ignored but must be provided

    @Override
    public void onCreate() {
        super.onCreate();
        //mRequestQueue = Volley.newRequestQueue(this);

        init();

    }

    private void init() {
        RequestManager.init(this);
        mRequestQueue=RequestManager.getRequestQueue();
        createImageCache();
    }
    private void createImageCache(){
        ImageCacheManager.getInstance().init(this,
                this.getPackageCodePath()
                , DISK_IMAGECACHE_SIZE
                , DISK_IMAGECACHE_COMPRESS_FORMAT
                , DISK_IMAGECACHE_QUALITY
                , ImageCacheManager.CacheType.MEMORY);
    }
//    public static Settings getSettings() {
//        return Settings.get();
//    }

    public synchronized static VolleyApplication getInstance() {
        return sInstance;
    }

    public RequestQueue getRequestQueue() {
        return mRequestQueue;
    }

}

