package com.example.radek.apodpocket.network;

import android.app.Application;


import android.graphics.Bitmap;
import android.support.v4.util.LruCache;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;
import com.example.radek.apodpocket.app.Settings;
import com.example.radek.apodpocket.images.DiskLruImageCache;

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
        mImageLoader = new ImageLoader(this.mRequestQueue, new ImageLoader.ImageCache() {
            //private final LruCache<String, Bitmap> mCache = new LruCache<String, Bitmap>(10);
            private final LruCache<String, Bitmap> mCache = new LruCache<String, Bitmap>(10);
            public void putBitmap(String url, Bitmap bitmap) {
                mCache.put(url, bitmap);
            }
            public Bitmap getBitmap(String url) {
                return mCache.get(url);
            }
        });

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

