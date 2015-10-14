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

import com.aphidmobile.flip.FlipViewController;
import com.example.radek.apodpocket.adapter.APODFlipAdapter;
import com.example.radek.apodpocket.interfaces.DataInterface;
import com.example.radek.apodpocket.model.APOD;
import com.example.radek.apodpocket.network.APIUtils;
import com.example.radek.apodpocket.utils.StorageMenagerHelper;

import java.io.IOException;
import java.util.ArrayList;


public class APODFlipViewActivity extends Activity implements DataInterface {
    private APODFlipAdapter apodAdapter;
    private FlipViewController flipViewController;
    private APIUtils apiUtils;
    private ArrayList<APOD> apods = new ArrayList<APOD>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        apiUtils = new APIUtils(this);
        apiUtils.openAPODrequest();

        initUI();

        setContentView(flipViewController);
    }

    private void initUI() {

        setFlipView();
    }

    private void setFlipView() {

        flipViewController = new FlipViewController(this);
        apodAdapter = new APODFlipAdapter(this);
        flipViewController.setAdapter(apodAdapter);
        flipViewController.setOnViewFlipListener(new FlipViewController.ViewFlipListener() {

            @Override
            public void onViewFlipped(View view, int position) {

                if (position==apods.size()-3){
                    apiUtils.openAPODrequest();
                }
            }
        });

        flipViewController.setOverFlipEnabled(false);

    }

    @Override
    protected void onResume() {
        super.onResume();
        flipViewController.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        flipViewController.onPause();
    }

    @Override
    public void saveData(APOD apodElement) {

    }

    @Override
    public void readData() throws IOException {

        apods = StorageMenagerHelper.readFromInternalStorage(this);
        apodAdapter.setData(apods);
    }


//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.menu_apodflip_view, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//
//        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }
//
//        return super.onOptionsItemSelected(item);
//    }
}
