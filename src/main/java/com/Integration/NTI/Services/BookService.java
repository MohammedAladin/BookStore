package com.Integration.NTI.Services;

import com.Integration.NTI.Models.Book;
import com.Integration.NTI.Repositries.BookRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class BookService {
    private BookRepo bookRepo;

    @Autowired
    public BookService(BookRepo bookRepo) {
        this.bookRepo = bookRepo;
    }

    public Book addBook(Book book){
        return this.bookRepo.save(book);
    }
    public List<Book> findAll(){
        List<Book> all = bookRepo.findAll();
        return all;
    }
    public Book getById(Long id){
        return bookRepo.findById(id).get();
    }
    public void deleteById(Long id){
        bookRepo.deleteById(id);

    }


}
