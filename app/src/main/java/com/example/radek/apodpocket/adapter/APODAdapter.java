package com.example.radek.apodpocket.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import com.android.volley.toolbox.NetworkImageView;
import com.example.radek.apodpocket.R;
import com.example.radek.apodpocket.images.ImageCacheManager;
import com.example.radek.apodpocket.model.APOD;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Random;

/**
 * Created by Radek on 27/09/15.
 */
public class APODAdapter extends RecyclerView.Adapter<APODAdapter.ViewHolder> {

    private ArrayList<APOD> mDataset;
    private Context mContext;

    public APODAdapter(Context context) {
        this.mContext = context;

    }

    public void setData(APOD apodElement) {
        if (this.mDataset == null) {
            this.mDataset = new ArrayList<APOD>();
        }
        this.mDataset.add(apodElement);
        Collections.sort(mDataset, new Comparator<APOD>() {

            public int compare(APOD apod1, APOD apod2) {
                String obj1 = apod1.getDate();
                String obj2 = apod2.getDate();
                if (obj1 == obj2) {
                    return 0;
                }
                if (obj1 == null) {
                    return -1;
                }
                if (obj2 == null) {
                    return 1;
                }
                return obj2.compareTo(obj1);
            }
        });
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
    public void onBindViewHolder(APODAdapter.ViewHolder viewHolder, int position) {
        if (mDataset != null) {
            viewHolder.mTitle.setText(mDataset.get(position).getTitle());
            viewHolder.mDate.setText(mDataset.get(position).getDate());
            viewHolder.mElementImage.setImageUrl(mDataset.get(position).getUrl(), ImageCacheManager.getInstance().getImageLoader());

            Animation mFadeAnimation  = AnimationUtils.loadAnimation(mContext,R.anim.blink);

            int intMin = (int) (long) mFadeAnimation.getDuration() - 200;
            int intMax = (int) (long) mFadeAnimation.getDuration() + 200;

            int randNum = intMin + (int)(Math.random() * ((intMax - intMin) + 1));
            mFadeAnimation.setDuration(randNum);

            viewHolder.mElementImage.startAnimation(mFadeAnimation);
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
        TextView mDate
                ;
        NetworkImageView mElementImage;
        public ViewHolder(View v, Context context) {
            super(v);
            mTitle = (TextView) v.findViewById(R.id.apod_element_title_tv);
            mDate = (TextView) v.findViewById(R.id.apod_element_date_tv);
            mElementImage = (NetworkImageView) v.findViewById(R.id.apod_element_iv);


        }
    }
}