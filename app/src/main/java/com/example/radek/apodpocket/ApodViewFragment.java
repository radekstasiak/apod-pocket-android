package com.example.radek.apodpocket;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.radek.apodpocket.images.ImageUtils;
import com.example.radek.apodpocket.interfaces.DataInterface;
import com.example.radek.apodpocket.model.APOD;
import com.example.radek.apodpocket.utils.TopCenterImageView;
import com.squareup.picasso.Picasso;

import java.io.IOException;


public class ApodViewFragment extends Fragment implements DataInterface {
    private static final int TOP_HEIGHT = 700;
    private TopCenterImageView mApodImageView;
    private TopCenterImageView blurredImageView;
    private TextView mTextView;
    private TextView mTitleView;
    private APOD mApodElement;
    private ListView mList;
    private float alpha;

    private static final String KEY_CONTENT = "ApodViewFragment:Content";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
        try {
            initUI(rootView);
        } catch (IOException e) {
            e.printStackTrace();
        }

        setData();

        return rootView;
    }

    private void initUI(ViewGroup rootView) throws IOException {
        blurredImageView = (TopCenterImageView) rootView.findViewById(R.id.blurred_image);
        mApodImageView = (TopCenterImageView) rootView.findViewById(R.id.apod_view_apod_iv);
        mList = (ListView) rootView.findViewById(R.id.list);




    }



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


        //mTextView.setText(mApodElement.getExplanation());
        //mTitleView.setText(mApodElement.getTitle());

    }

}
