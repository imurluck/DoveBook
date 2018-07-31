package com.example.dovebook.login;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;

import com.example.dovebook.base.BaseApp;
import com.example.dovebook.base.model.User;

/**
 * Created by zyd on 2018/3/26.
 */

public class UserManager {
    private static String TAG = "UserManager";
    public static volatile User sUser;
    private static SharedPreferences sUserPref;


    public static User getUser() {
        if (sUser == null) {
            sUser=new User();
            if(sUserPref==null){
                sUserPref = BaseApp.getContext().getSharedPreferences("User", Context.MODE_PRIVATE);
            }
            sUser.setUserName(sUserPref.getString("userName", null));
            sUser.setUserId(sUserPref.getString("userId", null));
            sUser.setUserPhone(sUserPref.getLong("userPhone", 0));
            Log.d(TAG, "getUser: " + sUserPref.getInt("userAge", -10));
            sUser.setUserAge(sUserPref.getInt("userAge", -1));
            sUser.setUserEmail(sUserPref.getString("userEmail", null));
            sUser.setUserAvatarPath(sUserPref.getString("userAvatarpath", null));
        }
        return sUser;
    }

    public static void setUser(User user) {
        sUser = user;
        if (sUserPref==null){
            sUserPref = BaseApp.getContext().getSharedPreferences("User", Context.MODE_PRIVATE);
        }
        SharedPreferences.Editor editor = sUserPref.edit();
        editor.putString("userId", user.getUserId());
        editor.putString("userName", user.getUserName());
        editor.putLong("userPhone", user.getUserPhone());
        editor.putString("userAvatarpath", user.getUserAvatarPath());
        Log.d(TAG, "setUser: " + "userage:" + user.getUserAge());
        if (user.getUserAge() != null) {
            editor.putInt("userAge", user.getUserAge());
        } else {
            sUser.setUserAge(-1);
            editor.putInt("userAge", -1);
        }

        editor.putString("userEmail", user.getUserEmail());
        editor.apply();
    }



}
