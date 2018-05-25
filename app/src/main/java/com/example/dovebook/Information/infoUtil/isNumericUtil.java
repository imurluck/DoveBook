package com.example.dovebook.Information.infoUtil;

/**
 * Created by zyd on 2018/4/12.
 */

public class isNumericUtil {
    public static boolean isNumeric(String str){
        for (int i = str.length(); --i>=0;) {
            if (!Character.isDigit(str.charAt(i))) {
                return false;
            }
        }
        return true;
    }
}
