package com.example.dovebook.utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.util.Log;

import com.example.dovebook.bookupload.BookUploadActivity;
import com.example.dovebook.images.ImageManager;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.ref.WeakReference;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by zjs on 2018/4/17.
 */

public class PhotoUtil {

    /**
     * 将Bitmap转换为File
     *
     * @param bitmap
     * @return
     */
    public static File getFileFromBitmap(Bitmap bitmap) {
        SimpleDateFormat format = new SimpleDateFormat("MMddHHmmss");
        Date date = new Date(System.currentTimeMillis());
        String filename = "dove_book_" + format.format(date);
        File file = new File(Environment.getExternalStorageDirectory(), filename + ".jpg");
        try {
            BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(file));
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bos);

            bos.flush();
            bos.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return file;
    }

}