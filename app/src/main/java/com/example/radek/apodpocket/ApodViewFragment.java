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
import android.widget.TextView;

import com.example.radek.apodpocket.images.ImageUtils;
import com.example.radek.apodpocket.interfaces.DataInterface;
import com.example.radek.apodpocket.model.APOD;
import com.squareup.picasso.Picasso;

import java.io.IOException;


public class ApodViewFragment extends Fragment implements DataInterface {
    public static final int BACKGROUND_SHIFT = 200;
    private ImageView mApodImageView;
    private ImageView blurredImageView;
    private TextView mTextView;
    private TextView mTitleView;
    private APOD mApodElement;

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
        int screenHeight = ImageUtils.getScreenHeight(getActivity())
                + BACKGROUND_SHIFT;
        blurredImageView = (ImageView) rootView.findViewById(R.id.blured_image);
        mApodImageView = (ImageView) rootView.findViewById(R.id.apod_view_apod_iv);

        setViewHeight(mApodImageView, screenHeight);
        setViewHeight(blurredImageView, screenHeight);

        loadBlurredImage();

    }


    public void setViewHeight(View v, int height) {
        ViewGroup.LayoutParams params = v.getLayoutParams();
        params.height = height;
        v.setLayoutParams(params);
    }

    private void loadBlurredImage() throws IOException {
        Drawable backgroundImage = ImageUtils.drawableFromUrl(mApodElement.getUrl());
            ImageUtils.getBlurredImage(getActivity(), backgroundImage,
                    ImageUtils.getImageName(mApodElement.getDate()), 20,
                    new ImageUtils.BlurEffectListener() {

                        @Override
                        public void onDone(Bitmap bitmap) {
                            blurredImageView.setImageBitmap(bitmap);
                        }
                    });
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
