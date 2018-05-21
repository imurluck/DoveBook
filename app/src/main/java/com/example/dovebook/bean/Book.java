package com.example.dovebook.bean;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import com.example.dovebook.utils.StringUtil;

/**
 * Created by zjs on 2018/3/10.
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
    private int bookPages;
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

    public Book setBookTitle(String bookTitle) {
        this.bookTitle = bookTitle;
        return this;
    }

    public String getBookAuthor() {
        if (bookAuthor != null)
            return bookAuthor;
        else
            return "unknown";
    }

    public Book setBookAuthor(String bookAuthor) {
        this.bookAuthor = bookAuthor;
        return this;
    }

    public String getBookImagepath() {
        return bookImagepath;
    }

    public Book setBookImagepath(String bookImagepath) {
        this.bookImagepath = bookImagepath;
        return this;
    }

    public String getBookSummary() {
        return bookSummary;
    }

    public Book setBookSummary(String bookSummary) {
        this.bookSummary = bookSummary;
        return this;
    }

    public String getBookPublisher() {
        if (bookPublisher != null)
            return bookPublisher;
        else
            return "unknown";
    }

    public Book setBookPublisher(String bookPublisher) {
        this.bookPublisher = bookPublisher;
        return this;
    }

    public String getBookIsbn() {
        if (bookIsbn != null)
            return bookIsbn;
        else
            return "unknown";
    }

    public Book setBookIsbn(String bookIsbn) {
        this.bookIsbn = bookIsbn;
        return this;
    }

    public Integer getBookPages() {
        return bookPages;
    }

    public Book setBookPages(String bookPages) {
        if (bookPages == null || bookPages.equals("")) {
            this.bookPages = 0;
        } else {
            this.bookPages = Integer.parseInt(bookPages);
        }
        return this;
    }

    public Float getBookPrice() {
        if (bookPrice != null)
            return bookPrice;
        else
            return 0f;
    }

    public Book setBookPrice(String bookPrice) {
        if (bookPrice == null || bookPrice.equals("") || !StringUtil.isFloatString(bookPrice)) {
            this.bookPrice = 0f;
            Log.d("test", "setBookPrice: "+bookPrice);
        } else {
            this.bookPrice = Float.parseFloat(bookPrice);
        }
        return this;
    }

    public String getBookPubdate() {
        if (bookPubdate != null)
            return bookPubdate;
        else
            return "";
    }

    public Book setBookPubdate(String bookPubdate) {
        this.bookPubdate = bookPubdate;
        return this;
    }

    public String getBookAnthorintro() {
        if (bookAnthorintro != null)
            return bookAnthorintro;
        else
            return "";
    }

    public Book setBookAnthorintro(String bookAnthorintro) {
        this.bookAnthorintro = bookAnthorintro;
        return this;
    }

    public Long getCreatedat() {
        if (createdat != null)
            return createdat;
        return 0L;
    }

    public void setCreatedat(Long createdat) {
        this.createdat = createdat;
    }

    public Long getUpdatedat() {
        if (updatedat != null)
            return updatedat;
        return 0L;
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

    public Book() {
        bookImagepath = "";
    }

    @Override
    public String toString() {
        return "Book{" +
                "bookId='" + bookId + '\'' +
                ", bookTitle='" + bookTitle + '\'' +
                ", bookAuthor='" + bookAuthor + '\'' +
                ", bookImagepath='" + bookImagepath + '\'' +
                ", bookSummary='" + bookSummary + '\'' +
                ", bookPublisher='" + bookPublisher + '\'' +
                ", bookIsbn='" + bookIsbn + '\'' +
                ", bookPages=" + bookPages +
                ", bookPrice=" + bookPrice +
                ", bookPubdate='" + bookPubdate + '\'' +
                ", bookAnthorintro='" + bookAnthorintro + '\'' +
                ", createdat=" + createdat +
                ", updatedat=" + updatedat +
                '}';
    }
}
