package com.example.radek.apodpocket.interfaces;

import com.example.radek.apodpocket.model.APOD;

import java.io.IOException;

/**
 * Created by Radek on 04/10/15.
 */
public interface DataInterface {

    void saveData(APOD apodElement);
    void readData() throws IOException;
}
