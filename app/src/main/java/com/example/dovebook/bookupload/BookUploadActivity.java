package com.example.dovebook.bookupload;

import android.view.View;

import com.example.dovebook.R;
import com.example.dovebook.base.BaseActivity;

/**
 * Created by 28748 on 2018/4/15.
 */

public class BookUploadActivity extends BaseActivity implements BookUploadContract.UploadView{
    @Override
    protected View initContentView() {
        View view = getLayoutInflater().inflate(R.layout.activity_book_upload, null);
        return view;
    }
}
