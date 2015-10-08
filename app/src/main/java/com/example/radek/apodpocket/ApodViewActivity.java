package com.example.radek.apodpocket;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;

import com.example.radek.apodpocket.adapter.ApodViewAdapter;
import com.example.radek.apodpocket.interfaces.DataInterface;
import com.example.radek.apodpocket.model.APOD;
import com.example.radek.apodpocket.utils.StorageMenagerHelper;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Radek on 05/10/15.
 */
public class ApodViewActivity extends FragmentActivity implements DataInterface {


    private ViewPager mPager;
    private PagerAdapter mPagerAdapter;
    private int currentApodId;
    private ArrayList<APOD> mApodsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apod_view);

        try {
            readData();
        } catch (IOException e) {
            e.printStackTrace();
        }
        initUI();


    }

    private void initUI() {

        mPager = (ViewPager) findViewById(R.id.apod_view_pager);
        setPager();

    }

    private void setPager() {

        mPagerAdapter = new ApodViewAdapter(getSupportFragmentManager(), mApodsList);
        mPager.setAdapter(mPagerAdapter);
        mPager.setCurrentItem(currentApodId);
    }

    @Override
    public void saveData(APOD apodElement) {

    }

    @Override
    public void readData() throws IOException {

        currentApodId = getIntent().getIntExtra("APOD_DATE", 0);
        mApodsList = StorageMenagerHelper.readFromInternalStorage(this);


    }
}
