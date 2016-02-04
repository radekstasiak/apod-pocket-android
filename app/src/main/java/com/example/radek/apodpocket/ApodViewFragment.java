package com.example.radek.apodpocket;

import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.radek.apodpocket.interfaces.DataInterface;
import com.example.radek.apodpocket.model.APOD;
import com.example.radek.apodpocket.utils.FontHelper;
import com.example.radek.apodpocket.utils.ImageHelper;
import com.squareup.picasso.Picasso;

import java.io.IOException;


public class ApodViewFragment extends Fragment implements DataInterface, AppBarLayout.OnOffsetChangedListener {
    private ImageView mApodImageView;
    private TextView mTextView;
    private TextView mTitle;
    private LinearLayout mContentFl;
    private APOD mApodElement;
    private RelativeLayout mRelativeLayout;

    private CollapsingToolbarLayout mCollapsingToolbarLayout;


    private static final String KEY_CONTENT = "ApodViewFragment:Content";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivity().getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);

        if ((savedInstanceState != null) && savedInstanceState.containsKey(KEY_CONTENT)) {
            mApodElement = (APOD) savedInstanceState.getSerializable(KEY_CONTENT);
        }

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable(KEY_CONTENT, mApodElement);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(
                R.layout.fragment_apod_material, container, false);
        initUI(rootView);
        //attachGlobalListener();
        setData();

        return rootView;
    }

    private void initUI(ViewGroup rootView) {
        mApodImageView = (ImageView) rootView.findViewById(R.id.apod_view_apod_iv);
        mTextView = (TextView) rootView.findViewById(R.id.apod_view_text_tv);
        mTitle = (TextView) rootView.findViewById(R.id.apod_fragment_title_tv);
        mRelativeLayout = (RelativeLayout) rootView.findViewById(R.id.apod_fragment_text_rl);
        mContentFl = (LinearLayout) rootView.findViewById(R.id.fragment_apod_fl);
        //mToolbar = (Toolbar) rootView.findViewById(R.id.toolbar);
        //mAppBarLayout = (AppBarLayout) rootView.findViewById(R.id.app_bar_layout);
        //mAppBarLayout.addOnOffsetChangedListener(this);
        //mCollapsingToolbarLayout = (CollapsingToolbarLayout) rootView.findViewById(R.id.apod_fragment_ctl);
//        ActionBar actionBar = ((ApodViewActivity) getActivity()).getSupportActionBar();

//        ActionBar actionBar = getSupportActionBar();
//        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                getActivity().onBackPressed();
//            }
//        });



    }

//    private void attachGlobalListener() {
//        ViewTreeObserver vto = mContentFl.getViewTreeObserver();
//        vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
//
//            @Override
//            public void onGlobalLayout() {
//                setHeroImageMaxHeight();
//                ViewTreeObserver obs = mContentFl.getViewTreeObserver();
//                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
//                    obs.removeOnGlobalLayoutListener(this);
//                } else {
//                    obs.removeGlobalOnLayoutListener(this);
//                }
//            }
//
//        });
//    }

    public static ApodViewFragment newInstance(APOD apodElement) {
        ApodViewFragment fragment = new ApodViewFragment();
        fragment.mApodElement = apodElement;
        return fragment;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void saveData(APOD apodElement) {
    }

    @Override
    public void readData() throws IOException {
    }

    private void setData() {
        if(mApodElement.getType().equals("video")){
            Picasso.with(getActivity()).load(R.drawable.videoplaceholder).into(mApodImageView);
        }else{
            Picasso.with(getActivity()).load(mApodElement.getUrl()).into(mApodImageView);
        }


        //new HeroImageSizeAsyncTask().execute();
        mTextView.setText(mApodElement.getExplanation());
        mTitle.setText(mApodElement.getTitle());

        //((ApodViewActivity) getActivity()).setSupportActionBar(mToolbar);
        //((ApodViewActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //mToolbar.requestLayout();
    }

    private void setHeroImageMaxHeight(){

        //int screenHeight = ImageHelper.getDisplayHeight(getActivity());
       // mToolbar.getLayoutParams().height = screenHeight - mContentFl.getHeight();
        //mToolbar.requestLayout();

    }

    private void setTitleTextPosition(Integer height){

       // mRelativeLayout.getLayoutParams().height =  height;
    }

    @Override
    public void onOffsetChanged(AppBarLayout appBarLayout, int offset) {
        float minimalTextSize = getActivity().getResources().getDimensionPixelSize(R.dimen.apod_view_title_scrolling_text_size);
        float maximumTextSize = getActivity().getResources().getDimensionPixelSize(R.dimen.apod_view_title_default_text_size);
        float absolut_offset = Math.abs(offset);
        float text_size_difference = maximumTextSize - minimalTextSize;
        float scale = (absolut_offset) / (appBarLayout.getHeight() - absolut_offset);
        if (offset < 0) {
            float result = maximumTextSize - (scale * text_size_difference);
            mTitle.setTextSize(FontHelper.pixelsToSp(getActivity(), result));
        } else {
            mTitle.setTextSize(FontHelper.pixelsToSp(getActivity(), maximumTextSize));


        }
    }
//
//    private class HeroImageSizeAsyncTask extends AsyncTask<Integer, Integer, Integer>{
//
//        @Override
//        protected Integer doInBackground(Integer... params) {
//
//            //return ImageHelper.getImageHeight(getActivity(),mApodElement.getUrl());
//        }
//
//        protected void onPostExecute(Integer result) {
//
//           // setTitleTextPosition(result);
//
//        }
//        }
//
//
}
