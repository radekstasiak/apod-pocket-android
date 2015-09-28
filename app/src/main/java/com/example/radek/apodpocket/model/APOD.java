package com.example.radek.apodpocket.model;

import java.io.Serializable;

/**
 * Created by Radek on 27/09/15.
 */
public class APOD implements Serializable {

    private String url;
    private String media_type;
    private String title;
    private String explanation;
    private String concepts;
    private String date;

    public APOD(String url, String media_type, String explanation) {
        this.url = url;
        this.media_type = media_type;
        this.explanation = explanation;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
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


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getConcepts() {
        return concepts;
    }

    public void setConcepts(String concepts) {
        this.concepts = concepts;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }




}
