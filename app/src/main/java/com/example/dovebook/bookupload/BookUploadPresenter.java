package com.example.dovebook.bookupload;

import android.content.ContentUris;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.util.Log;

import com.example.dovebook.book.model.Book;
import com.example.dovebook.bookupload.model.BookModel;
import com.example.dovebook.common.Constant;
import com.example.dovebook.net.Api;
import com.example.dovebook.net.HttpManager;
import com.example.dovebook.utils.StringUtil;
import com.example.dovebook.utils.ToastUtil;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import top.zibin.luban.Luban;
import top.zibin.luban.OnCompressListener;

/**
 * Created by 28748 on 2018/4/15.
 */

public class BookUploadPresenter implements BookUploadContract.UploadPresenter {

    private static final String TAG = "BookUploadPresenter";

    BookUploadActivity mView;
    BookModel mBookModel;


    public BookUploadPresenter(BookUploadActivity activity) {
        mView = activity;
        if (mBookModel == null) {
            mBookModel = new BookModel(this);
        }
    }


    //zyd代码
    public String handleImage(Intent data) {
        String imagePath = null;
        Uri uri = data.getData();
        if (DocumentsContract.isDocumentUri(mView, uri)) {
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
        Cursor cursor = mView.getContentResolver().query(uri, null, selection, null, null);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
            }
            cursor.close();
        }
        return path;
    }

    public void uploadBook(final Book book) {
        if (StringUtil.isNull(book.getBookTitle())) {
            mView.showInfoErrors("书名不能为空！");
        } else if (StringUtil.isNull(book.getBookAuthor())) {
            mView.showInfoErrors("作者不能为空!");
        } else if (StringUtil.isNull(book.getBookPublisher())) {
            mView.showInfoErrors("出版商不能为空!");
        } else if (StringUtil.isNull(book.getBookIsbn())) {
            mView.showInfoErrors("Isbn编号不能为空!");
        } else if (StringUtil.isNull(book.getBookImagepath())) {
            mView.showInfoErrors("图书照片不能为空!");
        } else {

            mView.showUploadProcess();

            /*直接获取Activity里面的属性 ？ */
            Luban.get(mView)
                    .load(mView.file)
                    .putGear(Luban.THIRD_GEAR)
                    .setCompressListener(new OnCompressListener() {
                        @Override
                        public void onStart(){

                        }

                        @Override
                        public void onSuccess(File file) {

                            if (file != null) {
                                RequestBody requestBookImagePath = RequestBody.create(MediaType.parse("multipart/form-data"), file);
                                MultipartBody.Part body = MultipartBody.Part.createFormData("bookimage", file.getName(), requestBookImagePath);
                                mBookModel.uploadBook(book, body);

                            } else {
                                mBookModel.uploadBook(book, null);
                            }

                        }

                        @Override
                        public void onError(Throwable e) {
                                mView.hideUploadPrecess();
                                mView.showInfoErrors("图片过大，压缩失败");
                        }
                    }).launch();
        }
    }

    public void onSuccessCompleteUpload(){
        mView.hideUploadPrecess();
        mView.showUploadSuccess();
    }


    public void onErrorCompletedUpload(){
        mView.hideUploadPrecess();
        mView.showInfoErrors("上传失败..");
    }


}
