package com.example.radek.apodpocket.adapter;


import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;

import com.example.radek.apodpocket.APODList;
import com.example.radek.apodpocket.ApodViewFragment;
import com.example.radek.apodpocket.model.APOD;
import com.example.radek.apodpocket.network.APIUtils;

import java.util.ArrayList;

/**
 * Created by Radek on 05/10/15.
 */

public class ApodViewAdapter extends FragmentStatePagerAdapter {

    private ArrayList<APOD> mDataset;

    public ApodViewAdapter(FragmentManager fm, ArrayList<APOD> apodsList) {
        super(fm);
        setData(apodsList);

    }

    public void setData(ArrayList<APOD> apodsList){

        if(apodsList!=null){
            this.mDataset= apodsList;
            notifyDataSetChanged();
        }
    }
    @Override
    public Fragment getItem(int position) {
        return new ApodViewFragment().newInstance(mDataset.get(position));


    }

    @Override
    public int getCount() {

        return mDataset.size();
    }
}
