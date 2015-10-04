package com.example.radek.apodpocket;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import android.os.Parcelable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;

import com.example.radek.apodpocket.adapter.APODAdapter;
import com.example.radek.apodpocket.interfaces.SaveDataInterface;
import com.example.radek.apodpocket.model.APOD;
import com.example.radek.apodpocket.model.CustomRecyclerView;
import com.example.radek.apodpocket.network.APIUtils;


public class APODList extends Activity implements SaveDataInterface {


    private CustomRecyclerView mRecyclerView;
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

                    //ProgressDialog pdia = MPSApp.getProgressDialog(ArticlesList.this);

                    //pdia.setTitle(getResources().getString(R.string.pdia_loading));
                    //pdia.setCancelable(false);


                    Intent intent = new Intent(APODList.this, ApodView.class);
                    APODAdapter.ViewHolder viewHolderElement = (APODAdapter.ViewHolder) recyclerView.getChildViewHolder(child);
                    //categoriesArticlesPosition = new HashMap();
                    //categoriesArticlesPosition.put("category_id", category_id);
                    //categoriesArticlesPosition.put("article_id", viewHolderElement.getLayoutPosition());

                    //new ShowProgressDialogTask().execute("");
                    //Bundle bundle = new Bundle();
                    //bundle.putSerializable(Constants.ARTICLE_ADAPTER, categoriesArticlesPosition);
                    String date = viewHolderElement.mDate.getText().toString();
                    try {
                        //    Thread.sleep(5000);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    //intent.putExtras(bundle);
                    intent.putExtra("APOD_DATE", date);
                    startActivity(intent);
                    //endLogging();

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
