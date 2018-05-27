package com.example.dovebook.utils;

import android.util.Log;

public class StringUtil {
    public static boolean isNull(String s) {
        if ("".equals(s) || s == null) {
            return true;
        }
        return false;
    }

    public static boolean isFloatString(String s) {
        int countOfPoint = 0;
        for (int i = 0; i < s.length(); i++) {
            if (Character.isDigit(s.charAt(i))) {
                Log.d("test", "isFloatString: " + s.charAt(i));
            } else if (s.charAt(i) == '.') {
                countOfPoint++;
            } else {
                Log.d("error", "isFloatString: " + s.charAt(i));
                return false;
            }
        }
        Log.d("error1", "isFloatString: " + s);
        return true;
    }
}
