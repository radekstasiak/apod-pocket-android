package com.example.radek.apodpocket.utils;

import android.content.Context;

/**
 * This class was created by me, here and now.
 * Turn on, tune in, drop out.
 */
public class FontHelper {



        public static float pixelsToSp(Context context, float px) {
            float scaledDensity = context.getResources().getDisplayMetrics().scaledDensity;
            return px/scaledDensity;

    }
}
