package com.viveksb007.libgenio.model;

import android.util.Log;

public class Book {

    private static final String TAG = "Book";
    private String ID;
    private String title;
    private String author;
    private String publisher;
    private String year;
    private String pages;
    private String language;
    private String size;
    private String extension;
    private String downloadLink;

    public Book() {

    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getPages() {
        return pages;
    }

    public void setPages(String pages) {
        this.pages = pages;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getExtension() {
        return extension;
    }

    public void setExtension(String extension) {
        this.extension = extension;
    }

    public String getDownloadLink() {
        return downloadLink;
    }

    public void setDownloadLink(String downloadLink) {
        this.downloadLink = downloadLink;
    }

    public void logBook() {
        Log.v(TAG, "ID : " + ID + '\n' +
                "Title : " + title + '\n' +
                "Author : " + author + '\n' +
                "Publisher : " + publisher + '\n' +
                "Year : " + year + '\n' +
                "Pages : " + pages + '\n' +
                "Language : " + language + '\n' +
                "Size : " + size + '\n' +
                "Extension : " + extension + '\n' +
                "Download Link : " + downloadLink + '\n');
    }
}
