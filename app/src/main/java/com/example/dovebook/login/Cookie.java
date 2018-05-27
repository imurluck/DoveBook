package com.example.dovebook.login;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by zyd on 2018/3/23.
 */

public class Cookie {
    private Context mContext;
    Cookie(Context context){
        this.mContext=context;
    }
    public boolean existCookie(){
        SharedPreferences pref=mContext.getSharedPreferences("Cookie",Context.MODE_PRIVATE);
        String cookie=pref.getString("cookie",null);
        if(null==cookie){
            return false;
        }
        else return true;
    }
    public String getCookie(){
        SharedPreferences pref=mContext.getSharedPreferences("Cookie",Context.MODE_PRIVATE);
        String cookie=pref.getString("cookie",null);
        return cookie;
    }
}
