package com.example.radek.apodpocket.adapter;


import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.radek.apodpocket.ApodViewFragment;
import com.example.radek.apodpocket.network.APIUtils;

/**
 * Created by Radek on 05/10/15.
 */

public class ApodViewAdapter  extends FragmentStatePagerAdapter {
    private static int NUM_ITEMS = 3;
    private Context mContext;
    private APIUtils apiUtils;

    public ApodViewAdapter(FragmentManager fm, Context context) {
        super(fm);
        mContext = context;
        //apiUtils = new APIUtils(context);


    }

    @Override
    public Fragment getItem(int position) {
        return new ApodViewFragment().newInstance();
    }

    @Override
    public int getCount() {
        return NUM_ITEMS;
    }
}
