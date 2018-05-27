package com.example.dovebook.register;

import android.app.ProgressDialog;
import android.content.Context;
import android.widget.Toast;

import com.example.dovebook.base.model.User;
import com.example.dovebook.common.Constant;
import com.example.dovebook.net.Api;
import com.example.dovebook.net.HttpManager;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.RequestBody;

/**
 * Created by zyd on 2018/4/11.
 */

public class registerPresenter {
    private register mRegister;
    private ProgressDialog registering;
    public registerPresenter(register mRegister){
        this.mRegister=mRegister;
    }

    public void register(RequestBody userName,RequestBody userPassword){
        registering=ProgressDialog.show(mRegister,"提示", "正在注册", false,true);
        Api api=HttpManager.getInstance().getApiService(Constant.BASE_URL);
        api.register(userName,userPassword)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<User>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(User user) {
                        registering.dismiss();
                        Toast.makeText(mRegister,"注册成功",Toast.LENGTH_SHORT).show();
                        mRegister.finish();
                    }

                    @Override
                    public void onError(Throwable e) {
                        registering.dismiss();
                        Toast.makeText(mRegister,"注册失败",Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
}
