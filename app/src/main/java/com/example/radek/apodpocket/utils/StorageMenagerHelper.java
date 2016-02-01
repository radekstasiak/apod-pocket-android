package com.example.radek.apodpocket.utils;

import android.content.Context;
import android.util.Log;

import com.example.radek.apodpocket.Constants;
import com.example.radek.apodpocket.model.APOD;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * Created by Radek on 06/10/15.
 */
public class StorageMenagerHelper {

    public static void saveToInternalStorage(Context ctx, APOD[] apods) {
        if (apods != null) {
            try {
                ArrayList<APOD> data = readFromInternalStorage(ctx);
                for(APOD apod:apods){
                    data.add(apod);
                    data=ArrayHelper.sortList(data,apod);
                }
                FileOutputStream fos = ctx.openFileOutput(Constants.STORAGE_FILENAME, ctx.MODE_PRIVATE);
                ObjectOutputStream of = new ObjectOutputStream(fos);
                of.writeObject(data);
                of.flush();
                of.close();
                fos.close();
            } catch (Exception e) {
                Log.e("InternalStorage", e.getMessage());
            }
        }

    }

    public static ArrayList<APOD> readFromInternalStorage(Context ctx) throws IOException {
        ArrayList<APOD> toReturn = new ArrayList<APOD>();
        FileInputStream fis;
        try {

            fis = ctx.openFileInput(Constants.STORAGE_FILENAME);
            ObjectInputStream oi = new ObjectInputStream(fis);
            toReturn = (ArrayList<APOD>) oi.readObject();
            oi.close();
        } catch (FileNotFoundException e) {
            return new ArrayList<APOD>();
        } catch (IOException e) {
            Log.e("InternalStorage", e.getMessage());
        } catch (ClassNotFoundException e) {
            Log.e("InternalStorage", e.getMessage());
        }
        return toReturn;
    }

    private static void createStorage(Context ctx) throws IOException {
        ArrayList<APOD> emptyList = new ArrayList<APOD>();
        APOD emptyApod = new APOD("AAA","BBB","CCC");
        emptyList.add(emptyApod);
        FileOutputStream fos = ctx.openFileOutput(Constants.STORAGE_FILENAME, ctx.MODE_PRIVATE);
        ObjectOutputStream of = new ObjectOutputStream(fos);
        of.writeObject(emptyList);
        of.flush();
        of.close();
        fos.close();

    }
    private static boolean ifStorageExists(Context context) {

        File file = context.getFileStreamPath(Constants.STORAGE_FILENAME);
        if(file == null || !file.exists()) {
            return false;

        }
        return true;
    }


}
