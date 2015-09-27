package com.example.radek.apodpocket.network;

import com.example.radek.apodpocket.Constants;
import com.example.radek.apodpocket.model.APOD;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.util.ArrayList;

/**
 * Created by Radek on 27/09/15.
 */
public class APODRequest {

    public static ArrayList<String> getRequests(){

        ArrayList<String> uriList= new ArrayList<String>();


        for(int i=0; i < Constants.NASA_API_RESPONSE_NUMBERY;i++) {
            DateTime date = new DateTime().minusDays(i);
            DateTimeFormatter fmt = DateTimeFormat.forPattern("yyyy-MM-dd");
            String uri = String.format(Constants.NASA_API_URL + Constants.NASA_API_APOD,
                    Constants.NASA_API_KEY,
                    date.toString(fmt));

            uriList.add(uri);
        }


        return uriList;

    }
}
