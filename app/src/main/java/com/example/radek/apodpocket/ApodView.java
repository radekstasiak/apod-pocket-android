package com.example.radek.apodpocket;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.example.radek.apodpocket.interfaces.SaveDataInterface;
import com.example.radek.apodpocket.model.APOD;
import com.example.radek.apodpocket.network.APIUtils;
import com.example.radek.apodpocket.network.VolleyApplication;

import java.awt.font.TextAttribute;


public class ApodView extends Activity implements SaveDataInterface {
    private NetworkImageView mNetworkImageView;
    private TextView mTextView;
    private APOD apodElement;
    private ImageLoader mImageLoader;
    private RelativeLayout mRelativeLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apod_view);
        mImageLoader = VolleyApplication.getInstance().getImageLoader();
        Intent intent = getIntent();
        String apodDate = intent.getExtras().getString("APOD_DATE");
        initUI();
        APIUtils apiUtils = new APIUtils(this);
        apiUtils.getSingleAPOD(apodDate);
    }

    private void initUI() {

        mNetworkImageView = (NetworkImageView) findViewById(R.id.apod_view_apod_iv);
        mTextView = (TextView) findViewById(R.id.apod_view_text_tv);
        mRelativeLayout = (RelativeLayout) findViewById(R.id.apod_view_rl);
        mTextView.setMovementMethod(new ScrollingMovementMethod());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.menu_apod_view, menu);
        return true;
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
        this.apodElement = apodElement;
    setData();
    }

    private void setData() {

        mNetworkImageView.setImageUrl(apodElement.getUrl(), mImageLoader);
        mTextView.setText(apodElement.getExplanation());
    }


    public void pressCloseButton(final View view)
    {
            super.onBackPressed();

    }
    public void showExplanation(final View view)
    {
        if (mRelativeLayout.getVisibility()==View.GONE){
            mRelativeLayout.setVisibility(View.VISIBLE);
            mNetworkImageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        } else{
            mRelativeLayout.setVisibility(View.GONE);
            mNetworkImageView.setScaleType(ImageView.ScaleType.CENTER);
        }

    }
}
