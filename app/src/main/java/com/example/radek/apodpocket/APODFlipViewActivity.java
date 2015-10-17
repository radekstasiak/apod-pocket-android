package com.example.radek.apodpocket;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Toast;

import com.example.radek.apodpocket.adapter.APODFlipAdapter;
import com.example.radek.apodpocket.interfaces.DataInterface;
import com.example.radek.apodpocket.model.APOD;
import com.example.radek.apodpocket.network.APIUtils;
import com.example.radek.apodpocket.utils.StorageMenagerHelper;

import java.io.IOException;
import java.util.ArrayList;

import se.emilsjolander.flipview.FlipView;
import se.emilsjolander.flipview.OverFlipMode;


public class APODFlipViewActivity extends Activity implements DataInterface {
    private APODFlipAdapter apodAdapter;
    private FlipView flipView;
    private APIUtils apiUtils;
    private ArrayList<APOD> apods = new ArrayList<APOD>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_apodflip_view);
        apiUtils = new APIUtils(this);
        apiUtils.openAPODrequest();

        initUI();

    }

    private void initUI() {

        setFlipView();
    }

    private void setFlipView() {


        flipView = (FlipView) findViewById(R.id.flip_view);
        apodAdapter = new APODFlipAdapter(this, R.layout.activity_apodflip_element,this);
        flipView.setAdapter(apodAdapter);

        flipView.setOnOverFlipListener(new FlipView.OnOverFlipListener() {
            @Override
            public void onOverFlip(FlipView flipView, OverFlipMode overFlipMode, boolean b, float v, float v1) {
                if (flipView.getCurrentPage() != 0) {
                    Toast.makeText(flipView.getContext(), "Loading more", Toast.LENGTH_SHORT).show();
                    apiUtils.openAPODrequest();
                }


            }
        });




    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    public void saveData(APOD apodElement) {

    }

    @Override
    public void readData() throws IOException {

        apods = StorageMenagerHelper.readFromInternalStorage(this);
        apodAdapter.setData(apods);
    }


    public void seeDetails(int postion){

        Intent intent = new Intent(APODFlipViewActivity.this, ApodViewActivity.class);

        intent.putExtra("APOD_DATE", postion);

        startActivityForResult(intent, 1);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                int currentPosition = data.getIntExtra("last_viewed_page", 0);
                try {
                    readData();
                    flipView.flipTo(currentPosition);
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }
    }
}
