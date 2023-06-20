package com.Integration.NTI.Models;

import javax.persistence.*;

import java.util.List;
@Entity

public class ShoppingCart {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToMany(mappedBy = "bookId")
    private List<Book> books;

    private Long cartId;
}
