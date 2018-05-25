package com.example.dovebook.bookupload.model;

import java.util.List;

public class DoubanBook {
    public String id;
    public String isbn10;
    public String isbn13;
    public String title;
    public String origin_title;
    public String alt_title;
    public String subtitle;
    public String url;
    public String alt;
    public String image;
    public MyImages images;
    public List<String> author;
    public List<String> translator;
    public String publisher;
    public String pubdate;
    public MyRating rating;
    public List<MyTags> tags;
    public String binding;
    public String price;
    public MySeries series;
    public int pages;
    public String author_intro;
    public String summary;
    public String catalog;
    public String ebook_url;
    public String ebook_price;


    private class MyImages{
        public String small;
        public String large;
        public String medium;
    }

    private class MyRating{
        public long max;
        public long numRaters;
        public double average;
        public long min;
    }

    private class MyTags{
        public long count;
        public String name;
    }

    private class MySeries{
        private String id;
        private String title;
    }
}
