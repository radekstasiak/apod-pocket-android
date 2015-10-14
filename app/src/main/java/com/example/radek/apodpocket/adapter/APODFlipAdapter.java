package com.example.radek.apodpocket.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
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
public class APODFlipAdapter extends ArrayAdapter<APOD> {

    private ArrayList<APOD> mDataset;
    private final LayoutInflater mInflater;
    private final int mResourceId;
    private Context mContext;


    public APODFlipAdapter(Context context, int resource){
        super(context,resource);

        this.mInflater = LayoutInflater.from(context);
        this.mResourceId = resource;
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
    public View getView(int position, View convertView, ViewGroup viewGroup) {

        ViewHolder viewHolder = null;
        if (convertView == null) {

            viewHolder = new ViewHolder();
            convertView = mInflater.inflate(mResourceId, viewGroup, false);
            viewHolder.mImageView = (ImageView) convertView.findViewById(R.id.apod_element_iv);
            viewHolder.mTitle = (TextView) convertView.findViewById(R.id.apod_element_title_tv);
            viewHolder.mDate = (TextView) convertView.findViewById(R.id.apod_element_date_tv);
            convertView.setTag(viewHolder);
        } else {

            viewHolder = (ViewHolder) convertView.getTag();
        }
        if(viewHolder!=null) {
            Picasso.with(mContext).load(mDataset.get(position).getUrl()).into(viewHolder.mImageView);
            viewHolder.mTitle.setText(mDataset.get(position).getTitle());
            viewHolder.mDate.setText(mDataset.get(position).getDate());
        }
        return convertView;

    }


    private class ViewHolder {
        ImageView mImageView;
        TextView mTitle;
        TextView mDate;

    }

}
