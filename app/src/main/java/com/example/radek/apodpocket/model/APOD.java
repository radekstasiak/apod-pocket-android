package com.example.radek.apodpocket.model;

import java.io.Serializable;

/**
 * Created by Radek on 27/09/15.
 */
public class APOD implements Serializable {

    private String url;
    private String hd_url;
    private String copyright;
    private String type;
    private String title;
    private String explanation;
    private String concepts;
    private String date;

    public APOD(String url, String type, String explanation) {
        this.url = url;
        this.type = type;
        this.explanation = explanation;
    }


    public String getHd_url() {
        return hd_url;
    }

    public void setHd_url(String hd_url) {
        this.hd_url = hd_url;
    }


    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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
