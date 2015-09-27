package com.example.radek.apodpocket.network;

import android.app.Application;


import android.app.Application;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * Created by Radek on 16/09/15.
 */
public class VolleyApplication extends Application {

    private static VolleyApplication sInstance;
    private static RequestQueue mRequestQueue;


    @Override
    public void onCreate() {
        super.onCreate();
        mRequestQueue = Volley.newRequestQueue(this);

        sInstance = this;
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

