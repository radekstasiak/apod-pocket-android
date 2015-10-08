package com.example.radek.apodpocket;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.example.radek.apodpocket.interfaces.DataInterface;
import com.example.radek.apodpocket.model.APOD;
import com.example.radek.apodpocket.network.VolleyApplication;
import com.squareup.picasso.Picasso;

import java.io.IOException;


public class ApodViewFragment extends Fragment implements DataInterface {
    private NetworkImageView mNetworkImageView;
    private ImageView mApodImageView;
    private TextView mTextView;
    private APOD mApodElement;
    private int mApodId;
    private ImageLoader mImageLoader;
    private RelativeLayout mRelativeLayout;
    private ImageView mCloseButton;

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
                R.layout.fragment_apod_view, container, false);

        //mNetworkImageView = (NetworkImageView) rootView.findViewById(R.id.apod_view_apod_iv);
        mApodImageView = (ImageView) rootView.findViewById(R.id.apod_view_apod_iv);
        mTextView = (TextView) rootView.findViewById(R.id.apod_view_text_tv);
        mRelativeLayout = (RelativeLayout) rootView.findViewById(R.id.apod_view_rl);
        mTextView.setMovementMethod(new ScrollingMovementMethod());
        mCloseButton = (ImageView) rootView.findViewById(R.id.apod_view_close_iv);
        setData();
        return rootView;
    }
    public static ApodViewFragment newInstance(APOD apodElement, int position) {
        ApodViewFragment fragment = new ApodViewFragment();

        fragment.mApodId = position;
        fragment.mApodElement = apodElement;
        fragment.mImageLoader = VolleyApplication.getInstance().getImageLoader();

        return fragment;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    public void saveData(APOD apodElement) {
       // this.mApodElement = apodElement;
    //setData();
    }

    @Override
    public void readData() throws IOException {

    }

    private void setData() {

        //mNetworkImageView.setImageUrl(mApodElement.getUrl(), mImageLoader);
        Picasso.with(getActivity()).load(mApodElement.getUrl()).into(mApodImageView);
        mTextView.setText(mApodElement.getExplanation());
        mCloseButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();

            }
        });
        //to change with mNetworkImageView
        mApodImageView.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                showExplanation();

            }
        });
    }


    public void pressCloseButton(final View view)
    {
            //super.onBackPressed();

    }
    public void showExplanation()
    {
        if (mRelativeLayout.getVisibility()==View.GONE){
            mRelativeLayout.setVisibility(View.VISIBLE);
            mApodImageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        } else{
            mRelativeLayout.setVisibility(View.GONE);
            mApodImageView.setScaleType(ImageView.ScaleType.CENTER);
        }

    }
}
