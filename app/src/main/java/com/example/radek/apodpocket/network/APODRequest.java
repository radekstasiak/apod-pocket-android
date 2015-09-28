package com.example.radek.apodpocket.network;

import com.example.radek.apodpocket.Constants;
import com.example.radek.apodpocket.model.APOD;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Radek on 27/09/15.
 */
public class APODRequest {

    public static HashMap<String,String> getRequests(){

        HashMap<String,String> uriList= new HashMap<String,String>();


        for(int i=0; i < Constants.NASA_API_RESPONSE_NUMBERY;i++) {
            DateTime date = new DateTime().minusDays(i);
            DateTimeFormatter fmt = DateTimeFormat.forPattern("yyyy-MM-dd");
            String uri = String.format(Constants.NASA_API_URL + Constants.NASA_API_APOD,
                    Constants.NASA_API_KEY,
                    date.toString(fmt));

            uriList.put(date.toString(fmt),uri);
        }


        return uriList;

    }
}
