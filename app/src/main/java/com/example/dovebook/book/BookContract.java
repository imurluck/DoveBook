package com.example.dovebook.book;

/**
 * Created by zjs on 2018/3/13.
 */

public interface BookContract {

    interface View {

        /**
         * 当没有数据时显示
         */
//        void showEmptyView();

    }

    interface Presenter{

        /**
         * 根据UserId 从服务器中获取所有Copy
         */
//        void getAllCopy();

        /**
         * 初始化数据
         */
//        void getInitData();

    }

    interface BookSendPresenter{

        /**
         * 删除图书
         * @param bookId
         */
//        void deleteABook(String bookId);
//
//        void getAllCopy();
    }
}
