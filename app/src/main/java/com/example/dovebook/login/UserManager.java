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
        if(mUser==null){
            mUser=new User();
            mUser = new User();
            mUser.setUserName(pref.getString("userName", ""));
            mUser.setUserId(pref.getString("userId", ""));
            mUser.setUserPhone(pref.getLong("userPhone", -1));
            mUser.setUserAge(pref.getInt("userAge", -1));
            mUser.setUserEmail(pref.getString("userEmail", ""));
            mUser.setUserAvatarPath(pref.getString("userAvatarpath", ""));
        }
        return mUser;
    }

    public void setUser(User user) {
        mUser = user;
        SharedPreferences.Editor editor = pref.edit();
        editor.putString("userId", user.getUserId());
        editor.putString("userName", user.getUserName());
        if(user.getUserPhone()!=null) {
            editor.putLong("userPhone", user.getUserPhone());
        }else{
            editor.putLong("userPhone", -1);
        }
//        editor.putString("Bgpath",user.getUserBgPath().toString());
        editor.putString("userAvatarpath", user.getUserAvatarPath());
//        editor.putString("userSex",user.getUserSex().toString());
        if(user.getUserAge()!=null) {
            editor.putInt("userAge", user.getUserAge());
        }else{
            editor.putInt("userAge",-1);
        }
        editor.putString("userEmail", user.getUserEmail());
//        editor.putString("userProfile",user.getUserProfile().toString());
//        editor.putString("userTaskcapacity",user.getUserTaskCapacity().toString());
        editor.apply();
    }

}
