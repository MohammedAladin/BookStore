package com.Integration.NTI.Models.Entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

import java.math.BigDecimal;

@Entity
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Setter @Getter
    private Long bookId;

    @Setter @Getter
    private String title;

    @Getter @Setter
    protected String author;

    @Getter @Setter
    protected BigDecimal price;

    @Getter @Setter
    private Integer quantity;

    public Book(String title, String auth, BigDecimal price, Integer quantity) {
        this.title = title;
        this.author = auth;
        this.price = price;
        this.quantity = quantity;

    }

    public Book() {
    }
}
