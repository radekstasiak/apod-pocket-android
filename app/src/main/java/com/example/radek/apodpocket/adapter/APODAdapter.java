package com.example.radek.apodpocket.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.radek.apodpocket.R;
import com.example.radek.apodpocket.model.APOD;

import java.util.ArrayList;

/**
 * Created by Radek on 27/09/15.
 */
public class APODAdapter extends RecyclerView.Adapter<APODAdapter.ViewHolder> {

    private ArrayList<APOD> mDataset;
    private Context mContext;

    //private OnItemClickListener mItemClickListener;
    public APODAdapter(Context context) {
        this.mContext = context;

    }

    public void setData(APOD apodElement) {
        if (this.mDataset == null) {
            this.mDataset = new ArrayList<APOD>();
        }
        this.mDataset.add(apodElement);
        notifyDataSetChanged();
//        if (this.mDataset != null) {
//            this.mDataset.clear();
//        }
//        ArrayList<APOD> apodList=new ArrayList<APOD>();
//        apodList.add(apodElement);
//        this.mDataset=apodList;
//        notifyDataSetChanged();
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
        public ViewHolder(View v, Context context) {
            super(v);
            mTitle = (TextView) v.findViewById(R.id.apod_element_title_tv);

        }
    }
}
