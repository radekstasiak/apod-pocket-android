package com.example.radek.apodpocket;

import android.app.Activity;
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
        apodAdapter = new APODFlipAdapter(this, R.layout.activity_apodflip_element);
        flipView.setAdapter(apodAdapter);

        flipView.setOnFlipListener(new FlipView.OnFlipListener() {
            @Override
            public void onFlippedToPage(FlipView flipView, int position, long l) {

                if(position == apods.size()-3){
                    Toast.makeText(flipView.getContext(), "Flipped to page " + position, Toast.LENGTH_SHORT).show();

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


}
