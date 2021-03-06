package com.example.radek.apodpocket.network;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;


import android.app.Activity;
import android.util.Log;
import android.widget.Toast;


import com.example.radek.apodpocket.APODList;
import com.example.radek.apodpocket.ApodViewFragment;
import com.example.radek.apodpocket.interfaces.DataInterface;
import com.example.radek.apodpocket.model.APOD;
import com.example.radek.apodpocket.model.HomeResponse;
import com.example.radek.apodpocket.utils.ArrayHelper;
import com.example.radek.apodpocket.utils.StorageMenagerHelper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

/**
 * Created by Radek on 16/09/15.
 */
public class APIUtils {
    private final String TAG="APOD REQUEST";
    private String globalResponse;
    private Activity mActivity;
    private ArrayList<APOD> apodsList;
    public APIUtils(Activity act){

        mActivity = act;
        apodsList = new ArrayList<APOD>();
    }
    public String getGlobalResponse(){
        return globalResponse;
    }

    public void openAPODrequest(){

        getAPODS();
    }
    public void getAPODS(){
        APODRequest apodRequest;
            String result=APODRequest.getRequest();

            Log.d(TAG,result);
            StringRequest request = new StringRequest(Request.Method.GET, result,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {

                            APOD[] apods = HomeResponse.fromJsonObject(response);
                            try {
                                StorageMenagerHelper.saveToInternalStorage(mActivity, apods);

                                        ((DataInterface) mActivity).readData();
                            } catch (IOException e) {

                            }
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(mActivity, error.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
            VolleyApplication.getInstance().getRequestQueue().add(request);


           // it.remove();



    }

//    public void getSingleAPOD(final String date){
//
//        String url=APODRequest.getSingleRequest(date);
//        StringRequest request = new StringRequest(Request.Method.GET, url,
//                new Response.Listener<String>() {
//                    @Override
//                    public void onResponse(String response) {
//
//                        APOD apodItem = HomeResponse.fromJsonObject(response);
//                        apodItem.setDate(date);
//                       // ((ApodViewFragment) mActivity).saveData(apodItem);
//                    }
//                }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                Toast.makeText(mActivity, error.getMessage(), Toast.LENGTH_SHORT).show();
//            }
//        });
//        VolleyApplication.getInstance().getRequestQueue().add(request);
//
//
//    }
}
