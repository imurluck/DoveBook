package com.example.dovebook.utils;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.WindowManager;

import com.example.dovebook.base.BaseApp;

public class WindowUtil {

    public static DisplayMetrics getWindowDisplayMetrics() {
        WindowManager wm = (WindowManager) BaseApp.getContext().getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics dm = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(dm);
        return dm;
    }
}
