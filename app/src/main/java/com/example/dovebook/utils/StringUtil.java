package com.example.dovebook.utils;

public class StringUtil {
    public static boolean isNull(String s){
        if ("".equals(s) || s == null) {
            return  true;
        }
        return false;
    }
}
