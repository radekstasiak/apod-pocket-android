package com.example.radek.apodpocket.utils;

import android.content.Context;
import android.graphics.Point;
import android.view.Display;
import android.view.WindowManager;
import android.widget.ImageView;

import com.example.radek.apodpocket.R;
import com.squareup.picasso.Picasso;

/**
 * This class was created by me, here and now.
 * Turn on, tune in, drop out.
 */
public final class ImageHelper {

    public static void loadImage(Context context, ImageView imageView, String imageUrl) {

        Picasso.with(context).load(imageUrl).fit().centerCrop().into(imageView);
        // ImageLoader imageLoader = ImageLoader.getInstance();
        //imageLoader.displayImage(imageUrl, imageView);

        //  viewHolder.twitterUserImage.setImageUrl(tweet.getUserImageUrl(), ImageCacheManager.getInstance().getImageLoader());

    }

    public static int getDisplayHeight(Context mContext){
        Display display = getDisplay(mContext);
        Point size = new Point();

        display.getSize(size);

        int height = size.y;
        return height;

    }

    public static int getDisplayWidth(Context mContext){

        Display display = getDisplay(mContext);
        Point size = new Point();

        display.getSize(size);
        int width = size.x;

        return width;
    }

    public static Display getDisplay(Context mContext){
        WindowManager wm = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        return display;
    }

    public static float getDensity(Context mContext){

        return mContext.getResources().getDisplayMetrics().density;
    }

}
