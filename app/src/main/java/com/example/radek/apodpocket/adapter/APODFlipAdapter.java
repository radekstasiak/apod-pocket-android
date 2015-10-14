package com.example.radek.apodpocket.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.radek.apodpocket.R;
import com.example.radek.apodpocket.model.APOD;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by Radek on 14/10/15.
 */
public class APODFlipAdapter extends BaseAdapter {

    private ArrayList<APOD> mDataset;
    private LayoutInflater inflater;
    private Context mContext;


    public APODFlipAdapter(Context context){

        this.inflater = LayoutInflater.from(context);
        this.mContext = context;
    }
    public void setData(ArrayList<APOD> apodsList) {
        if (this.mDataset == null) {
            this.mDataset = new ArrayList<APOD>();
        }
        this.mDataset=apodsList;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        if (mDataset != null) {
            return mDataset.size();
        } else {
            return 0;
        }
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        return null;
    }


}
