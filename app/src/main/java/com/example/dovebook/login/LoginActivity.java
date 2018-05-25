package com.example.dovebook.login;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import com.example.dovebook.Information.InfoManager;
import com.example.dovebook.R;
import com.example.dovebook.base.BaseActivity;
import com.example.dovebook.base.model.User;
import com.example.dovebook.register.register;

import butterknife.BindView;

public class LoginActivity extends BaseActivity {

    private static final String TAG = "LoginActivity";
    @BindView(R.id.checkbox)
    CheckBox mCheckBox;
    @BindView(R.id.btn_login)
    Button btnLogin;
    @BindView(R.id.btn_register)
    Button btnRegister;
    @BindView(R.id.Login_EditText01)
    EditText editTextA;
    @BindView(R.id.Login_EditText02)
    EditText editTextP;
    LoginPresenter loginPresenter;
    TimeManager mTimeManager;
    UserManager mUserManager;


    @Override
    protected View initContentView() {
        View view = getLayoutInflater().inflate(R.layout.activity_login, null);
        return view;
    }

    @Override
    protected void initOptions() {
        mUserManager= UserManager.getInstance();
        loginPresenter = new LoginPresenter(this);
        mTimeManager = new TimeManager(this);
        loginPresenter.hasRememberPas(editTextA, editTextP);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                loginPresenter.isRemember(mCheckBox, editTextA, editTextP);
                loginPresenter.login(editTextA.getText().toString(), editTextP.getText().toString(), mTimeManager, mUserManager);
            }
        });
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(LoginActivity.this,register.class);
                startActivity(intent);
            }
        });
    }

}
