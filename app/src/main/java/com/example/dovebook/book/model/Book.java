package com.example.dovebook.book.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by xulw on 2018/3/10.
 */

public class Book implements Parcelable {

    private String bookId;
    //书名
    private String bookTitle;
    //作者
    private String bookAuthor;
    //书封面路径
    private String bookImagepath;
    //摘要
    private String bookSummary;
    //出版商
    private String bookPublisher;
    //ISBN号
    private String bookIsbn;
    //书页
    private Integer bookPages;
    //书价
    private Float bookPrice;
    //出版时间
    private String bookPubdate;
    //
    private String bookAnthorintro;
    //上传时间
    private Long createdat;
    //更新时间
    private Long updatedat;

    public String getBookId() {
        return bookId;
    }

    public void setBookId(String bookId) {
        this.bookId = bookId;
    }

    public String getBookTitle() {
        return bookTitle;
    }

    public void setBookTitle(String bookTitle) {
        this.bookTitle = bookTitle;
    }

    public String getBookAuthor() {
        return bookAuthor;
    }

    public void setBookAuthor(String bookAuthor) {
        this.bookAuthor = bookAuthor;
    }

    public String getBookImagepath() {
        return bookImagepath;
    }

    public void setBookImagepath(String bookImagepath) {
        this.bookImagepath = bookImagepath;
    }

    public String getBookSummary() {
        return bookSummary;
    }

    public void setBookSummary(String bookSummary) {
        this.bookSummary = bookSummary;
    }

    public String getBookPublisher() {
        return bookPublisher;
    }

    public void setBookPublisher(String bookPublisher) {
        this.bookPublisher = bookPublisher;
    }

    public String getBookIsbn() {
        return bookIsbn;
    }

    public void setBookIsbn(String bookIsbn) {
        this.bookIsbn = bookIsbn;
    }

    public Integer getBookPages() {
        return bookPages;
    }

    public void setBookPages(Integer bookPages) {
        this.bookPages = bookPages;
    }

    public Float getBookPrice() {
        return bookPrice;
    }

    public void setBookPrice(Float bookPrice) {
        this.bookPrice = bookPrice;
    }

    public String getBookPubdate() {
        return bookPubdate;
    }

    public void setBookPubdate(String bookPubdate) {
        this.bookPubdate = bookPubdate;
    }

    public String getBookAnthorintro() {
        return bookAnthorintro;
    }

    public void setBookAnthorintro(String bookAnthorintro) {
        this.bookAnthorintro = bookAnthorintro;
    }

    public Long getCreatedat() {
        return createdat;
    }

    public void setCreatedat(Long createdat) {
        this.createdat = createdat;
    }

    public Long getUpdatedat() {
        return updatedat;
    }

    public void setUpdatedat(Long updatedat) {
        this.updatedat = updatedat;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(bookId);
        dest.writeString(bookTitle);
        dest.writeString(bookAuthor);
        dest.writeString(bookImagepath);
        dest.writeString(bookSummary);
        dest.writeString(bookPublisher);
        dest.writeString(bookIsbn);
        dest.writeInt(bookPages);
        dest.writeFloat(bookPrice);
        dest.writeString(bookPubdate);
        dest.writeString(bookAnthorintro);
        dest.writeLong(createdat);
        dest.writeLong(updatedat);
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
        bookId = in.readString();
        bookTitle = in.readString();
        bookAuthor = in.readString();
        bookImagepath = in.readString();
        bookSummary = in.readString();
        bookPublisher = in.readString();
        bookIsbn = in.readString();
        bookPages = in.readInt();
        bookPrice = in.readFloat();
        bookPubdate = in.readString();
        bookAnthorintro = in.readString();
        createdat = in.readLong();
        updatedat = in.readLong();
    }

    public Book(){

    }

    @Override
    public String toString() {
        return "title:"+ bookTitle +" author:"+ bookAuthor +" id:"+ bookId;
    }
}
