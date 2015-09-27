package com.example.radek.apodpocket.network;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;


import android.app.Activity;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.radek.apodpocket.APODList;
import com.example.radek.apodpocket.model.APOD;
import com.example.radek.apodpocket.model.HomeResponse;
import com.example.radek.apodpocket.model.RequestManager;

import java.util.ArrayList;

/**
 * Created by Radek on 16/09/15.
 */
public class APIUtils {

    private String globalResponse;
    private APODList mActivity;
    public APIUtils(APODList act){

        mActivity = act;

    }
    public String getGlobalResponse(){
        return globalResponse;
    }

    public void openAPODrequest(){
//        VolleyApplication.getInstance().getRequestQueue().addRequestFinishedListener(new RequestQueue.RequestFinishedListener<Object>() {
//            @Override
//            public void onRequestFinished(Request<Object> request) {
//
//
//                Toast.makeText(mActivity, "KONIEC", Toast.LENGTH_SHORT).show();
//            }
//        });

        getAPODS();
    }
    public void getAPODS(){

        ArrayList<String> uriList=APODRequest.getRequests();

        for(String uri: uriList) {
            StringRequest request = new StringRequest(Request.Method.GET, uri,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            APOD apodItem = HomeResponse.fromJsonObject(response);
                            mActivity.saveData(apodItem);
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(mActivity, error.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
            RequestManager.getRequestQueue().add(request);

        }


    }
}
