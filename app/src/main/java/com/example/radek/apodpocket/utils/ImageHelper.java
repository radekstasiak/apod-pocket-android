package com.example.radek.apodpocket.utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.os.Build;
import android.util.Log;
import android.view.Display;
import android.view.WindowManager;
import android.widget.ImageView;

import com.example.radek.apodpocket.R;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * This class was created by me, here and now.
 * Turn on, tune in, drop out.
 */
public final class ImageHelper {
    private static final String TAG = "ImageHelper";
    public static void loadImage(Context context, ImageView imageView, String imageUrl) {

        Picasso.with(context).load(imageUrl).fit().centerCrop().into(imageView);
        // ImageLoader imageLoader = ImageLoader.getInstance();
        //imageLoader.displayImage(imageUrl, imageView);

        //  viewHolder.twitterUserImage.setImageUrl(tweet.getUserImageUrl(), ImageCacheManager.getInstance().getImageLoader());

    }

    public static int getImageHeight(Context mContext, String imageUrl){
        Bitmap image = null;
        try {
            image = Picasso.with(mContext).load(imageUrl).get();
        } catch (IOException e) {
            e.printStackTrace();
        }
       return image.getHeight();
    }



    public static float getDensity(Context mContext){

        return mContext.getResources().getDisplayMetrics().density;
    }

    @SuppressWarnings("deprecation")
    @SuppressLint("NewApi")
    public static int getScreenHeight(Activity context) {

        Display display = context.getWindowManager().getDefaultDisplay();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            Point size = new Point();
            display.getSize(size);
            return size.y;
        }
        return display.getHeight();
    }

    /**
     * Get the screen width.
     *
     * @param context
     * @return the screen width
     */
    @SuppressWarnings("deprecation")
    @SuppressLint("NewApi")
    public static int getScreenWidth(Activity context) {

        Display display = context.getWindowManager().getDefaultDisplay();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            Point size = new Point();
            display.getSize(size);
            return size.x;
        }
        return display.getWidth();
    }

    public static void storeImage(Bitmap image, File pictureFile) {
        if (pictureFile == null) {
            Log.d(TAG, "Error creating media file, check storage permissions: ");
            return;
        }
        try {
            FileOutputStream fos = new FileOutputStream(pictureFile);
            image.compress(Bitmap.CompressFormat.PNG, 90, fos);
            fos.close();
        } catch (FileNotFoundException e) {
            Log.d(TAG, "File not found: " + e.getMessage());
        } catch (IOException e) {
            Log.d(TAG, "Error accessing file: " + e.getMessage());
        }
    }

}
