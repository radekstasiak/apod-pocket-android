package com.example.radek.apodpocket;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;

import com.example.radek.apodpocket.adapter.APODAdapter;
import com.example.radek.apodpocket.interfaces.DataInterface;
import com.example.radek.apodpocket.model.APOD;
import com.example.radek.apodpocket.model.CustomRecyclerView;
import com.example.radek.apodpocket.network.APIUtils;
import com.example.radek.apodpocket.utils.StorageMenagerHelper;

import java.io.IOException;
import java.util.ArrayList;


public class APODList extends AppCompatActivity implements DataInterface {


    private CustomRecyclerView mRecyclerView;
    private APODAdapter mAdapter;
    private ArrayList<APOD> apods = new ArrayList<APOD>();
    private RecyclerView.LayoutManager mLayoutManager;
    private APIUtils apiUtils;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pictures_list);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        myToolbar.setTitle(getResources().getString(R.string.app_name));
        myToolbar.setTitleTextColor(getResources().getColor(R.color.brown));
        setSupportActionBar(myToolbar);
        apiUtils = new APIUtils(this);
        apiUtils.openAPODrequest();

        //lipView.setAdapter(mAdapter);
        //flipView.setOnCli
        initUI();

    }



    private void initUI() {
        setAdapter();
    }

    private void setAdapter() {

        mRecyclerView = (CustomRecyclerView) findViewById(R.id.apods_rv);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setItemViewCacheSize(5);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        mAdapter = new APODAdapter(this);
        mRecyclerView.setAdapter(mAdapter);
        setListener();
        addItemTouchListener();

    }

    private void setListener() {

        mRecyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {

            private boolean loading = true;
            int pastVisiblesItems, visibleItemCount, totalItemCount;

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {

                visibleItemCount = mLayoutManager.getChildCount();
                totalItemCount = mLayoutManager.getItemCount();
                pastVisiblesItems = ((LinearLayoutManager) recyclerView.getLayoutManager()).findFirstVisibleItemPosition();


                if (loading) {
                    if ((visibleItemCount + pastVisiblesItems) >= totalItemCount) {

                         apiUtils.openAPODrequest();
                    }
                }
            }
        });
    }

    public void addItemTouchListener() {
        final GestureDetector mGestureDetector = new GestureDetector(APODList.this, new GestureDetector.SimpleOnGestureListener() {

            @Override
            public boolean onSingleTapUp(MotionEvent e) {
                return true;
            }

        });
        mRecyclerView.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {


            @Override
            public boolean onInterceptTouchEvent(RecyclerView recyclerView, MotionEvent motionEvent) {

                View child = recyclerView.findChildViewUnder(motionEvent.getX(), motionEvent.getY());

                if (child != null && mGestureDetector.onTouchEvent(motionEvent)) {
                    Intent intent = new Intent(APODList.this, ApodViewActivity.class);
                    APODAdapter.ViewHolder viewHolderElement = (APODAdapter.ViewHolder) recyclerView.getChildViewHolder(child);


                    intent.putExtra("APOD_DATE", viewHolderElement.getPosition());

                    startActivityForResult(intent, 1);

                    return true;

                }

                return false;
            }

            @Override
            public void onTouchEvent(RecyclerView recyclerView, MotionEvent motionEvent) {

            }


        });
    }

    ;

    @Override
    public void saveData(APOD apodElement) {

        // mAdapter.setData(apodElement);
    }

    @Override
    public void readData() throws IOException {

        apods = StorageMenagerHelper.readFromInternalStorage(this);
        mAdapter.setData(apods);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_pictures_list, menu);
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

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                int currentPosition = data.getIntExtra("last_viewed_page", 0);
                try {
                    readData();
                    mLayoutManager.scrollToPosition(currentPosition);
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }
    }
}
