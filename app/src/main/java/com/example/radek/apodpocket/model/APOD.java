package com.example.radek.apodpocket.model;

/**
 * Created by Radek on 27/09/15.
 */
public class APOD {

    private String url;
    private String media_type;
    private String explanation;

    public APOD(String url, String media_type, String explanation) {
        this.url = url;
        this.media_type = media_type;
        this.explanation = explanation;
    }

    public String getMedia_type() {
        return media_type;
    }

    public void setMedia_type(String media_type) {
        this.media_type = media_type;
    }

    public String getExplanation() {
        return explanation;
    }

    public void setExplanation(String explanation) {
        this.explanation = explanation;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }




}
