package com.example.radek.apodpocket.network;

import com.android.volley.Request;
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
    public void getAPODS(){

        ArrayList<String> uriList=APODRequest.getRequests();

        for(String uri: uriList) {
            StringRequest request = new StringRequest(Request.Method.GET, uri,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            //TODO add user id to string
                            Toast.makeText(mActivity, response, Toast.LENGTH_SHORT).show();
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(mActivity, error.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });

            VolleyApplication.getInstance().getRequestQueue().add(request);
        }


    }
}
