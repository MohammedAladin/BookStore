package com.Integration.NTI.Models;

import com.Integration.NTI.Interfaces.Item;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "cart_item")
public class CartItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String type;
    @ManyToOne
    @JoinColumn(name = "item_id")
    private Book book;

    private int quantity;

    private BigDecimal price = BigDecimal.ZERO;

    @ManyToOne
    @JoinColumn(name = "cart_id")
    private Cart cart;

    public CartItem(String type, Book item, int quantity) {
        this.book = item;
        this.quantity = quantity;
        this.type =type;
        this.price = price.add(book.getPrice().multiply(BigDecimal.valueOf(quantity)));
    }
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Book getBook() {
        return this.book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Cart getCart() {
        return cart;
    }

    public void setCart(Cart cart) {
        this.cart = cart;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
// Constructor, getters, and setters


    public CartItem(){}


}
