package com.example.radek.apodpocket.adapter;


import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;

import com.example.radek.apodpocket.ApodViewFragment;
import com.example.radek.apodpocket.model.APOD;
import com.example.radek.apodpocket.network.APIUtils;

import java.util.ArrayList;

/**
 * Created by Radek on 05/10/15.
 */

public class ApodViewAdapter  extends FragmentStatePagerAdapter {

   /// private ArrayList<View> views = new ArrayList<View>();

    private ArrayList<APOD> mDataset;

    private Context mContext;
    private APIUtils apiUtils;
    private String mDate;

    public ApodViewAdapter(FragmentManager fm, Context context, String date) {
        super(fm);
        mContext = context;
        mDate = date;

    }

//    @Override
//    public int getItemPosition (Object object)
//    {
//        int index = views.indexOf (object);
//        if (index == -1)
//            return POSITION_NONE;
//        else
//            return index;
//    }
//
//    @Override
//    public Object instantiateItem (ViewGroup container, int position)
//    {
//        Object v = views.get(position);
//        container.addView((View) v);
//        return v;
//    }
//
//    @Override
//    public void destroyItem (ViewGroup container, int position, Object object)
//    {
//        container.removeView(views.get(position));
//    }
//
//    @Override
//    public int getCount ()
//    {
//        return views.size();
//    }
//
//    @Override
//    public boolean isViewFromObject (View view, Object object)
//    {
//        return view == object;
//    }
//
//    public int addView (View v)
//    {
//
//        return addView(v, views.size());
//    }
//
//    public int addView (View v, int position)
//    {
//        views.add(position, v);
//        return position;
//    }
//
//    public int removeView (ViewPager pager, View v)
//    {
//        return removeView (pager, views.indexOf (v));
//    }
//
//    public int removeView (ViewPager pager, int position)
//    {
//
//        pager.setAdapter (null);
//        views.remove (position);
//        pager.setAdapter (this);
//
//        return position;
//    }
//    public View getView (int position)
//    {
//        return views.get (position);
//    }
//
//
    @Override
    public Fragment getItem(int position) {
        ApodViewFragment apodFg = new ApodViewFragment().newInstance(position);
        //views.add(apodFg);
        return apodFg;
    }

    @Override
    public int getCount() {
        return mDataset.size();
    }
}
