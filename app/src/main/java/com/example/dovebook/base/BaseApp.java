package com.example.dovebook.base;

import android.app.Application;
import android.content.Context;

/**
 * Created by zzx on 18-1-28.
 */

public class BaseApp extends Application {

    public static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
    }

    public static Context getContext() {
        return context;
    }
}
