package com.example.dovebook.test;

import android.os.Bundle;
import android.view.View;

import com.example.dovebook.R;
import com.example.dovebook.base.BaseActivity;

public class TestActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected View initContentView() {
        View view = getLayoutInflater().inflate(R.layout.activity_test, null);
        return view;
    }
}
