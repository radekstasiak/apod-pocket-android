package com.example.radek.apodpocket.model;

/**
 * Created by Radek on 02/12/14.
 */
import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

import org.json.JSONObject;

import java.io.Serializable;

public class HomeResponse implements Serializable {

    public APOD[] apods;

    public static APOD[] fromJsonObject(String jsonObject) {
        Gson gson = new Gson();

        return gson.fromJson(jsonObject, APOD[].class);
    }

}
