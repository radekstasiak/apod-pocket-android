package com.example.radek.apodpocket;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.ArrayAdapter;
import android.widget.AbsListView.LayoutParams;

import com.example.radek.apodpocket.adapter.BlurListAdapter;
import com.example.radek.apodpocket.images.Blur;
import com.example.radek.apodpocket.interfaces.DataInterface;
import com.example.radek.apodpocket.model.APOD;
import com.example.radek.apodpocket.utils.ImageHelper;
import com.example.radek.apodpocket.utils.ScrollableImageView;

import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.IOException;


public class ApodViewFragment extends Fragment implements DataInterface {
    private static final int TOP_HEIGHT = 700;
    public static final int BACKGROUND_SHIFT = 300;

    private String blurredImagePath;
    private ImageView mApodImageView;
    private ImageView mBlurredImage;
    private ScrollableImageView mBlurredImageHeader;
    private View headerView;
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

        initUI(rootView);


        setData();

        return rootView;
    }

    private void initUI(ViewGroup rootView)  {

        blurredImagePath="/"+mApodElement.getDate()+"_blurred.png";
        mBlurredImage = (ImageView) rootView.findViewById(R.id.blurred_image);
        mApodImageView = (ImageView) rootView.findViewById(R.id.apod_view_apod_iv);
        mBlurredImageHeader = (ScrollableImageView) rootView.findViewById(R.id.blurred_image_header);
        mList = (ListView) rootView.findViewById(R.id.list);



        initImages();
        headerView = new View(getActivity());
        headerView.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, TOP_HEIGHT));
        initList();

    }

    private void initList() {
        //String[] strings = getResources().getStringArray(R.array.list_content);
        //String[] strings = mApodElement.getExplanation().split(" ");
        String[] strings = new String[] {mApodElement.getExplanation()};
        mList.addHeaderView(headerView);
        //mList.setAdapter(new ArrayAdapter<String>(getActivity(), R.layout.list_item, strings));
        mList.setAdapter(new BlurListAdapter(getActivity(),mApodElement));
        mList.setOnScrollListener(new AbsListView.OnScrollListener() {

            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            /**
             * Listen to the list scroll. This is where magic happens ;)
             */
            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

                // Calculate the ratio between the scroll amount and the list
                // header weight to determinate the top picture alpha
                alpha = (float) -headerView.getTop() / (float) TOP_HEIGHT;
                // Apply a ceil
                if (alpha > 1) {
                    alpha = 1;
                }

                // Apply on the ImageView if needed
                //if (mSwitch.isChecked()) {
                mBlurredImage.setAlpha(alpha);
                //}

                // Parallax effect : we apply half the scroll amount to our
                // three views
                mBlurredImage.setTop(headerView.getTop() / 2);
                mApodImageView.setTop(headerView.getTop() / 2);
                mBlurredImageHeader.handleScroll(headerView.getTop() / 2);

            }
        });
    }


    private void initImages() {
        final int screenWidth = ImageHelper.getScreenWidth(getActivity());
        int screenHeight = ImageHelper.getScreenHeight(getActivity())
                + BACKGROUND_SHIFT;

        mBlurredImageHeader.setScreenWidth(screenWidth);
        mBlurredImage.setAlpha(alpha);
        setViewHeight(mApodImageView, screenHeight);
        setViewHeight(mBlurredImage, screenHeight);

        final File blurredImage = new File(getActivity().getFilesDir() + blurredImagePath);
        if (!blurredImage.exists()) {

            // launch the progressbar in ActionBar
            //setProgressBarIndeterminateVisibility(true);

            new Thread(new Runnable() {

                @Override
                public void run() {

                    // No image found => let's generate it!
                    BitmapFactory.Options options = new BitmapFactory.Options();
                    options.inSampleSize = 2;
                    Bitmap image = ImageHelper.getBitmapFromURL(mApodElement.getUrl());
                    Bitmap newImg = Blur.fastblur(getActivity(), image, 12);
                    ImageHelper.storeImage(newImg, blurredImage);
                    getActivity().runOnUiThread(new Runnable() {

                        @Override
                        public void run() {
                            updateView(screenWidth);

                            // And finally stop the progressbar
                            // setProgressBarIndeterminateVisibility(false);
                        }
                    });

                }

            }).start();

        } else {

            // The image has been found. Let's update the view
            updateView(screenWidth);

        }

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


    }
    private void updateView(final int screenWidth) {
        Bitmap bmpBlurred = BitmapFactory.decodeFile(getActivity().getFilesDir() + blurredImagePath);
        bmpBlurred = Bitmap.createScaledBitmap(bmpBlurred, screenWidth, (int) (bmpBlurred.getHeight()
                * ((float) screenWidth) / (float) bmpBlurred.getWidth()), false);

        mBlurredImage.setImageBitmap(bmpBlurred);
    }
    public void setViewHeight(View v, int height) {
        FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) v.getLayoutParams();
        params.height = height;
        v.setLayoutParams(params);
    }
}
