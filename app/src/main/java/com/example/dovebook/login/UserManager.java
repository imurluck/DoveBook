package com.example.dovebook.login;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;

import com.example.dovebook.base.model.User;

/**
 * Created by zyd on 2018/3/26.
 */

public class UserManager {
    private static String TAG = "UserManager";
    public static volatile User mUser;
    private Context mContext;
    private SharedPreferences pref;

    public UserManager(Context context) {
        mContext = context;
        pref = mContext.getSharedPreferences("User", Context.MODE_PRIVATE);
    }


    public User getUser() {
        if (mUser == null) {
            mUser = new User();
            mUser = new User();
            mUser.setUserName(pref.getString("userName", null));
            mUser.setUserId(pref.getString("userId", null));
            mUser.setUserPhone(pref.getLong("userPhone", 0));
            Log.d(TAG, "getUser: " + pref.getInt("userAge", -10));
            mUser.setUserAge(pref.getInt("userAge", -1));
            mUser.setUserEmail(pref.getString("userEmail", null));
            mUser.setUserAvatarPath(pref.getString("userAvatarpath", null));
        }
        return mUser;
    }

    public void setUser(User user) {
        mUser = user;
        SharedPreferences.Editor editor = pref.edit();
        editor.putString("userId", user.getUserId());
        editor.putString("userName", user.getUserName());
        editor.putLong("userPhone", user.getUserPhone());
//        editor.putString("Bgpath",user.getUserBgPath().toString());
        editor.putString("userAvatarpath", user.getUserAvatarPath());
//        editor.putString("userSex",user.getUserSex().toString());
        Log.d(TAG, "setUser: " + "userage:" + user.getUserAge());
        if (user.getUserAge() != null) {
            editor.putInt("userAge", user.getUserAge());
        } else {
            mUser.setUserAge(-1);
            editor.putInt("userAge", -1);
        }

        editor.putString("userEmail", user.getUserEmail());
//        editor.putString("userProfile",user.getUserProfile().toString());
//        editor.putString("userTaskcapacity",user.getUserTaskCapacity().toString());
        editor.apply();
    }

}
