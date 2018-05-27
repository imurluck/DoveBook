package com.example.dovebook.base;

import android.app.Application;
import android.content.Context;
import android.os.Handler;

/**
 * Created by zzx on 18-1-28.
 */

public class BaseApp extends Application {

    public static Context sContext;
    public static Handler sMainHandler;

    @Override
    public void onCreate() {
        super.onCreate();
        sContext = getApplicationContext();
        sMainHandler = new Handler(getMainLooper());
    }

    public static Context getContext() {
        return sContext;
    }

    public static Handler getMainHandler() {
        return sMainHandler;
    }
}
