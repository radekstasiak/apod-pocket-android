package com.example.radek.apodpocket;

import android.app.Activity;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.LinearLayoutManager;
import android.view.Menu;
import android.view.MenuItem;

import com.example.radek.apodpocket.adapter.APODAdapter;
import com.example.radek.apodpocket.model.APOD;
import com.example.radek.apodpocket.network.APIUtils;
import com.example.radek.apodpocket.network.APODRequest;


public class APODList extends Activity {


    private RecyclerView mRecyclerView;
    private APODAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private  APIUtils apiUtils;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pictures_list);
        apiUtils=new APIUtils(this);
        apiUtils.openAPODrequest();

        initUI();
    }

    private void initUI(){
        setAdapter();
    }

    private void setAdapter() {

        mRecyclerView = (RecyclerView) findViewById(R.id.apods_rv);
        mRecyclerView.setHasFixedSize(true);

        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        mAdapter = new APODAdapter(this);
        mRecyclerView.setAdapter(mAdapter);
        //addItemTouchListener();
        setListener();

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
                    if ((visibleItemCount + pastVisiblesItems) >= totalItemCount-5) {
                        apiUtils.openAPODrequest();
                    }
                }
            }
        });
    }

    ;
    public void saveData(APOD apodElement){

        mAdapter.setData(apodElement);
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
}
