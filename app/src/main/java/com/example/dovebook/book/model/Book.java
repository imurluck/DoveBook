package com.example.dovebook.book.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

/**
 * Created by xulw on 2018/3/10.
 */

public class Book implements Parcelable {

    private String mBookId;
    //书名
    private String mBookTitle;
    //作者
    private String mBookAuthor;
    //书封面路径
    private String mBookImagepath;
    //摘要
    private String mBookSummary;
    //出版商
    private String mBookPublisher;
    //ISBN号
    private String mBookIsbn;
    //书页
    private Integer mBookPages;
    //书价
    private Float mBookPrice;
    //出版时间
    private String mBookPubdate;
    //
    private String mBookAnthorintro;
    //上传时间
    private Long mCreatedat;
    //更新时间
    private Long mUpdatedat;

    public String getBookId() {
        return mBookId;
    }

    public void setBookId(String bookId) {
        mBookId = bookId;
    }

    public String getBookTitle() {
        return mBookTitle;
    }

    public void setBookTitle(String bookTitle) {
        mBookTitle = bookTitle;
    }

    public String getBookAuthor() {
        return mBookAuthor;
    }

    public void setBookAuthor(String bookAuthor) {
        mBookAuthor = bookAuthor;
    }

    public String getBookImagepath() {
        return mBookImagepath;
    }

    public void setBookImagepath(String bookImagepath) {
        mBookImagepath = bookImagepath;
    }

    public String getBookSummary() {
        return mBookSummary;
    }

    public void setBookSummary(String bookSummary) {
        mBookSummary = bookSummary;
    }

    public String getBookPublisher() {
        return mBookPublisher;
    }

    public void setBookPublisher(String bookPublisher) {
        mBookPublisher = bookPublisher;
    }

    public String getBookIsbn() {
        return mBookIsbn;
    }

    public void setBookIsbn(String bookIsbn) {
        mBookIsbn = bookIsbn;
    }

    public Integer getBookPages() {
        return mBookPages;
    }

    public void setBookPages(Integer bookPages) {
        mBookPages = bookPages;
    }

    public Float getBookPrice() {
        return mBookPrice;
    }

    public void setBookPrice(Float bookPrice) {
        mBookPrice = bookPrice;
    }

    public String getBookPubdate() {
        return mBookPubdate;
    }

    public void setBookPubdate(String bookPubdate) {
        mBookPubdate = bookPubdate;
    }

    public String getBookAnthorintro() {
        return mBookAnthorintro;
    }

    public void setBookAnthorintro(String bookAnthorintro) {
        mBookAnthorintro = bookAnthorintro;
    }

    public Long getCreatedat() {
        return mCreatedat;
    }

    public void setCreatedat(Long createdat) {
        mCreatedat = createdat;
    }

    public Long getUpdatedat() {
        return mUpdatedat;
    }

    public void setUpdatedat(Long updatedat) {
        mUpdatedat = updatedat;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mBookId);
        dest.writeString(mBookTitle);
        dest.writeString(mBookAuthor);
        dest.writeString(mBookImagepath);
        dest.writeString(mBookSummary);
        dest.writeString(mBookPublisher);
        dest.writeString(mBookIsbn);
        dest.writeInt(mBookPages);
        dest.writeFloat(mBookPrice);
        dest.writeString(mBookPubdate);
        dest.writeString(mBookAnthorintro);
        dest.writeLong(mCreatedat);
        dest.writeLong(mUpdatedat);
    }

    public static final Parcelable.Creator<Book> CREATOR = new Parcelable.Creator<Book>() {

        @Override
        public Book createFromParcel(Parcel source) {
            return new Book(source);
        }

        @Override
        public Book[] newArray(int size) {
            return new Book[size];
        }
    };

    private Book(Parcel in) {
        mBookId = in.readString();
        mBookTitle = in.readString();
        mBookAuthor = in.readString();
        mBookImagepath = in.readString();
        mBookSummary = in.readString();
        mBookPublisher = in.readString();
        mBookIsbn = in.readString();
        mBookPages = in.readInt();
        mBookPrice = in.readFloat();
        mBookPubdate = in.readString();
        mBookAnthorintro = in.readString();
        mCreatedat = in.readLong();
        mUpdatedat = in.readLong();
    }

    @Override
    public String toString() {
        return "title:"+mBookTitle+" author:"+mBookAuthor+" id:"+mBookId;
    }
}
