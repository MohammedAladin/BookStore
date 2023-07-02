package com.Integration.NTI.Models;

import javax.persistence.*;

import java.math.BigDecimal;
import java.util.List;

@Entity
public class Book extends Item {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long bookId;
    private String title;
    private String author;
    private BigDecimal price;

    private Integer quantity;
    public Book(String title, BigDecimal price, Integer quantity) {
        this.title = title;
        this.quantity = quantity;
        this.price = price;
    }
    public Book(BigDecimal price, Integer quantity) {
        this.quantity = quantity;
        this.price = price;
    }


    public Book() {
        quantity = 1;
    }

    public Long getBookId() {
        return bookId;
    }

    public void setBookId(Long bookId) {
        this.bookId = bookId;
    }


    public Long getId() {
        return bookId;
    }

    public void setId(Long id) {
        this.bookId = id;
    }

    @Override
    public String getType() {
        return "Book";
    }

    @Override
    public String getTitle() {
        return this.title;
    }

    @Override
    public BigDecimal getPrice() {
        return this.price;
    }

    @Override
    public Integer getQuantity() {
        return this.quantity;
    }

    @Override


    public String getAuthor() {
        return author;
    }



    @Override
    public void setTitle(String title) {
        this.title=title;
    }

    @Override
    public void setPrice(BigDecimal price) {
        this.price=price;
    }

    @Override
    public void setQuantity(Integer quantity) {
        this.quantity= quantity;
    }


    public void setAuthor(String author) {
        this.author = author;
    }



    @Override
    public String toString() {
        return "Book{" +
                "bookId=" + bookId +
                ", title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", price=" + price +
                ", quantity=" + quantity +
                '}';
    }
}
