package com.example.radek.apodpocket.images;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Point;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.Display;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Radek on 29/09/15.
 */
public class ImageUtils {
    public static final int IO_BUFFER_SIZE = 8 * 1024;
    private static final String TAG = "ImageUtils";
    private ImageUtils() {};

    public static boolean isExternalStorageRemovable() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD) {
            return Environment.isExternalStorageRemovable();
        }
        return true;
    }

    public static File getExternalCacheDir(Context context) {
        if (hasExternalCacheDir()) {
            return context.getExternalCacheDir();
        }

        // Before Froyo we need to construct the external cache dir ourselves
        final String cacheDir = "/Android/data/" + context.getPackageName() + "/cache/";
        return new File(Environment.getExternalStorageDirectory().getPath() + cacheDir);
    }

    public static boolean hasExternalCacheDir() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.FROYO;
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

    public static void getBlurredImage(final Context context,
                                       final Drawable bitmapRes, final String nameToSave, final int radius,
                                       final BlurEffectListener listener) {
        new Thread(new Runnable() {

            @Override
            public void run() {
                File saveFile = new File(context.getFilesDir(), nameToSave);
                Bitmap blurredBitmap = null;
                if (!saveFile.exists()) {
                    try {
                        Bitmap orgBitmap = drawableToBitmap(bitmapRes);
                        blurredBitmap = Blur.fastblur(context, orgBitmap,
                                radius);
                        saveFile.createNewFile();
                        ImageUtils.storeImage(blurredBitmap, saveFile);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                } else {
                    blurredBitmap = BitmapFactory.decodeFile(saveFile
                            .getAbsolutePath());
                }
                final Bitmap tempBitmap = blurredBitmap;
                Handler handler = new Handler(Looper.getMainLooper());
                handler.post(new Runnable() {

                    @Override
                    public void run() {
                        listener.onDone(tempBitmap);
                    }
                });

            }
        }).start();
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

    public static interface BlurEffectListener {

        public void onDone(Bitmap bitmap);
    }
    public static String getImageName(String filename) {
        return filename + "_cached.png";
    }
    public static Drawable drawableFromUrl(String url) throws IOException {
        Bitmap x;

        HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
        connection.connect();
        InputStream input = connection.getInputStream();

        x = BitmapFactory.decodeStream(input);
        return new BitmapDrawable(x);
    }

    public static Bitmap drawableToBitmap (Drawable drawable) {
        Bitmap bitmap = null;

        if (drawable instanceof BitmapDrawable) {
            BitmapDrawable bitmapDrawable = (BitmapDrawable) drawable;
            if(bitmapDrawable.getBitmap() != null) {
                return bitmapDrawable.getBitmap();
            }
        }

        if(drawable.getIntrinsicWidth() <= 0 || drawable.getIntrinsicHeight() <= 0) {
            bitmap = Bitmap.createBitmap(1, 1, Bitmap.Config.ARGB_8888); // Single color bitmap will be created of 1x1 pixel
        } else {
            bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        }

        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);
        return bitmap;
    }
}