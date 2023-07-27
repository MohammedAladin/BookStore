package com.Integration.NTI.Response;

import com.Integration.NTI.Interfaces.Item;
import com.Integration.NTI.Models.Book;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

public class BookRespnse extends Item {


    @Getter @Setter
    protected Integer quantity;


    public static BookRespnse convertTOResponse(Book book){
        BookRespnse bookRespnse = new BookRespnse();

        bookRespnse.setTitle(book.getTitle());
        bookRespnse.setAuthor(book.getAuthor());
        bookRespnse.setPrice(book.getPrice());
        bookRespnse.setQuantity(book.getQuantity());

        return bookRespnse;
    }
}
