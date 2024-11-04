// Books.java
package edu.psu.sweng888.firebaseexamples;

import java.io.Serializable;

public class Books implements Serializable {
    private String author;
    private Long isbn;
    private String publicationDate;
    private String publisher;
    private String title;

    public Books() {
    }

    public Books(String author, Long isbn, String publicationDate, String publisher, String title) {
        this.author = author;
        this.isbn = isbn;
        this.publicationDate = publicationDate;
        this.publisher = publisher;
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Long getIsbn() {
        return isbn;
    }

    public void setIsbn(Long isbn) {
        this.isbn = isbn;
    }

    public String getPublicationDate() {
        return publicationDate;
    }

    public void setPublicationDate(String publicationDate) {
        this.publicationDate = publicationDate;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}