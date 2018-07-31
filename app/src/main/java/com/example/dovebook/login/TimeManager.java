package com.example.dovebook.login;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;

import com.example.dovebook.base.BaseApp;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by zyd on 2018/3/23.
 */

public class TimeManager {
    private static final String TAG = "TimeManager";
    private static java.util.Date sDate;
    private static final SimpleDateFormat sf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    public static SharedPreferences sTimePref;



    public static void getDate(){
        sDate=new java.util.Date();
    }

    public static boolean isLoginTime(){
        getDate();
        if(sTimePref==null){
            sTimePref= BaseApp.getContext().getSharedPreferences("Time",Context.MODE_PRIVATE);
        }
        String dataStr=sTimePref.getString("data",null);
        if(dataStr!=null){
            try {
                Date data= (Date) sf.parse(dataStr);
                int result=sDate.compareTo(data);
                Log.d(TAG, "isLoginTime: before:"+sf.format(sDate)+" after:"+sf.format(data));
                if(result<0){
                    return true;
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return false;
    }
    public static void setTime(){
        getDate();
        if (sTimePref==null){
            sTimePref= BaseApp.getContext().getSharedPreferences("Time",Context.MODE_PRIVATE);
        }
        Date date=new Date(sDate.getTime()+24*60*60*1000);
        sTimePref.edit()
        .putString("data",sf.format(date))
        .apply();
        Log.d(TAG, "setTime: "+sTimePref.getString("data",null));
    }

    public static void clearLoginTime(){
        if(sTimePref==null){
            sTimePref= BaseApp.getContext().getSharedPreferences("Time",Context.MODE_PRIVATE);
        }
        sTimePref.edit()
                .clear()
                .apply();
    }
}
