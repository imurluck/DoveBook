package com.example.dovebook.Information;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.dovebook.R;
import com.example.dovebook.base.BaseToolbarActivity;
import com.example.dovebook.base.model.User;
import com.example.dovebook.contact.isNumericUtil;
import com.example.dovebook.login.UserManager;

import java.io.File;

import butterknife.BindView;
import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.MediaType;
import okhttp3.RequestBody;

public class InfoManager extends BaseToolbarActivity {
    private static String TAG = "InfoManager";
    public static final int CHOOSE_PHOTO = 2;
    @BindView(R.id.relative0)
    RelativeLayout relative0;
    @BindView(R.id.name_ediText)
    EditText name_ediText;
    @BindView(R.id.tel_ediText)
    EditText tel_ediText;
    @BindView(R.id.mail_ediText)
    EditText mail_ediText;
    @BindView(R.id.age_ediText)
    EditText age_ediText;
    @BindView(R.id.circle_image)
    CircleImageView circle_image;
    private User user;
    UserManager mUserManager;
    InformationPresenter mInformationPresenter;
    private File file;
    private RequestBody requestUserName;
    private RequestBody requestUserEmail;


    @Override
    protected View initContentView() {
        View view = getLayoutInflater().inflate(R.layout.activity_infomanager, null);
        return view;
    }

    @Override
    protected String initToolbarTitle() {
//        toolbar_textView.setVisibility(View.VISIBLE);
        return "我的资料";
    }

    @Override
    protected void initOptions() {
        mUserManager = new UserManager(this);
        mInformationPresenter = new InformationPresenter(this);
        Glide.with(this).load(mUserManager.getUser().getUserAvatarPath()).into(circle_image);
        if (mUserManager.getUser().getUserName() != null) {
            name_ediText.setText(mUserManager.getUser().getUserName());
            name_ediText.setHint(mUserManager.getUser().getUserName());
        }
        if (mUserManager.getUser().getUserPhone() != 0) {
            tel_ediText.setText(mUserManager.getUser().getUserPhone().toString());
            tel_ediText.setHint(String.valueOf(mUserManager.getUser().getUserPhone()));
        }
        if (mUserManager.getUser().getUserEmail() != null) {
            mail_ediText.setText(mUserManager.getUser().getUserEmail());
            mail_ediText.setHint(mUserManager.getUser().getUserEmail());
        }
        if (mUserManager.getUser().getUserAge() != -1) {
            age_ediText.setText(mUserManager.getUser().getUserAge().toString());
            age_ediText.setHint(mUserManager.getUser().getUserAge().toString());
        }

        /*
        ****************************按下图片进入相册*****************************
         */
        relative0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                choosePhotoFromAlbum();
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case 1:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    openAlbum();
                }
                break;
            default:
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case CHOOSE_PHOTO:
                if (requestCode == CHOOSE_PHOTO) {
                    if (data != null) {
                        if (user == null) {
                            user = new User();
                        }

                        file = new File(mInformationPresenter.handleImage(data));
                        Log.d(TAG, "onActivityResult: " + mInformationPresenter.handleImage(data));
                        user.setUserAvatarPath("" + data.getData());
                        Glide.with(this).load(file)
                                .into(circle_image);
                    }
                }
                break;
            default:
                break;
        }
    }

    public void choosePhotoFromAlbum() {
        if (ContextCompat.checkSelfPermission(InfoManager.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(InfoManager.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
        } else {
            openAlbum();
        }
    }

    public void openAlbum() {
        Log.d(TAG, "openAlbum: ");
        Intent intent = new Intent("android.intent.action.GET_CONTENT");
        intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                "image/*");
        startActivityForResult(intent, CHOOSE_PHOTO);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.information_menu,menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
/*
 **************************按下保存发送网络请求************************
 */
            case R.id.save:
                if (user == null) {
                    user = new User();
                }
                if (!mail_ediText.getText().toString().trim().equals("")) {
                    user.setUserEmail(mail_ediText.getText().toString().trim());
                }
                if (!age_ediText.getText().toString().trim().equals("")) {
                    if (isNumericUtil.isNumeric(age_ediText.getText().toString().trim()) && Integer.valueOf(age_ediText.getText().toString().trim()) > -1) {
                        user.setUserAge(Integer.valueOf(age_ediText.getText().toString().trim()));
                    } else {
                        Toast.makeText(InfoManager.this, "年龄有点不对劲哦-_-", Toast.LENGTH_LONG).show();
                        break;
                    }
                } else {
                    user.setUserAge(-1);
                }
                if (!tel_ediText.getText().toString().trim().equals("")) {
                    if (isNumericUtil.isNumeric(tel_ediText.getText().toString().trim())) {
                        user.setUserPhone(Long.valueOf(tel_ediText.getText().toString().trim()));
                    } else {
                        Toast.makeText(InfoManager.this, "电话有点不对劲哦-_-", Toast.LENGTH_LONG).show();
                        break;
                    }
                } else {
                    user.setUserPhone(0);
                }

                if (!name_ediText.getText().toString().trim().equals("")) {
                    user.setUserName(name_ediText.getText().toString().trim());
                    requestUserName = RequestBody.create(MediaType.parse("multipart/form-data"), user.getUserName());
                    if (user.getUserEmail() != null) {
                        requestUserEmail = RequestBody.create(MediaType.parse("multipart/form-data"), user.getUserEmail());
                    }
                    mInformationPresenter.upFile(mUserManager.getUser().getUserId(), requestUserName, user.getUserPhone(), requestUserEmail, user.getUserAge(), file);
                } else {
                    Toast.makeText(InfoManager.this, "请填写您的昵称", Toast.LENGTH_LONG).show();
                }
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
