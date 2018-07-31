package com.example.dovebook.login;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.example.dovebook.base.model.User;
import com.example.dovebook.common.Constant;
import com.example.dovebook.main.MainActivity;
import com.example.dovebook.net.Api;
import com.example.dovebook.net.HttpManager;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by zyd on 2018/3/19.
 */

public class LoginPresenter {
    private static final String TAG = "LoginPresenter";
    private Context mContext;
    private SharedPreferences pref;
    private ProgressDialog logining;
    public LoginPresenter(Context context) {
        this.mContext = context;
        pref=mContext.getSharedPreferences("loginMessage",Context.MODE_PRIVATE);
    }

    public void login(String userName , String userPassword/*,final TimeManager timeManager,final UserManager userManager*/) {
        logining= ProgressDialog.show(mContext, "提示", "正在登陆", false,true);
        Api api = HttpManager.getInstance().getApiService(Constant.BASE_LOGIN_URL);
        api.login(userName, userPassword)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<User>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(User user) {
                        TimeManager.setTime();
//                        userManager.setUser(user);
                        Log.d(TAG, "onNext: OK");
                        logining.dismiss();
                        Toast.makeText(mContext,"登陆成功",Toast.LENGTH_SHORT).show();
                        ((Activity)mContext).startActivity(new Intent((Activity)mContext, MainActivity.class));
                        ((Activity) mContext).finish();
                    }

                    @Override
                    public void onError(Throwable e) {
                        logining.dismiss();
                        Toast.makeText(mContext,"登录失败",Toast.LENGTH_SHORT).show();
                        Log.e(TAG, "loginOnError: " + e.getCause());
                        //getSelfListFromServer("02a618bf-0241-11e8-bd05-00163e0ac98c");
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
    public boolean hasRememberPas(EditText editTextA,EditText editTextP){
        boolean isRemember=pref.getBoolean("remember_PassWord",false);
        if(isRemember){
            editTextA.setText(pref.getString("account",null));
            editTextP.setText(pref.getString("password",null));
            return true;
        }
        return false;
    }
    public boolean isRemember(CheckBox mCheckBox,EditText editTextA,EditText editTextP){
        SharedPreferences.Editor editor=pref.edit();
        if(mCheckBox.isChecked()){
            editor.putString("account",editTextA.getText().toString());
            editor.putString("password",editTextP.getText().toString());
            editor.putBoolean("remember_PassWord",true);
            editor.apply();
            return true;
        }
        else{
            editor.clear();
            editor.apply();
        }
        return false;
    }
}
