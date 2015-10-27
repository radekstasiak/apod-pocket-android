package com.example.radek.apodpocket;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.radek.apodpocket.adapter.ApodViewAdapter;
import com.example.radek.apodpocket.interfaces.DataInterface;
import com.example.radek.apodpocket.model.APOD;
import com.example.radek.apodpocket.network.APIUtils;
import com.example.radek.apodpocket.utils.StorageMenagerHelper;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Radek on 05/10/15.
 */
public class ApodViewActivity extends AppCompatActivity implements DataInterface {


    private ViewPager mPager;
    private ApodViewAdapter mPagerAdapter;
    private int currentApodId;
    private ArrayList<APOD> mApodsList;
    private APIUtils apiUtils;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apod_view);
        apiUtils = new APIUtils(this);
        try {
            currentApodId = getIntent().getIntExtra("APOD_DATE", 0);
            readData();
        } catch (IOException e) {
            e.printStackTrace();
        }
        initUI();


    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void initUI() {

        mPager = (ViewPager) findViewById(R.id.apod_view_pager);
        setPager();

    }

    private void setPager() {

        mPagerAdapter = new ApodViewAdapter(getSupportFragmentManager(), mApodsList);
        mPager.setAdapter(mPagerAdapter);
        mPager.setCurrentItem(currentApodId);

        mPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {


            }

            @Override
            public void onPageSelected(int position) {

                if (position == mApodsList.size() - 3) {
                    apiUtils.getAPODS();
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    public void saveData(APOD apodElement) {

    }

    @Override
    public void onBackPressed(){

        Intent intent = new Intent();
        intent.putExtra("last_viewed_page",mPager.getCurrentItem());
        setResult(RESULT_OK, intent);
        super.onBackPressed();
    }


    @Override
    public void readData() throws IOException {


        mApodsList = StorageMenagerHelper.readFromInternalStorage(this);
        if(mPagerAdapter !=null) {
            mPagerAdapter.setData(mApodsList);
        }


    }
}
