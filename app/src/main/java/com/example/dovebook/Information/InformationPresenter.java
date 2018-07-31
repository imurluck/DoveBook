package com.example.dovebook.Information;

import android.app.ProgressDialog;
import android.content.ContentUris;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.Toast;

import com.example.dovebook.base.model.User;
import com.example.dovebook.common.Constant;
import com.example.dovebook.login.UserManager;
import com.example.dovebook.net.Api;
import com.example.dovebook.net.HttpManager;
import com.example.dovebook.utils.ToastUtil;

import java.io.File;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import top.zibin.luban.Luban;
import top.zibin.luban.OnCompressListener;


/**
 * Created by zyd on 2018/3/28.
 */

public class InformationPresenter {
    private static final String TAG = "InformationPresenter";
    private InfoManager mInfoManager;
    private SharedPreferences pref;
    RequestBody requestUserAvatarPath;
    MultipartBody.Part body;
    private ProgressDialog saving;

    InformationPresenter(InfoManager infoManager) {
        mInfoManager = infoManager;
    }

    public void update(String userId, RequestBody userName, long userPhone, RequestBody userEmail, int userAge, MultipartBody.Part userAvatarPath) {

        Api api = HttpManager.getInstance().getApiService(Constant.BASE_UPDATE_URL);
        api.updateUser(userId, userName, userPhone, userEmail, userAge, userAvatarPath)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<User>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(User user) {
                        UserManager.setUser(user);
                        saving.dismiss();
                        ToastUtil.shortToast("保存成功！");
//                        Log.d(TAG, "print: " + mInfoManager.mUserManager.getUser().getUserAvatarPath());
                    }

                    @Override
                    public void onError(Throwable e) {
                        saving.dismiss();
                        ToastUtil.shortToast("保存失败！");
                        Log.e(TAG, "updateOnError: " + e.getMessage());
                        //getSelfListFromServer("02a618bf-0241-11e8-bd05-00163e0ac98c");
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }


    public String handleImage(Intent data) {
        String imagePath = null;
        Uri uri = data.getData();
        if (DocumentsContract.isDocumentUri(mInfoManager, uri)) {
            String docId = DocumentsContract.getDocumentId(uri);
            if ("com.android.providers.media.documents".equals(uri.getAuthority())) {
                String id = docId.split(":")[1];
                String selection = MediaStore.Images.Media._ID + "=" + id;
                imagePath = getImagePath(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, selection);
            } else if ("com.android.providers.downloads.documents".equals(uri.getAuthority())) {
                Uri contentUri = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"), Long.valueOf(docId));
                imagePath = getImagePath(contentUri, null);
            }
        } else if ("content".equalsIgnoreCase(uri.getScheme())) {
            imagePath = getImagePath(uri, null);
        } else if ("file".equalsIgnoreCase(uri.getScheme())) {
            imagePath = uri.getPath();
        }
        return imagePath;
    }

    public String getImagePath(Uri uri, String selection) {
        String path = null;
        Cursor cursor = mInfoManager.getContentResolver().query(uri, null, selection, null, null);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
            }
            cursor.close();
        }
        return path;
    }

    public void upFile(final String userId, final RequestBody userName, final long userPhone, final RequestBody userEmail, final int userAge, final File file) {
        saving = ProgressDialog.show(mInfoManager, "提示", "正在保存", false,true);
        if (file != null) {
            Luban.get(mInfoManager)
                    .load(file)
                    .putGear(Luban.THIRD_GEAR)
                    .setCompressListener(new OnCompressListener() {
                        @Override
                        public void onStart() {

                        }

                        @Override
                        public void onSuccess(File file) {
                            Log.d(TAG, "upFile: ok");
                            if (file != null) {
                                requestUserAvatarPath = RequestBody.create(MediaType.parse("multipart/form-data"), file);
                                body = MultipartBody.Part.createFormData("avatar", file.getName(), requestUserAvatarPath);
                            } else {
                                body = null;
                            }
                            update(userId, userName, userPhone, userEmail, userAge, body);
                        }

                        @Override
                        public void onError(Throwable e) {

                        }
                    }).launch();
        } else {
            body = null;
            update(userId, userName, userPhone, userEmail, userAge, body);
        }
    }

}
