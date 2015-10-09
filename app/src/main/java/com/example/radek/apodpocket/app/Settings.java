package com.example.radek.apodpocket.app;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.radek.apodpocket.Constants;
import com.example.radek.apodpocket.network.VolleyApplication;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Settings {
    private static final String TAG = "Settings";


    private static Settings sInstance;

    private SharedPreferences mPreferences;

    public Settings() {
        mPreferences = VolleyApplication.getInstance().getApplicationContext().getSharedPreferences(Constants.PREF_NAME, Context.MODE_PRIVATE);
    }

    public static synchronized Settings get() {
        if (sInstance == null) sInstance = new Settings();
        return sInstance;
    }

    public void setLastViewedPagePosition(String lastPage) {

        mPreferences.edit().putString("lastViewedPage", lastPage).commit();

    }

    public String getLastViewedPagePosition() {

        return mPreferences.getString("latestDate", "");

    }

    public void setLatestDate(String latestDate) {

        mPreferences.edit().putString("latestDate", latestDate).commit();

    }

    public DateTime getLastUpdateDate() {
        DateTime date = new DateTime();
        String param = mPreferences.getString("latestDate", "");
        if(!param.isEmpty()){
            date = new DateTime(mPreferences.getString("latestDate", ""));
        }

        return date;
    }


}
