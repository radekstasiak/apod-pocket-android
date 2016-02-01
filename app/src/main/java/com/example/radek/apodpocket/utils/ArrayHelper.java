package com.example.radek.apodpocket.utils;

import com.example.radek.apodpocket.model.APOD;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * Created by Radek on 08/10/15.
 */
public class ArrayHelper {


    public static ArrayList<APOD> sortList(ArrayList<APOD> apodsList, APOD newApod) {

        ArrayList<APOD> sortedList = apodsList;
        if (newApod != null) {
            if (!newApod.getTitle().isEmpty() && newApod.getType() != "video") {
                //sortedList.add(newApod);
                Collections.sort(sortedList, new Comparator<APOD>() {

                    public int compare(APOD apod1, APOD apod2) {
                        String obj1 = apod1.getDate();
                        String obj2 = apod2.getDate();
                        if (obj1 == obj2) {
                            return 0;
                        }
                        if (obj1 == null) {
                            return -1;
                        }
                        if (obj2 == null) {
                            return 1;
                        }
                        return obj2.compareTo(obj1);
                    }
                });
            }
        }

        return sortedList;
    }

}
