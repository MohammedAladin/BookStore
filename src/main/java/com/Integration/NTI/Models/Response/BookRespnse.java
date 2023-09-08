package com.Integration.NTI.Models.Response;

import com.Integration.NTI.Models.Entities.BaseBook;
import com.Integration.NTI.Models.Entities.Book;
import lombok.Getter;
import lombok.Setter;

public class BookRespnse extends BaseBook {


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
