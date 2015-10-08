package com.example.radek.apodpocket.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.andexert.library.RippleView;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.Volley;
import com.example.radek.apodpocket.R;

import com.example.radek.apodpocket.images.LruBitmapCache;
import com.example.radek.apodpocket.model.APOD;
import com.example.radek.apodpocket.network.VolleyApplication;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * Created by Radek on 27/09/15.
 */
public class APODAdapter extends RecyclerView.Adapter<APODAdapter.ViewHolder> {

    private ArrayList<APOD> mDataset;
    private Context mContext;
    private ImageLoader mImageLoader;
    private RequestQueue mRequestQueue;
    public APODAdapter(Context context) {
        this.mContext = context;
        DisplayMetrics displayMetrics = mContext.getResources().getDisplayMetrics();
         mRequestQueue= Volley.newRequestQueue(mContext);
       // mImageLoader = new ImageLoader(mRequestQueue, new LruBitmapCache(
         //           LruBitmapCache.getCacheSize(mContext)));
        mImageLoader = VolleyApplication.getInstance().getImageLoader();


    }

    public void setData(ArrayList<APOD> apodsList) {
        if (this.mDataset == null) {
            this.mDataset = new ArrayList<APOD>();
        }
        this.mDataset=apodsList;
        notifyDataSetChanged();
    }
    @Override
    public APODAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {

        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.apod_list_element, viewGroup, false);
        ViewHolder vh = new ViewHolder(v, mContext);
        return vh;
    }

    @Override
    public void onBindViewHolder(final APODAdapter.ViewHolder viewHolder, final int position) {
        if (mDataset != null) {
            viewHolder.mTitle.setText(mDataset.get(position).getTitle());
            viewHolder.mDate.setText(mDataset.get(position).getDate());
            //viewHolder.mElementImage.setImageUrl(mDataset.get(position).getUrl(), mImageLoader);

            Picasso.with(mContext).load(mDataset.get(position).getUrl()).into(viewHolder.mElementImage);
            Animation mFadeAnimation  = AnimationUtils.loadAnimation(mContext,R.anim.blink);
            int intMin = (int) (long) mFadeAnimation.getDuration() - 200;
            int intMax = (int) (long) mFadeAnimation.getDuration() + 200;

            int randNum = intMin + (int)(Math.random() * ((intMax - intMin) + 1));
            mFadeAnimation.setDuration(randNum);
            if(viewHolder !=null){

                viewHolder.mListLayout.startAnimation(mFadeAnimation);

            }

            //viewHolder.mElementImage.setImageUrl(mDataset.get(position).getUrl(), mImageLoader);

            if (viewHolder == null){

                mFadeAnimation.cancel();
                mFadeAnimation.reset();
            }

        }
    }

    @Override
    public int getItemCount() {
        if (mDataset != null) {
            return mDataset.size();
        } else {
            return 0;
        }


    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView mTitle;
       public TextView mDate;
        RelativeLayout mListLayout;
        //NetworkImageView mElementImage;
        ImageView mElementImage;
        RippleView mRippleView;
         ;
        public ViewHolder(View v, Context context) {
            super(v);
            mTitle = (TextView) v.findViewById(R.id.apod_element_title_tv);
            mDate = (TextView) v.findViewById(R.id.apod_element_date_tv);
            //mElementImage = (NetworkImageView) v.findViewById(R.id.apod_element_iv);
            mElementImage = (ImageView) v.findViewById(R.id.apod_element_iv);
            mListLayout = (RelativeLayout) v.findViewById(R.id.apods_list_rl);
            mRippleView = (RippleView) v.findViewById(R.id.ripple_view);


        }


    }
}