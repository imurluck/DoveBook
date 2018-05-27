package com.example.dovebook.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by zzx on 18-1-28.
 */

public class DateUtil {

    /**
     * 将Unix时间戳转换成日月年时分秒格式
     * @param timeStamp
     * @return
     */
    public static String timeStampToDate(long timeStamp) {
        return new SimpleDateFormat("dd/MM/yyyy HH:mm:ss")
                .format(new Date(timeStamp));
    }
}
