package com.example.radek.apodpocket;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;

import com.example.radek.apodpocket.adapter.ApodViewAdapter;

/**
 * Created by Radek on 05/10/15.
 */
public class ApodViewActivity extends FragmentActivity {


    private ViewPager mPager;
    private PagerAdapter mPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apod_view);

        // Instantiate a ViewPager and a PagerAdapter.

        mPager = (ViewPager) findViewById(R.id.apod_view_pager);
        Bundle extras = getIntent().getExtras();
        String date = extras.getString("APOD_DATE");
        //String date = getIntent().getIntExtra("APOD_DATE", 0);
        mPagerAdapter = new ApodViewAdapter(getSupportFragmentManager(),this, date);
        mPager.setAdapter(mPagerAdapter);
        mPager.getCurrentItem();
    }
}
