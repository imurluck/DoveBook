package com.example.dovebook.login;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by zyd on 2018/3/23.
 */

public class TimeManager {
    private static final String TAG = "TimeManager";
    private Context mContext;
    private java.util.Date mDate;
    private SimpleDateFormat sf;
    private SharedPreferences pref;


    public TimeManager(Context context){
        this.mContext=context;
        sf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        pref=mContext.getSharedPreferences("Time",Context.MODE_PRIVATE);
    }

    public void getDate(){
        mDate=new java.util.Date();
    }

    public boolean isLoginTime(){
        getDate();
        String dataStr=pref.getString("data",null);
        if(dataStr!=null){
            try {
                Date data= (Date) sf.parse(dataStr);
                int result=mDate.compareTo(data);
                if(result<0){
                    return true;
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return false;
    }
    public void setTime(){
        if(mDate==null){
            mDate=new java.util.Date();
        }
        Date date=new Date(mDate.getTime()+24*60*60*1000);
        SharedPreferences.Editor editor=pref.edit();
        editor.putString("data",sf.format(date));
        editor.apply();
    }
}
