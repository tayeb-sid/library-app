package com.example.bookstore;

public class Book {
    private int nb_pages,book_id;
    private String title,author;
    public Book(){}
    public Book(int nb_pages, String title, String author) {
        this.nb_pages = nb_pages;
        this.title = title;
        this.author = author;
    }

    public int getBook_id() {
        return book_id;
    }

    public void setBook_id(int book_id) {
        this.book_id = book_id;
    }

    public int getNb_pages() {
        return nb_pages;
    }

    public void setNb_pages(int nb_pages) {
        this.nb_pages = nb_pages;
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
}
