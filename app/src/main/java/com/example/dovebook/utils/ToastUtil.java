package com.example.dovebook.utils;

import android.widget.Toast;

import com.example.dovebook.base.BaseApp;

/**
 * Created by zzx on 18-1-28.
 */

public class ToastUtil {

    public static void shortToast(String content) {
        Toast.makeText(BaseApp.getContext(), content, Toast.LENGTH_SHORT).show();
    }
}
