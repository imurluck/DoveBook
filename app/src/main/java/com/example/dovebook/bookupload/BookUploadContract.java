package com.example.dovebook.bookupload;

import com.example.dovebook.bean.Book;

import okhttp3.MultipartBody;

/**
 * Created by xulw on 2018/4/15.
 */

public interface BookUploadContract {
    interface UploadView {
        //显示上传进度
        void showUploadProcess();
        //隐藏上传进度
        void hideUploadPrecess();
        //显示上传成功
        void showUploadSuccess();
        //显示上传失败
        void showUploadFailed();
        //显示字段错误信息
        void showInfoErrors(String message);

        void getEditFocus(int editCode);

        void showBookInfo(Book book);


    }

    interface UploadPresenter {

        // 上传书到服务器
//       void uploadBookToServer();

        //创建副本
//        void createCopy();
        //上传副本
//        void uploadCopyToServer();
        //打开相册选择图片
//        void choosePhotoFromAlbum();
    }

    interface DataModel{
        void uploadBook(Book book, MultipartBody.Part bookImage);

        void getBookInfoByIsbn(String isbn);
    }
}
