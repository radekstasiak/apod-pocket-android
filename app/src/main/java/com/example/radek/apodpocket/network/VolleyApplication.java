package com.example.radek.apodpocket.network;

import android.app.Application;


import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.util.LruCache;
import android.util.DisplayMetrics;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;
import com.example.radek.apodpocket.Constants;
import com.example.radek.apodpocket.app.Settings;
import com.example.radek.apodpocket.images.DiskLruImageCache;
import com.example.radek.apodpocket.images.LruBitmapCache;

import java.io.File;

/**
 * Created by Radek on 16/09/15.
 */
public class VolleyApplication extends Application {

    private static VolleyApplication sInstance;
    private RequestQueue mRequestQueue;
    private ImageLoader mImageLoader;

    @Override
    public void onCreate() {
        super.onCreate();
        sInstance=this;
        mRequestQueue  = Volley.newRequestQueue(this.getApplicationContext());
        mImageLoader = new ImageLoader(mRequestQueue, new LruBitmapCache(
                LruBitmapCache.getCacheSize(this.getApplicationContext())));
//        File dir = getApplicationContext().getFilesDir();
//        File file = new File(dir, Constants.STORAGE_FILENAME);
//        file.delete();

    }



    public synchronized static VolleyApplication getInstance() {

        if(sInstance == null){
            sInstance = new VolleyApplication();
        }
        return sInstance;
    }


    public static Settings getSettings() {
        return Settings.get();
    }

    public RequestQueue getRequestQueue() {
        return mRequestQueue;
    }
    public ImageLoader getImageLoader(){
        return this.mImageLoader;
    }

}

