package com.example.dovebook.register;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.dovebook.R;
import com.example.dovebook.base.BaseActivity;
import com.example.dovebook.base.BaseToolbarActivity;

import butterknife.BindView;
import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * Created by zyd on 2018/4/11.
 */

public class register extends BaseToolbarActivity {

    @BindView(R.id.name_editText)
    EditText name_editText;
    @BindView(R.id.password_editText)
    EditText password_editText;
    @BindView(R.id.register_btn)
    Button register_btn;

    private registerPresenter mRegisterPresenter;

    @Override
    protected View initContentView() {
        View view = getLayoutInflater().inflate(R.layout.activity_register, null);
        return view;
    }

    @Override
    protected String initToolbarTitle() {
        return "注册";
    }

    @Override
    protected void initOptions() {
        if (mRegisterPresenter == null) {
            mRegisterPresenter = new registerPresenter(register.this);
        }
        register_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!name_editText.getText().toString().equals("") && !register_btn.getText().equals("")) {
                    RequestBody requestName = RequestBody.create(MediaType.parse("multipart/form-data"), name_editText.getText().toString());
                    RequestBody requestPassword = RequestBody.create(MediaType.parse("multipart/form-data"), password_editText.getText().toString());
                    mRegisterPresenter.register(requestName, requestPassword);
                }
            }
        });
    }

    @Override
    protected void initHomeButton() {
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }
}
